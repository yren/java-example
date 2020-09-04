package me.lokvin.baeldung.scan;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaScannerTest {

    @Before
    public void init() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("target/test.txt"));
        try (writer) {
            writer.append("2\n");
            writer.append("we are the best\n");
            writer.append("I like it.\n");
            writer.append("3\n");
            writer.append("I like it one.\n");
            writer.append("I like it two.\n");
            writer.append("I like it three.\n");
        }
    }

    @Test
    public void readFileWithScanner() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("target/test.txt"));
        try (scanner) {
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        }
    }

    @Test
    public void whenConvertInputStreamToString() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("target/test.txt");
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("A");
        System.out.println(scanner.next());
        scanner.close();
    }

    @Test
    public void readUsingBufferedReader() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("target/test.txt"));
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        System.out.println(content.toString());
        reader.close();
    }

    @Test
    public void readUsingScanner() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("target/test.txt");
        Scanner scanner = new Scanner(inputStream);
        String result = scanner.nextLine();
        System.out.println(result);
        scanner.useDelimiter(", ");
        System.out.println(scanner.next());
        scanner.close();
    }

    @Test
    public void readInputFromConsole() {
        String input = "hello";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        String result = scanner.next();
        System.out.println("output: " + result);
        System.setIn(stdin);
        scanner.nextInt();
        scanner.close();
    }

    @Test
    public void validateInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("target/html.txt"));
        int n = 0;
        List<HtmlPart> htmlParts = new ArrayList<>();
        String normalTemplate = "<.*?>";
        String missingEndTemplate = "<\\w*\\b";
        while (scanner.hasNextInt()) {
            int num = scanner.nextInt();
            scanner.nextLine();
            HtmlPart htmlPart = new HtmlPart();
            n++;
            //System.out.println("Test Case " + n);
            Stack<String> tagStack = new Stack<>();

            for (int i = 1; i <= num; i++) {
                String line = scanner.nextLine();
                htmlPart.getLines().add(line);
                //System.out.println("line: " + line);
//                Scanner lineScaner = new Scanner(line);
//                Stream<MatchResult> matches = lineScaner.findAll("<.*?>|<\\w*\\b");
//                int lineNum = i;
//                matches.forEach(e -> doWork(tagStack, e, lineNum));
            }
            htmlParts.add(htmlPart);
        }
        System.out.println("html parts size: " + htmlParts.size());
        for (HtmlPart part : htmlParts) {
            for (int i = 0; i < part.lines.size(); i++) {
                System.out.printf("%d, %s\n", i + 1, part.lines.get(i));
            }
        }

        dealWith(htmlParts);
    }

    private void dealWith(List<HtmlPart> htmlParts) {
        String template = "line %d: %s\n";
        String tooMany = "too many/few characters in tag name.";
        String noMatchBegin = "no matching begin tag.";
        for (int i = 0; i < htmlParts.size(); i++) {
            System.out.printf("Test Case %d\n", i + 1);
            List<String> lines = htmlParts.get(i).lines;
            for (int j = 0; j < lines.size(); j++) {
                String line = lines.get(j);
                Scanner lineScaner = new Scanner(line);
                Stack<String> tagStack = new Stack<String>();
                boolean flag = true;
                Stream<MatchResult> matches = lineScaner.findAll("<.*?>|<\\w*\\b");
                List<String> tags = matches.map(e -> e.group(0)).collect(Collectors.toList());
                for (String tag : tags) {
                    //System.out.println("...: " + tag);
                    if (!flag) {
                        continue;
                    }
                    if (tag.matches("<\\w*\\b")) {
                        System.out.printf(template, j + 1, "bad character in tag name.");
                        flag = false;
                    }

                    if (tag.matches("<>")) {
                        System.out.printf(template, i, tooMany);
                        flag = false;
                    }

                    if (tag.length() >= 12) {
                        System.out.printf(template, i, tooMany);
                        flag = false;
                    }
                    if (tagStack.isEmpty() && isEndTag(tag)) {
                        System.out.printf(template, i, noMatchBegin);
                        flag = false;
                    }
                    if (tagStack.isEmpty() && !isEndTag(tag)) {
                        tagStack.push(tag);

                    }
                    if (!tagStack.isEmpty()) {
                        String prev = tagStack.pop();
                        if (matchTag(prev, tag)) {
                            tagStack.pop();
                        }
                    }
                }
            }
        }
    }

    private void doWork(Stack<String> tagStack, MatchResult e, int i) {
        String tooMany = "too many/few characters in tag name.";
        String template = "line %d: %s\n";
        String noMatchBegin = "no matching begin tag.";
        String tag = e.group(0);
        System.out.println("tag: " + tag);
        if (tag.length() >= 12) {
            System.out.printf(template, i, tooMany);
            return;
        }

        if (tag.matches("<>")) {
            System.out.printf(template, i, tooMany);
        }
        if (tagStack.isEmpty() && isEndTag(tag)) {
            System.out.printf(template, i, noMatchBegin);
        }
        if (tagStack.isEmpty() && !isEndTag(tag)) {
            tagStack.push(tag);
            return;
        }
        if (!tagStack.isEmpty()) {
            String prev = tagStack.pop();
            if (matchTag(prev, tag)) {
                tagStack.pop();
                return;
            }
        }
    }

    private boolean isEndTag(String tag) {
        return tag.charAt(1) == '/';
    }

    private boolean matchTag(String tag1, String tag2) {
        return tag1.substring(1) == tag2.substring(2);
    }
}

class HtmlPart {

    List<String> lines;

    public HtmlPart() {
        lines = new ArrayList<String>();
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
