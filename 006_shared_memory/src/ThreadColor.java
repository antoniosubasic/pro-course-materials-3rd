public enum ThreadColor {
    ANSI_RESET("\u001B[0m"),
    ANSI_RED("\u001B[31m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_GREEN("\u001B[32m");

    private final String color;

    ThreadColor(String color) {
        this.color = color;
    }

    public String color() {
        return color;
    }
}