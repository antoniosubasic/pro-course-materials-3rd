package at.htl.contact_manager.view;

import at.htl.contact_manager.model.Contact;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ContactView {
    private final VBox root = new VBox();

    private final HBox hBoxSearch = new HBox();
    private final TextField tfSearchText = new TextField();
    private final Button btnSearch = new Button("Suchen");

    private final ListView<Contact> lvContacts = new ListView<>();

    private final HBox hBoxId = new HBox();
    private final Label lblId = new Label("Id:");
    private final TextField tfId = new TextField();

    private final HBox hBoxName = new HBox();
    private final Label lblName = new Label("Name:");
    private final TextField tfName = new TextField();

    private final HBox hBoxTelephone = new HBox();
    private final Label lblTelephone = new Label("Telephone:");
    private final TextField tfTelephone = new TextField();

    private final HBox hBoxAddress = new HBox();
    private final Label lblAddress = new Label("Address:");
    private final TextField tfAddress = new TextField();

    private final HBox changeButtons = new HBox();
    private final Button btnNew = new Button("Neu");
    private final Button btnEdit = new Button("Bearbeiten");
    private final Button btnSave = new Button("Speichern");
    private final Button btnDelete = new Button("Löschen");

    public ContactView() {
        init();
    }

    private void init() {
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        hBoxSearch.setSpacing(10);
        hBoxSearch.getChildren().addAll(tfSearchText, btnSearch);

        lvContacts.setPrefHeight(200);
        lvContacts.setPrefWidth(400);

        hBoxId.setSpacing(10);
        hBoxId.getChildren().addAll(lblId, tfId);
        lblId.setMinWidth(75);
        tfId.setDisable(true);

        hBoxName.setSpacing(10);
        hBoxName.getChildren().addAll(lblName, tfName);
        lblName.setMinWidth(75);
        tfName.setEditable(false);

        hBoxTelephone.setSpacing(10);
        hBoxTelephone.getChildren().addAll(lblTelephone, tfTelephone);
        lblTelephone.setMinWidth(75);
        tfTelephone.setEditable(false);

        hBoxAddress.setSpacing(10);
        hBoxAddress.getChildren().addAll(lblAddress, tfAddress);
        lblAddress.setMinWidth(75);
        tfAddress.setEditable(false);

        changeButtons.setSpacing(10);
        changeButtons.getChildren().addAll(btnNew, btnEdit, btnSave, btnDelete);
        btnSave.setDisable(true);

        root.getChildren().addAll(hBoxSearch, lvContacts, hBoxId, hBoxName, hBoxTelephone, hBoxAddress, changeButtons);
    }

    public VBox getRoot() { return root; }
    public TextField getTfSearchText() { return tfSearchText; }
    public Button getBtnSearch() { return btnSearch; }
    public ListView<Contact> getLvContacts() { return lvContacts; }
    public TextField getTfId() { return tfId; }
    public TextField getTfName() { return tfName; }
    public TextField getTfTelephone() { return tfTelephone; }
    public TextField getTfAddress() { return tfAddress; }
    public Button getBtnNew() { return btnNew; }
    public Button getBtnEdit() { return btnEdit; }
    public Button getBtnSave() { return btnSave; }
    public Button getBtnDelete() { return btnDelete; }
}
