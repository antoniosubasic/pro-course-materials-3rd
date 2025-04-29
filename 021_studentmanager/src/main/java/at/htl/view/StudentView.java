package at.htl.view;

import at.htl.model.Course;
import at.htl.model.Student;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

public class StudentView {
    private TextField txtId;
    private TextField txtFirstName;
    private TextField txtLastName;
    private TableView<Student> tableStudents;

    private ListView<Course> listEnrolledCourses;
    private ComboBox<Course> comboAvailableCourses;
    private Button btnAddCourse;
    private Button btnRemoveCourse;

    private Button btnNew;
    private Button btnEdit;
    private Button btnSave;
    private Button btnDelete;
    private Button btnClear;

    private BorderPane root;

    public StudentView() {
        initializeControls();
        layoutControls();
    }

    private void initializeControls() {
        txtId = new TextField();
        txtId.setEditable(false);
        txtId.setPromptText("ID");

        txtFirstName = new TextField();
        txtFirstName.setPromptText("First Name");

        txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");

        btnNew = new Button("New");
        btnEdit = new Button("Edit");
        btnSave = new Button("Save");
        btnDelete = new Button("Delete");
        btnClear = new Button("Clear");

        btnSave.setDisable(true);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);

        tableStudents = new TableView<>();
        setupTable();

        listEnrolledCourses = new ListView<>();
        setupCourseList();

        comboAvailableCourses = new ComboBox<>();
        setupCourseComboBox();

        btnAddCourse = new Button("+");
        btnRemoveCourse = new Button("-");
    }

    private void setupTable() {
        TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(50);

        TableColumn<Student, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setPrefWidth(150);

        TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setPrefWidth(150);

        tableStudents.getColumns().add(idColumn);
        tableStudents.getColumns().add(firstNameColumn);
        tableStudents.getColumns().add(lastNameColumn);

        tableStudents.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    private void setupCourseList() {
        listEnrolledCourses.setCellFactory(param -> new ListCell<Course>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                if (empty || course == null) {
                    setText(null);
                } else {
                    setText(course.getName() + " (" + course.getId() + ")");
                }
            }
        });

        listEnrolledCourses.setPrefHeight(150);
    }

    private void setupCourseComboBox() {
        comboAvailableCourses.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                if (course == null) {
                    return null;
                }
                return course.getName() + " (" + course.getId() + ")";
            }

            @Override
            public Course fromString(String string) {
                return null;
            }
        });

        comboAvailableCourses.setPromptText("Select course to add");
        comboAvailableCourses.setPrefWidth(250);
    }

    private void layoutControls() {
        VBox leftPanel = new VBox(15);
        leftPanel.setPadding(new Insets(15));

        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(0, 0, 10, 0));

        formPane.add(new Label("ID:"), 0, 0);
        formPane.add(txtId, 1, 0);

        formPane.add(new Label("First Name:"), 0, 1);
        formPane.add(txtFirstName, 1, 1);

        formPane.add(new Label("Last Name:"), 0, 2);
        formPane.add(txtLastName, 1, 2);

        leftPanel.getChildren().add(formPane);

        leftPanel.getChildren().add(new Label("Enrolled Courses:"));
        leftPanel.getChildren().add(listEnrolledCourses);

        leftPanel.getChildren().add(new Label("Course Assignment:"));

        HBox courseAssignmentBox = new HBox(10);
        courseAssignmentBox.setPadding(new Insets(5, 0, 15, 0));

        courseAssignmentBox.getChildren().addAll(comboAvailableCourses, btnAddCourse, btnRemoveCourse);
        leftPanel.getChildren().add(courseAssignmentBox);

        HBox buttonBar = new HBox(10);
        buttonBar.setPadding(new Insets(15, 0, 0, 0));
        buttonBar.getChildren().addAll(btnNew, btnEdit, btnSave, btnDelete, btnClear);
        leftPanel.getChildren().add(buttonBar);

        root = new BorderPane();
        root.setLeft(leftPanel);
        root.setCenter(tableStudents);

        BorderPane.setMargin(tableStudents, new Insets(15));
        BorderPane.setMargin(leftPanel, new Insets(0, 0, 0, 10));
    }

    public TextField getTxtId() {
        return txtId;
    }

    public TextField getTxtFirstName() {
        return txtFirstName;
    }

    public TextField getTxtLastName() {
        return txtLastName;
    }

    public TableView<Student> getTableStudents() {
        return tableStudents;
    }

    public ListView<Course> getListEnrolledCourses() {
        return listEnrolledCourses;
    }

    public ComboBox<Course> getComboAvailableCourses() {
        return comboAvailableCourses;
    }

    public Button getBtnAddCourse() {
        return btnAddCourse;
    }

    public Button getBtnRemoveCourse() {
        return btnRemoveCourse;
    }

    public Button getBtnNew() {
        return btnNew;
    }

    public Button getBtnEdit() {
        return btnEdit;
    }

    public Button getBtnSave() {
        return btnSave;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public Button getBtnClear() {
        return btnClear;
    }

    public BorderPane getRoot() {
        return root;
    }

    public void clearForm() {
        txtId.clear();
        txtFirstName.clear();
        txtLastName.clear();
    }

    public void setFormEnabled(boolean enabled) {
        txtFirstName.setDisable(!enabled);
        txtLastName.setDisable(!enabled);
        btnSave.setDisable(!enabled);
    }
}
