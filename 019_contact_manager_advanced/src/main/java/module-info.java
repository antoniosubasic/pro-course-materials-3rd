module at.htl.contact_manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens at.htl.contact_manager to javafx.fxml;
    exports at.htl.contact_manager;
}