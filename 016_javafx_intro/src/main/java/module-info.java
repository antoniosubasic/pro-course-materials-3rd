module htl.at {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;


    opens htl.at to javafx.fxml;
    exports htl.at;
}