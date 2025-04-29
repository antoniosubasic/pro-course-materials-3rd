package at.htl.db;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:h2:tcp://localhost:9092/./studentdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private static Database instance;
    private Connection connection;

    private Database() {
        initializeDatabase();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void initializeDatabase() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS students (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "first_name VARCHAR(100) NOT NULL," +
                        "last_name VARCHAR(100) NOT NULL" +
                    ")");

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS courses (" +
                        "id VARCHAR(20) PRIMARY KEY," +
                        "name VARCHAR(100) NOT NULL" +
                    ")");

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS courses_of_students (" +
                        "student_id INT NOT NULL," +
                        "course_id VARCHAR(20) NOT NULL," +
                        "PRIMARY KEY (student_id, course_id)," +
                        "FOREIGN KEY (student_id) REFERENCES students(id)," +
                        "FOREIGN KEY (course_id) REFERENCES courses(id)" +
                    ")");
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                instance = null;
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}