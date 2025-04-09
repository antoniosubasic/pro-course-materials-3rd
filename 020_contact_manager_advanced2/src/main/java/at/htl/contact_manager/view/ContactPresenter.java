package at.htl.contact_manager.view;

import at.htl.contact_manager.database.ContactRepository;
import at.htl.contact_manager.database.LocationRepository;
import at.htl.contact_manager.model.Contact;
import at.htl.contact_manager.model.ContactType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContactPresenter {
    private final ContactView contactView;
    private final ContactRepository contactRepository;
    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private final Contact currentContact = new Contact();
    private final LocationRepository locationRepository;

    private ContactPresenter(ContactView contactView) {
        this.contactView = contactView;
        this.contactRepository = new ContactRepository();
        this.locationRepository = new LocationRepository();

        bindViewToModel();
        attachEvents();
        addListeners();
        init();
    }

    private void bindViewToModel() {
        contactView.getTfId().textProperty().bindBidirectional(
                currentContact.idProperty(),
                new NumberStringConverter());
        contactView.getTfName().textProperty().bindBidirectional(currentContact.nameProperty());
        contactView.getTfTelephone().textProperty().bindBidirectional(currentContact.phoneProperty());
        contactView.getTfAddress().textProperty().bindBidirectional(currentContact.addressProperty());
        contactView.getCbContactType().valueProperty().bindBidirectional(currentContact.typeProperty());
        contactView.getCbLocationPlz().valueProperty().bindBidirectional(currentContact.plzProperty());

        buildTreeView();
    }

    private void buildTreeView() {
        TreeItem<Object> root = new TreeItem<>(null); // Dummy-Root

        // Group contacts by type
        Map<ContactType, List<Contact>> contactsByType = contacts.stream()
                .collect(Collectors.groupingBy(Contact::getType));

        contactsByType.forEach((type, contacts) -> {
            // Create a node for each type
            TreeItem<Object> typeNode = new TreeItem<>(type);
            contacts.forEach(contact -> {
                // Create a node for each contact
                TreeItem<Object> contactNode = new TreeItem<>(contact);
                typeNode.getChildren().add(contactNode);
            });
            typeNode.setExpanded(true);
            root.getChildren().add(typeNode);
        });

        // set root node and hide it
        contactView.getTvContacts().setRoot(root);
        contactView.getTvContacts().setShowRoot(false);
    }

    private void attachEvents() {
        contactView.getBtnSearch().setOnAction(_ -> searchContact());
        contactView.getBtnNew().setOnAction(_ -> newContact());
        contactView.getBtnEdit().setOnAction(_ -> editContact());
        contactView.getBtnSave().setOnAction(_ -> saveContact());
        contactView.getBtnDelete().setOnAction(_ -> deleteContact());
    }

    private void searchContact() {
        var searchText = contactView.getTfSearchText().getText().toLowerCase();
        var root = contactView.getTvContacts().getRoot();

        for (var typeNode : root.getChildren()) {
            for (var contactNode : typeNode.getChildren()) {
                var contact = (Contact) contactNode.getValue();

                if (contact.getName().toLowerCase().contains(searchText)) {
                    contactView.getTvContacts().getSelectionModel().select(contactNode);
                    contactView.getTvContacts().scrollTo(contactView.getTvContacts().getRow(contactNode));
                    return;
                }
            }
        }
    }

    private void newContact() {
        clearFields();
        setEditMode(true);
    }

    private void editContact() {
        if (contactView.getTvContacts().getSelectionModel().getSelectedItem() != null) {
            setEditMode(true);
        }
    }

    private void saveContact() {
        var id = currentContact.getId();
        var idString = String.valueOf(id);
        var name = currentContact.getName();
        var phone = currentContact.getPhone();
        var address = currentContact.getAddress();
        var type = currentContact.getType();
        var locationPlz = currentContact.getPlz();

        if (idString != null && !idString.isEmpty() && !idString.equals("0")) {
            if (contacts.stream().anyMatch(contact -> contact.getId() == id)) {
                contactRepository.updateContact(id, name, phone, address, type, locationPlz);
                reloadContacts();
                for (var contact : contacts) {
                    if (contact.getId() == id) {
                        TreeItem<Object> contactNode = findTreeItemByContact(contact);
                        if (contactNode != null) {
                            contactView.getTvContacts().getSelectionModel().select(contactNode);
                            contactView.getTvContacts().scrollTo(contactView.getTvContacts().getRow(contactNode));
                        }
                        break;
                    }
                }
            }
        } else {
            contactRepository.addContact(name, phone, address, type, locationPlz);
            reloadContacts();
            TreeItem<Object> contactNode = findTreeItemByContact(contacts.getLast());
            if (contactNode != null) {
                contactView.getTvContacts().getSelectionModel().select(contactNode);
                contactView.getTvContacts().scrollTo(contactView.getTvContacts().getRow(contactNode));
            }
        }

        setEditMode(false);
    }

    private void deleteContact() {
        var id = currentContact.getId();
        contactRepository.deleteContact(id);
        reloadContacts();
        clearFields();
    }

    public static void show(Stage stage) {
        var view = new ContactView();
        new ContactPresenter(view);

        var scene = new Scene(view.getRoot());
        stage.setTitle("Contact Manager");
        stage.setScene(scene);
        stage.show();
    }

    private void addListeners() {
        contactView.getTvContacts().getSelectionModel().selectedItemProperty()
                .addListener((_, _, newValue) -> {
                    if (newValue != null && newValue.getValue() instanceof Contact) {
                        var contact = (Contact) newValue.getValue();

                        currentContact.setId(contact.getId());
                        currentContact.setName(contact.getName());
                        currentContact.setPhone(contact.getPhone());
                        currentContact.setAddress(contact.getAddress());
                        currentContact.setType(contact.getType());
                        currentContact.setPlz(contact.getPlz());

                        contactView.getLblLocationName().setText(
                                Optional.ofNullable(locationRepository.getLocation(contact.getPlz()))
                                        .map(location -> location.getName())
                                        .orElse(""));

                        setEditMode(false);
                    } else {
                        clearFields();
                    }
                });
    }

    private void init() {
        reloadContacts();
        buildTreeView();
    }

    private void reloadContacts() {
        contacts.clear();
        contacts.addAll(contactRepository.getAllContacts());
        buildTreeView();
    }

    private void clearFields() {
        currentContact.setId(0);
        currentContact.setName("");
        currentContact.setPhone("");
        currentContact.setAddress("");
        currentContact.setType(null);
        currentContact.setPlz(null);
        contactView.getLblLocationName().setText("");
    }

    private void setEditMode(boolean editMode) {
        contactView.getTfName().setEditable(editMode);
        contactView.getTfTelephone().setEditable(editMode);
        contactView.getTfAddress().setEditable(editMode);

        contactView.getCbContactType().setDisable(!editMode);
        contactView.getBtnSave().setDisable(!editMode);
        contactView.getCbLocationPlz().setDisable(!editMode);
    }

    private TreeItem<Object> findTreeItemByContact(Contact searchContact) {
        var root = contactView.getTvContacts().getRoot();
        for (var typeNode : root.getChildren()) {
            for (var contactNode : typeNode.getChildren()) {
                var contact = (Contact) contactNode.getValue();
                if (contact.getId() == searchContact.getId()) {
                    return contactNode;
                }
            }
        }
        return null;
    }
}
