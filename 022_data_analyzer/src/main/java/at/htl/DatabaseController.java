package at.htl;

import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {

    private TextField urlField;
    private TextField userField;
    private TextField passwordField;
    private TreeView<String> databaseTreeView;
    private TableView<Object> dataTableView;

    private Connection connection;

    public DatabaseController(TextField urlField, TextField userField, TextField passwordField,
            TreeView<String> databaseTreeView, TableView<Object> dataTableView) {
        this.urlField = urlField;
        this.userField = userField;
        this.passwordField = passwordField;
        this.databaseTreeView = databaseTreeView;
        this.dataTableView = dataTableView;
    }

    public void connectToDatabase() {
        String url = urlField.getText();
        String user = userField.getText();
        String password = passwordField.getText();

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(url, user, password);
            populateTreeView();
        } catch (ClassNotFoundException e) {
            showAlert("Driver Error", "JDBC Driver not found: " + e.getMessage());
        } catch (Exception e) {
            showAlert("Connection Error", "Could not connect to the database: " + e.getMessage());
        }
    }

    private void populateTreeView() {
        try {
            TreeItem<String> rootItem = new TreeItem<>("Connection");
            Statement statement = connection.createStatement();

            ResultSet tables = statement.executeQuery("SHOW TABLES");
            TreeItem<String> tablesItem = new TreeItem<>("Tables");
            while (tables.next()) {
                TreeItem<String> tableItem = new TreeItem<>("Table: " + tables.getString(1));
                tablesItem.getChildren().add(tableItem);
            }

            ResultSet views = statement
                    .executeQuery(
                            "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'VIEW' AND TABLE_SCHEMA = 'PUBLIC'");
            TreeItem<String> viewsItem = new TreeItem<>("Views");
            while (views.next()) {
                TreeItem<String> viewItem = new TreeItem<>("View: " + views.getString(1));
                viewsItem.getChildren().add(viewItem);
            }

            List<TreeItem<String>> children = new ArrayList<>();
            children.add(tablesItem);
            children.add(viewsItem);
            rootItem.getChildren().addAll(children);

            databaseTreeView.setRoot(rootItem);

            databaseTreeView.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        if (newValue != null && newValue.isLeaf()) {
                            String value = newValue.getValue();
                            if (value.startsWith("Table: ")) {
                                loadTableData(value.replace("Table: ", ""));
                            }
                        }
                    });
        } catch (Exception e) {
            showAlert("Error", "Could not populate TreeView: " + e.getMessage());
        }
    }

    private void loadTableData(String tableName) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

            dataTableView.getColumns().clear();
            dataTableView.getItems().clear();

            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                final int columnIndex = i;
                TableColumn<Object, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i));
                column.setCellValueFactory(cellData -> {
                    Object[] row = (Object[]) cellData.getValue();
                    return new SimpleStringProperty(
                            row[columnIndex - 1] != null ? row[columnIndex - 1].toString() : "");
                });
                dataTableView.getColumns().add(column);
            }

            while (resultSet.next()) {
                Object[] row = new Object[resultSet.getMetaData().getColumnCount()];
                for (int i = 0; i < row.length; i++) {
                    row[i] = resultSet.getObject(i + 1);
                }
                dataTableView.getItems().add(row);
            }
        } catch (Exception e) {
            showAlert("Error", "Could not load table data: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
