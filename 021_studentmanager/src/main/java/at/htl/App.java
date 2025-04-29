package at.htl;

import at.htl.view.StudentPresenter;
import at.htl.view.StudentView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        StudentView view = new StudentView();

        StudentPresenter presenter = new StudentPresenter(view);

        Scene scene = new Scene(view.getRoot(), 900, 600);
        stage.setScene(scene);
        stage.setTitle("Student Management System");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}