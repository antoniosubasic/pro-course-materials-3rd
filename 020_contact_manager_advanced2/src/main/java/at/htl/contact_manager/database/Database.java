package at.htl.contact_manager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:h2:tcp://localhost:9092/./contactDb";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private static Database instance;
    private static Connection connection;

    private Database() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void initialize() {
        var createLocationTable = """
                CREATE TABLE IF NOT EXISTS location (
                    plz VARCHAR(10) PRIMARY KEY,
                    name VARCHAR(255) NOT NULL
                )
                """;

        var createContactTable = """
                CREATE TABLE IF NOT EXISTS contact (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    phone VARCHAR(255),
                    address VARCHAR(255),
                    contactType VARCHAR(20),
                    locationPlz VARCHAR(10),
                    FOREIGN KEY (locationPlz) REFERENCES location(plz)
                )
                """;

        try (var statement = connection.createStatement()) {
            statement.execute(createLocationTable);
            statement.execute(createContactTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
