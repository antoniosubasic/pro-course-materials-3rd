package at.htl.contact_manager.view;

import at.htl.contact_manager.database.LocationRepository;
import at.htl.contact_manager.model.ContactType;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ContactView {
    private final VBox root = new VBox();

    private final HBox hBoxSearch = new HBox();
    private final TextField tfSearchText = new TextField();
    private final Button btnSearch = new Button("Suchen");

    private final TreeView<Object> tvContacts = new TreeView<>();

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

    private final HBox hBoxContactType = new HBox();
    private final Label lblContactType = new Label("Contact Type:");
    private final ComboBox<ContactType> cbContactType = new ComboBox<>();

    private final HBox hBoxLocation = new HBox();
    private final Label lblLocation = new Label("Location:");
    private final ComboBox<String> cbLocationPlz = new ComboBox<>();
    private final Label lblLocationName = new Label();

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

        tvContacts.setPrefHeight(200);
        tvContacts.setPrefWidth(400);

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

        hBoxContactType.setSpacing(10);
        hBoxContactType.getChildren().addAll(lblContactType, cbContactType);
        lblContactType.setMinWidth(75);
        cbContactType.setDisable(true);

        hBoxLocation.setSpacing(10);
        hBoxLocation.getChildren().addAll(lblLocation, cbLocationPlz, lblLocationName);
        lblLocation.setMinWidth(75);
        cbLocationPlz.setDisable(true);

        changeButtons.setSpacing(10);
        changeButtons.getChildren().addAll(btnNew, btnEdit, btnSave, btnDelete);
        btnSave.setDisable(true);

        root.getChildren().addAll(
                hBoxSearch,
                tvContacts,
                hBoxId,
                hBoxName,
                hBoxTelephone,
                hBoxAddress,
                hBoxContactType,
                hBoxLocation,
                changeButtons);

        cbContactType.getItems().addAll(ContactType.values());

        var locationRepository = new LocationRepository();
        cbLocationPlz.getItems()
                .addAll(locationRepository.getAllLocations().stream().map(location -> location.getPlz()).toList());
    }

    public VBox getRoot() {
        return root;
    }

    public TextField getTfSearchText() {
        return tfSearchText;
    }

    public Button getBtnSearch() {
        return btnSearch;
    }

    public TreeView<Object> getTvContacts() {
        return tvContacts;
    }

    public TextField getTfId() {
        return tfId;
    }

    public TextField getTfName() {
        return tfName;
    }

    public TextField getTfTelephone() {
        return tfTelephone;
    }

    public TextField getTfAddress() {
        return tfAddress;
    }

    public ComboBox<ContactType> getCbContactType() {
        return cbContactType;
    }

    public ComboBox<String> getCbLocationPlz() {
        return cbLocationPlz;
    }

    public Label getLblLocationName() {
        return lblLocationName;
    }

    public Button getBtnNew() {
        return btnNew;
    }

    public Button getBtnEdit() {
        return btnEdit;
    }

    public Button getBtnSave() {
        return btnSave;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }
}
