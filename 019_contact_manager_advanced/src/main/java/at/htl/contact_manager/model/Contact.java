package at.htl.contact_manager.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Contact {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty phone = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private ObjectProperty<ContactType> type = new SimpleObjectProperty<>();

    public Contact() {
    }

    public Contact(int id, String name, String phone, String address, ContactType type) {
        setId(id);
        setName(name);
        setPhone(phone);
        setAddress(address);
        setType(type);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public ContactType getType() {
        return this.type.get();
    }

    public void setType(ContactType type) {
        this.type.set(type);
    }

    @Override
    public String toString() {
        return "%d: %s (%s, %s)".formatted(getId(), getName(), getPhone(), getAddress());
    }
}
