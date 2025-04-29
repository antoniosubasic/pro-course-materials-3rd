package at.htl.db;

import at.htl.model.Course;
import at.htl.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {
    private Connection connection;

    public EnrollmentRepository() {
        connection = Database.getInstance().getConnection();
    }

    public boolean enrollStudentInCourse(Integer studentId, String courseId) {
        String sql = "INSERT INTO courses_of_students (student_id, course_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, courseId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error enrolling student in course: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeStudentFromCourse(Integer studentId, String courseId) {
        String sql = "DELETE FROM courses_of_students WHERE student_id = ? AND course_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, courseId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error removing student from course: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Course> getCoursesForStudent(Integer studentId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.* FROM courses c " +
                "JOIN courses_of_students cos ON c.id = cos.course_id " +
                "WHERE cos.student_id = ? " +
                "ORDER BY c.name";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    courses.add(new Course(id, name));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting courses for student: " + e.getMessage());
            e.printStackTrace();
        }

        return courses;
    }

    public List<Student> getStudentsInCourse(String courseId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.* FROM students s " +
                "JOIN courses_of_students cos ON s.id = cos.student_id " +
                "WHERE cos.course_id = ? " +
                "ORDER BY s.last_name, s.first_name";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    students.add(new Student(id, firstName, lastName));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting students in course: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }

    public boolean isStudentEnrolledInCourse(Integer studentId, String courseId) {
        String sql = "SELECT 1 FROM courses_of_students WHERE student_id = ? AND course_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking student enrollment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}