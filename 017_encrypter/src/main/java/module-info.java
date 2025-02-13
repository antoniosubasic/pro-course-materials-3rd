module htl.encrypter {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;


    opens htl.encrypter to javafx.fxml;
    exports htl.encrypter;
}