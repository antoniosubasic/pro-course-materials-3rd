package codeEditor;

import java.util.Arrays;
import java.util.List;

public class JavaFormatter implements CodeFormatter {
    private static final char EOF = (char) -1;
    private static final List<String> KEYWORDS = Arrays.asList(
            "abstract",
            "assert",
            "boolean",
            "break",
            "byte",
            "case",
            "catch",
            "char",
            "class",
            "const",
            "continue",
            "default",
            "do",
            "double",
            "else",
            "enum",
            "extends",
            "final",
            "finally",
            "float",
            "for",
            "goto",
            "if",
            "implements",
            "import",
            "instanceof",
            "int",
            "interface",
            "long",
            "native",
            "new",
            "package",
            "private",
            "protected",
            "public",
            "return",
            "short",
            "static",
            "strictfp",
            "super",
            "switch",
            "synchronized",
            "this",
            "throw",
            "throws",
            "transient",
            "try",
            "void",
            "volatile",
            "while",
            "true",
            "false",
            "null");

    private String text;
    private int pos;
    private char ch;

    @Override
    public String format(String text) {
        StringBuilder formattedCode = new StringBuilder();
        this.text = text;
        pos = 0;
        var previousCharIsComment = false;
        nextChar();

        while (ch != EOF) {
            switch (ch) {
                // check for variable, which starts with a letter and can contain letters and
                // digits
                case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                        'w', 'x', 'y', 'z',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                        'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                        'W', 'X', 'Y', 'Z':
                    // read and format word
                    formattedCode.append(readAndFormatWord());
                    break;
                case '/':
                    if (previousCharIsComment) {
                        formattedCode.append(readComment());
                    } else {
                        nextChar();
                    }
                    previousCharIsComment = !previousCharIsComment;
                    break;
                case '"':
                    formattedCode.append(readString());
                    break;
                default:
                    formattedCode.append(ch);
                    nextChar();
                    break;
            }
        }

        return formattedCode.toString();
    }

    private void nextChar() {
        try {
            ch = text.charAt(pos++);
        } catch (StringIndexOutOfBoundsException e) {
            ch = EOF;
        }
    }

    private String readAndFormatWord() {
        StringBuilder variable = new StringBuilder();
        while (Character.isLetter(ch)) {
            variable.append(ch);
            nextChar();
        }

        // check if variable is a function or a variable and format it accordingly
        if (KEYWORDS.contains(variable.toString())) {
            return ConsoleColor.ANSI_BLUE + variable.toString() + ConsoleColor.ANSI_RESET;
        } else {
            return variable.toString();
        }
    }

    // read and format comments
    private String readComment() {
        var comment = new StringBuilder();
        comment.append('/');

        while (ch != '\n') {
            comment.append(ch);
            nextChar();
        }

        return ConsoleColor.ANSI_GREY + comment.toString() + ConsoleColor.ANSI_RESET;
    }

    // read and format strings
    private String readString() {
        var string = new StringBuilder();
        var previousChar = '\u0000';

        while (ch != EOF) {
            string.append(ch);
            previousChar = ch;
            nextChar();

            if (ch == '"' && previousChar != '\\') {
                break;
            }
        }
        string.append('"');
        nextChar();

        return ConsoleColor.ANSI_GREEN + string.toString() + ConsoleColor.ANSI_RESET;
    }
}
