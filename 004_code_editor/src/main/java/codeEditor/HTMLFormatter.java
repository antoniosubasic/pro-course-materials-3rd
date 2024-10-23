package codeEditor;

public class HTMLFormatter implements CodeFormatter {
    private static final char EOF = (char) -1;

    private String text;
    private int pos;
    private char ch;

    @Override
    public String format(String text) {
        StringBuilder formattedCode = new StringBuilder();
        this.text = text;
        pos = 0;

        while (ch != EOF) {
            switch (ch) {
                case '<':
                    formattedCode.append(readAndFormatTag());
                    break;
                default:
                    formattedCode.append(ch);
                    nextChar();
                    break;
            }
        }

        return formattedCode.toString();
    }

    private boolean isHtmlLetter(char c) {
        return Character.isLetter(c) || Character.isDigit(c) || c == '-';
    }

    private String readWord() {
        var word = new StringBuilder();

        while (isHtmlLetter(ch)) {
            word.append(ch);
            nextChar();
        }

        return word.toString();
    }

    private String readStringLiteral() {
        var lit = new StringBuilder();
        var previousChar = '\u0000';

        while (ch != EOF) {
            lit.append(ch);
            previousChar = ch;
            nextChar();

            if (ch == '"' && previousChar != '\\') {
                break;
            }
        }
        lit.append('"');
        nextChar();

        return ConsoleColor.ANSI_GREEN + lit.toString() + ConsoleColor.ANSI_RESET;
    }

    private String readAndFormatTag() {
        var tag = new StringBuilder();

        tag.append(ch);
        nextChar();

        if (ch == '/') {
            tag.append(ch);
            nextChar();
        }

        tag.append(ConsoleColor.ANSI_RED + readWord() + ConsoleColor.ANSI_RESET);

        while (ch != '>') {
            if (Character.isLetter(ch)) {
                tag.append(ConsoleColor.ANSI_PURPLE + readWord() + ConsoleColor.ANSI_RESET);
            } else if (ch == '"') {
                tag.append(readStringLiteral());
            } else {
                tag.append(ch);
                nextChar();
            }
        }

        return tag.toString();
    }

    private void nextChar() {
        try {
            ch = text.charAt(pos++);
        } catch (StringIndexOutOfBoundsException e) {
            ch = EOF;
        }
    }
}
