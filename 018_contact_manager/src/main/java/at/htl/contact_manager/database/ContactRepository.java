package at.htl.contact_manager.database;

import at.htl.contact_manager.model.Contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository {
    private Connection connection;

    public ContactRepository() {
        connection = Database.getInstance().getConnection();
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        var sql = "SELECT * FROM contact";

        try (var statement = connection.createStatement(); var rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                contacts.add(new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public void addContact(String name, String phone, String address) {
        var sql = "INSERT INTO contact (name, phone, address) VALUES (?, ?, ?)";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContact(int id, String name, String phone, String address) {
        var sql = "UPDATE contact SET name = ?, phone = ?, address = ? WHERE id = ?";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContact(int id) {
        var sql = "DELETE FROM contact WHERE id = ?";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
