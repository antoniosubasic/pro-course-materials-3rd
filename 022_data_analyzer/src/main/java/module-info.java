module at.htl {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;

    opens at.htl to javafx.fxml;

    exports at.htl;
}
