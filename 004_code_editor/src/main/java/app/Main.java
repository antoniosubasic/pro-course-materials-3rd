package app;

import codeEditor.FormulaEditor;

public class Main {
    public static void main(String[] args) {
        FormulaEditor editor = new FormulaEditor();
        editor.appendLine("1: a^2 + b^2 = c^2");
        editor.appendLine("2: sin(x) + cos(x) = 1");
        editor.appendLine("3: y = 2*a*b*cos(gamma)");
        editor.appendLine("IV: A = r^2 * pi");
        editor.appendLine("V: f(x) = 1.245 * x^2 + 3.45");
        editor.print();
    }
}