package at.htl.contact_manager.database;

import at.htl.contact_manager.model.Location;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository {
    private Connection connection;

    public LocationRepository() {
        connection = Database.getInstance().getConnection();
    }

    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        var sql = "SELECT * FROM location";

        try (var statement = connection.createStatement(); var rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                locations.add(new Location(
                        rs.getString("name"),
                        rs.getString("plz")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locations;
    }

    public void addLocation(String plz, String name) {
        var sql = "INSERT INTO location (plz, name) VALUES (?, ?)";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, plz);
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Location getLocation(String plz) {
        var sql = "SELECT * FROM location WHERE plz = ?";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, plz);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Location(
                            rs.getString("name"),
                            rs.getString("plz"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteLocation(String plz) {
        var sql = "DELETE FROM location WHERE plz = ?";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, plz);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
