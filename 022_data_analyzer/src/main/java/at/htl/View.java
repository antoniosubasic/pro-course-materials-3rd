package at.htl;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class View {

    public static VBox createPrimaryView() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        HBox topRow = new HBox(15);
        topRow.setAlignment(Pos.CENTER);
        TextField urlField = new TextField();
        urlField.setPromptText("URL");
        TextField userField = new TextField();
        userField.setPromptText("User");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        Button connectButton = new Button("Connect");
        topRow.getChildren().addAll(urlField, userField, passwordField, connectButton);

        HBox centerRow = new HBox(15);
        centerRow.setAlignment(Pos.CENTER);
        TreeView<String> databaseTreeView = new TreeView<>();
        TableView<Object> dataTableView = new TableView<>();
        centerRow.getChildren().addAll(databaseTreeView, dataTableView);

        DatabaseController controller = new DatabaseController(urlField, userField, passwordField, databaseTreeView,
                dataTableView);
        connectButton.setOnAction(event -> controller.connectToDatabase());

        root.getChildren().addAll(topRow, centerRow);
        return root;
    }

    public static VBox createSecondaryView() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        Label label = new Label("Secondary View");
        Button switchButton = new Button("Switch to Primary View");
        root.getChildren().addAll(label, switchButton);
        return root;
    }
}
