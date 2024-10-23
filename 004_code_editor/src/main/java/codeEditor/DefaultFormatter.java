package codeEditor;

public class DefaultFormatter implements CodeFormatter {
    @Override
    public String format(String text) {
        return text;
    }
}
