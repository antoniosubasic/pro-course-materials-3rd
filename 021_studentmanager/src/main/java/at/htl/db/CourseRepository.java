package at.htl.db;

import at.htl.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private Connection connection;

    public CourseRepository() {
        connection = Database.getInstance().getConnection();
    }

    public Course create(Course course) {
        String sql = "INSERT INTO courses (id, name) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, course.getId());
            stmt.setString(2, course.getName());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating course failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating course: " + e.getMessage());
            e.printStackTrace();
        }

        return course;
    }

    public Course findById(String id) {
        String sql = "SELECT * FROM courses WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCourse(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding course by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY name";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all courses: " + e.getMessage());
            e.printStackTrace();
        }

        return courses;
    }

    public boolean update(Course course) {
        if (course.getId() == null) {
            return false;
        }

        String sql = "UPDATE courses SET name = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating course: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(String id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting course: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Course course) {
        if (course.getId() == null) {
            return false;
        }
        return deleteById(course.getId());
    }

    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");

        return new Course(id, name);
    }

    public void close() {
        Database.getInstance().close();
    }
}