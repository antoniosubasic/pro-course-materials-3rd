package codeEditor;

import java.util.Arrays;
import java.util.List;

public class FormulaEditor {

    private StringBuilder text;

    private static final char EOF = (char) -1;
    private static final List<String> FUNCTIONS = Arrays.asList(
            "sin", "cos", "tan", "sqrt", "log", "ln", "exp"
    );

    private int pos;
    private char ch;

    public FormulaEditor() {
        text = new StringBuilder();
    }


    public void appendLine(String line) {
        text.append(line);
        text.append("\n");
    }

    public void deleteAll() {
        text.delete(0, text.length());
    }

    public void print() {
        System.out.println(format());
    }

    public String format() {
        StringBuilder formattedCode = new StringBuilder();
        pos = 0;
        boolean newLine = true;
        nextChar();

        while (ch != EOF) {
            if (newLine) {
                // read formated formula number
                formattedCode.append(readAndFormatFormulaNumber());
                newLine = false;
            } else {
                switch (ch) {
                    // check for number, which is a sequence of digits
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
                        // read and format number
                        formattedCode.append(ConsoleColor.ANSI_BLUE + readNumber() + ConsoleColor.ANSI_RESET);
                        break;
                    // check for variable, which starts with a letter and can contain letters and digits
                    case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                         'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                         'w', 'x', 'y', 'z',
                         'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                         'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                            'W', 'X', 'Y', 'Z':
                        // read and format word
                        formattedCode.append(readAndFormatWord());
                        break;
                    case '\n':
                        formattedCode.append(ch);
                        nextChar();
                        newLine = true;
                        break;
                    default:
                        formattedCode.append(ch);
                        nextChar();
                        break;
                }
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

    private String readAndFormatFormulaNumber() {
        StringBuilder formulaNumber = new StringBuilder();
        // change color to red
        formulaNumber.append(ConsoleColor.ANSI_RED);

        // read and append formula number (all characters until ':')
        while (ch != ':' && ch != EOF) {
            formulaNumber.append(ch);
            nextChar();
        }

        // reset color
        formulaNumber.append(ConsoleColor.ANSI_RESET);
        // append ':'
        formulaNumber.append(ch);
        // read next character
        nextChar();
        return formulaNumber.toString();
    }

    private String readNumber() {
        StringBuilder number = new StringBuilder();
        while (Character.isDigit(ch) || ch == '.') {
            number.append(ch);
            nextChar();
        }
        return number.toString();
    }

    private String readAndFormatWord() {
        StringBuilder variable = new StringBuilder();
        while (Character.isLetter(ch)) {
            variable.append(ch);
            nextChar();
        }

        // check if variable is a function or a variable and format it accordingly
        if (FUNCTIONS.contains(variable.toString())) {
            return ConsoleColor.ANSI_GREEN + variable.toString() + ConsoleColor.ANSI_RESET;
        } else {
            return ConsoleColor.ANSI_PURPLE + variable.toString() + ConsoleColor.ANSI_RESET;
        }

    }

}
