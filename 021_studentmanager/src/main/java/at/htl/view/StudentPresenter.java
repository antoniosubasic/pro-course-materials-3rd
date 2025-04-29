package at.htl.view;

import at.htl.db.CourseRepository;
import at.htl.db.EnrollmentRepository;
import at.htl.db.StudentRepository;
import at.htl.model.Course;
import at.htl.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentPresenter {
    private StudentView view;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;

    private ObservableList<Student> students;
    private ObservableList<Course> courses;
    private ObservableList<Course> enrolledCourses;
    private ObservableList<Course> availableCourses;

    private Student currentStudent;
    private boolean isEditing = false;

    public StudentPresenter(StudentView view) {
        this.view = view;
        this.studentRepository = new StudentRepository();
        this.courseRepository = new CourseRepository();
        this.enrollmentRepository = new EnrollmentRepository();

        initializeLists();
        setupEventHandlers();
        refreshStudents();
    }

    private void initializeLists() {
        students = FXCollections.observableArrayList();
        courses = FXCollections.observableArrayList();
        enrolledCourses = FXCollections.observableArrayList();
        availableCourses = FXCollections.observableArrayList();

        view.getTableStudents().setItems(students);
        view.getListEnrolledCourses().setItems(enrolledCourses);
        view.getComboAvailableCourses().setItems(availableCourses);

        courses.addAll(courseRepository.findAll());
        availableCourses.addAll(courses);
    }

    private void setupEventHandlers() {
        view.getTableStudents().getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> onStudentSelected(newSelection));

        view.getBtnNew().setOnAction(e -> onNewClicked());
        view.getBtnEdit().setOnAction(e -> onEditClicked());
        view.getBtnSave().setOnAction(e -> onSaveClicked());
        view.getBtnDelete().setOnAction(e -> onDeleteClicked());
        view.getBtnClear().setOnAction(e -> onClearClicked());

        view.getBtnAddCourse().setOnAction(e -> onAddCourseClicked());
        view.getBtnRemoveCourse().setOnAction(e -> onRemoveCourseClicked());
    }

    private void refreshStudents() {
        students.clear();
        students.addAll(studentRepository.findAll());
    }

    private void refreshEnrolledCourses() {
        enrolledCourses.clear();
        if (currentStudent != null && currentStudent.getId() != null) {
            enrolledCourses.addAll(enrollmentRepository.getCoursesForStudent(currentStudent.getId()));
            updateAvailableCourses();
        }
    }

    private void updateAvailableCourses() {
        availableCourses.clear();
        for (Course course : courses) {
            if (enrolledCourses.stream().noneMatch(c -> c.getId().equals(course.getId()))) {
                availableCourses.add(course);
            }
        }
    }

    private void onStudentSelected(Student student) {
        if (student != null) {
            currentStudent = student;
            view.getTxtId().setText(String.valueOf(student.getId()));
            view.getTxtFirstName().setText(student.getFirstName());
            view.getTxtLastName().setText(student.getLastName());

            refreshEnrolledCourses();
            view.getBtnEdit().setDisable(false);
            view.getBtnDelete().setDisable(false);
        } else {
            view.clearForm();
            currentStudent = null;
            view.getBtnEdit().setDisable(true);
            view.getBtnDelete().setDisable(true);
        }

        isEditing = false;
        view.setFormEnabled(false);
        view.getBtnSave().setDisable(true);
    }

    private void onNewClicked() {
        view.clearForm();
        currentStudent = new Student();
        isEditing = false;
        view.setFormEnabled(true);
        view.getBtnSave().setDisable(false);
        enrolledCourses.clear();
    }

    private void onEditClicked() {
        if (currentStudent != null) {
            isEditing = true;
            view.setFormEnabled(true);
        }
    }

    private void onSaveClicked() {
        String firstName = view.getTxtFirstName().getText().trim();
        String lastName = view.getTxtLastName().getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            return;
        }

        if (currentStudent == null) {
            currentStudent = new Student();
        }

        currentStudent.setFirstName(firstName);
        currentStudent.setLastName(lastName);

        if (isEditing) {
            studentRepository.update(currentStudent);
        } else {
            currentStudent = studentRepository.create(currentStudent);
        }

        refreshStudents();
        view.getTableStudents().getSelectionModel().select(currentStudent);
        view.setFormEnabled(false);
        view.getBtnSave().setDisable(true);
        isEditing = false;
    }

    private void onDeleteClicked() {
        if (currentStudent != null && currentStudent.getId() != null) {
            studentRepository.deleteById(currentStudent.getId());
            refreshStudents();
            view.clearForm();
            currentStudent = null;
            view.getBtnEdit().setDisable(true);
            view.getBtnDelete().setDisable(true);
            enrolledCourses.clear();
        }
    }

    private void onClearClicked() {
        view.clearForm();
        view.getTableStudents().getSelectionModel().clearSelection();
        currentStudent = null;
        view.getBtnEdit().setDisable(true);
        view.getBtnDelete().setDisable(true);
        isEditing = false;
        view.setFormEnabled(false);
        enrolledCourses.clear();
    }

    private void onAddCourseClicked() {
        if (currentStudent == null || currentStudent.getId() == null) {
            return;
        }

        Course selectedCourse = view.getComboAvailableCourses().getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            return;
        }

        enrollmentRepository.enrollStudentInCourse(currentStudent.getId(), selectedCourse.getId());
        refreshEnrolledCourses();
    }

    private void onRemoveCourseClicked() {
        if (currentStudent == null || currentStudent.getId() == null) {
            return;
        }

        Course selectedCourse = view.getListEnrolledCourses().getSelectionModel().getSelectedItem();
        if (selectedCourse == null) {
            return;
        }

        enrollmentRepository.removeStudentFromCourse(currentStudent.getId(), selectedCourse.getId());
        refreshEnrolledCourses();
    }
}