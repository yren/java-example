package me.lokvin.baeldung.scan;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScanTest {
    String normalTemplate = "<.*?>";
    String missingEndTemplate = "<\\w*\\b";
    String allTemplate = "<.*?>|<\\w*\\b";

    String errInfoTemplate = "line %d: %s\n";
    String expected = "line %d: expected %s\n";
    String tooMany = "too many/few characters in tag name.";
    String badName = "bad character in tag name.";
    String noMatchBegin = "no matching begin tag.";

    @Test
    public void validateInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("target/html.txt"));
        List<HtmlPart> htmlParts = new ArrayList<>();
        while (scanner.hasNextInt()) {
            int num = scanner.nextInt();
            if (num == 0) {
                break;
            }
            scanner.nextLine();
            HtmlPart htmlPart = new HtmlPart();
            for (int i = 1; i <= num; i++) {
                String line = scanner.nextLine();
                htmlPart.getLines().add(line);
            }
            htmlParts.add(htmlPart);
        }
        validate(htmlParts);
    }

    private void validate(List<HtmlPart> htmlParts) {
        for (int i = 0; i < htmlParts.size(); i++) {
            System.out.printf("Test Case %d\n", i + 1);
            List<String> lines = htmlParts.get(i).lines;
            boolean noError = true;
            Stack<String> tagStack = new Stack<String>();
            for (int j = 0; j < lines.size(); j++) {
                String line = lines.get(j);
                Scanner lineScaner = new Scanner(line);
                boolean flag = true;
                Stream<MatchResult> matches = lineScaner.findAll("<.*?>|<\\w*\\b");
                List<String> tags = matches.map(e -> e.group(0)).collect(Collectors.toList());
                for (String tag: tags) {
                    if (!flag) {
                        break;
                    }

                    if (tag.matches("<\\w*\\b")) {
                        System.out.printf(errInfoTemplate, j + 1, badName);
                        flag = false;
                        noError = false;
                    }

                    if (tag.matches("<>")) {
                        System.out.printf(errInfoTemplate, j+1, tooMany);
                        flag = false;
                        noError = false;
                    }

                    if (tag.length() >= 12) {
                        System.out.printf(errInfoTemplate, j+1, tooMany);
                        flag = false;
                        noError = false;
                    }

                    if (tagStack.isEmpty() && isEndTag(tag)) {
                        System.out.printf(errInfoTemplate, j+1, noMatchBegin);
                        flag = false;
                        noError = false;
                    }

                    if (!isEndTag(tag)) {
                        tagStack.push(tag);
                    }

                    if (isEndTag(tag) && !tagStack.isEmpty()) {
                        String prev = tagStack.peek();
                        if (matchTag(prev, tag)) {
                            tagStack.pop();
                        } else {
                            String expectedTag = getExpectedTag(prev);
                            System.out.printf(expected, j+1, expectedTag);
                            flag = false;
                            noError = false;
                        }
                    }


                }
            }
            if (noError) {
                System.out.println("OK");
            }
            System.out.println("");
        }
    }

    private boolean isEndTag(String tag) {
        return tag.charAt(1) == '/';
    }
    @Test
    public void testIsEnd() {
        String tag = "<hi>";
        System.out.println(isEndTag(tag));
    }

    private boolean matchTag(String tag1, String tag2) {
        String t1Sub = tag1.substring(1), t2Sub = tag2.substring(2);
        return t1Sub.equals(t2Sub);
    }

    @Test
    public void testMatchTag() {
        String t1= "<START>", t2 = "</START>";
        System.out.println(matchTag(t1, t2));
    }

    private String getExpectedTag(String tag) {
        String expectedTag = tag.substring(0,1) + "/" + tag.substring(1);
        return expectedTag;
    }

    @Test
    public void testGetTag() {
        String tag = "<hello>";
        System.out.println(getExpectedTag(tag));
    }
}
