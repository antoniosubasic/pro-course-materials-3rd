module at.htl {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;

    opens at.htl to javafx.fxml;
    exports at.htl;

    opens at.htl.model;
}
