public enum Gruppe {
    GESAMT("Gesamtgruppe"),
    QUALIFIKATION("Qualifikationsgruppe"),
    MEISTER("Meistergruppe");

    private final String name;

    Gruppe(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Gruppe fromName(String name) {
        for (var gruppe : Gruppe.values()) {
            if (gruppe.getName().equals(name)) {
                return gruppe;
            }
        }

        throw new IllegalArgumentException("unbekannter gruppen name: " + name);
    }
}
