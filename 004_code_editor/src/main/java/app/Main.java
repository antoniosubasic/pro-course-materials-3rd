package app;

import codeEditor.CodeEditor;
import codeEditor.CodeType;

public class Main {
    public static void main(String[] args) {
        CodeEditor editor = new CodeEditor(CodeType.HTML);
        editor.appendLine("<html lang=\"de\">");
        editor.appendLine("    <head>");
        editor.appendLine("        <meta charset=\"UTF-8\">");
        editor.appendLine("        <meta name=\"viewport\">");
        editor.appendLine("        <meta http-equiv=\"X-UA-Compatible\">");
        editor.appendLine("        <title>Meine kleine HTML-Seite</title>");
        editor.appendLine("    </head>");
        editor.appendLine("    <body>");
        editor.appendLine("        <h1>Willkommen auf meiner Seite</h1>");
        editor.appendLine("        <p>Hier steht ein bisschen Text.</p>");
        editor.appendLine("    </body>");
        editor.appendLine("</html>");
        editor.print();

        editor.deleteAll();

        editor.setCodeType(CodeType.JAVA);
        editor.appendLine("public class TestProgramm {");
        editor.appendLine("    public static void main(String[] args) {");
        editor.appendLine("        // A loop that iterates from 1 to 10");
        editor.appendLine("        for (int i = 1; i <= 10; i++) {");
        editor.appendLine("            // Check if the number is even or odd");
        editor.appendLine("            if (i % 2 == 0) {");
        editor.appendLine("                System.out.println(i + \" is an even number.\");");
        editor.appendLine("            } else {");
        editor.appendLine("                System.out.println(i + \" ist an odd number.\");");
        editor.appendLine("            }");
        editor.appendLine("        }");
        editor.appendLine("    }");
        editor.appendLine("}");
        editor.print();
    }
}