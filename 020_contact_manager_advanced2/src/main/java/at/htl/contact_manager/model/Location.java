package at.htl.contact_manager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty plz = new SimpleStringProperty();

    public Location() {
    }

    public Location(String name, String plz) {
        setName(name);
        setPlz(plz);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPlz() {
        return plz.get();
    }

    public StringProperty plzProperty() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz.set(plz);
    }

    @Override
    public String toString() {
        return "%s (%s)".formatted(getName(), getPlz());
    }
}
