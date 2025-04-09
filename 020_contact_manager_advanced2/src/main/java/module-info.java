module at.htl.contact_manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires transitive javafx.graphics;

    opens at.htl.contact_manager to javafx.fxml;
    exports at.htl.contact_manager;
}