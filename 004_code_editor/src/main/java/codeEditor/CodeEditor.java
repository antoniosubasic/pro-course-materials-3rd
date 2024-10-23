package codeEditor;

public class CodeEditor {
    private StringBuilder text;
    private CodeFormatter codeFormatter;

    public CodeEditor(CodeType codeType) {
        setCodeType(codeType);
        text = new StringBuilder();
    }

    public void setCodeType(CodeType codeType) {
        switch (codeType) {
            case FORMULA:
                codeFormatter = new FormulaFormatter();
                break;
            case HTML:
                codeFormatter = new HTMLFormatter();
                break;
            case JAVA:
                codeFormatter = new JavaFormatter();
                break;
            case DEFAULT:
                codeFormatter = new DefaultFormatter();
                break;
        }
    }

    public void appendLine(String line) {
        text.append(line);
        text.append("\n");
    }

    public void deleteAll() {
        text.delete(0, text.length());
    }

    public void print() {
        System.out.println(codeFormatter.format(text.toString()));
    }
}
