package at.htl.contact_manager;

import at.htl.contact_manager.view.ContactPresenter;
import javafx.application.Application;
import javafx.stage.Stage;

public class ContactManagerApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        ContactPresenter.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
