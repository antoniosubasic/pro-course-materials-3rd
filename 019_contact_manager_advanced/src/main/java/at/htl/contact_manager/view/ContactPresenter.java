package at.htl.contact_manager.view;

import at.htl.contact_manager.database.ContactRepository;
import at.htl.contact_manager.model.Contact;
import at.htl.contact_manager.model.ContactType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactPresenter {
    private final ContactView contactView;
    private final ContactRepository contactRepository;
    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

    private ContactPresenter(ContactView contactView) {
        this.contactView = contactView;
        this.contactRepository = new ContactRepository();

        bindViewToModel();
        attachEvents();
        addListeners();
        init();
    }

    private void bindViewToModel() {
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
        var idString = contactView.getTfId().getText();
        var name = contactView.getTfName().getText();
        var phone = contactView.getTfTelephone().getText();
        var address = contactView.getTfAddress().getText();
        var type = contactView.getCbContactType().getValue();

        if (idString != "") {
            var id = Integer.parseInt(idString);

            if (contacts.stream().anyMatch(contact -> contact.getId() == id)) {
                contactRepository.updateContact(id, name, phone, address, type);
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
            contactRepository.addContact(name, phone, address, type);
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
        var id = Integer.parseInt(contactView.getTfId().getText());
        contactRepository.deleteContact(id);
        reloadContacts();
        clearFields();
    }

    public static void show(Stage stage) {
        var view = new ContactView();
        var presenter = new ContactPresenter(view);

        var scene = new Scene(view.getRoot());
        stage.setTitle("Contact Manager");
        stage.setScene(scene);
        stage.show();
    }

    private void addListeners() {
        contactView.getTvContacts().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getValue() instanceof Contact) {
                var contact = (Contact) newValue.getValue();

                contactView.getTfId().setText(String.valueOf(contact.getId()));
                contactView.getTfName().setText(contact.getName());
                contactView.getTfTelephone().setText(contact.getPhone());
                contactView.getTfAddress().setText(contact.getAddress());
                contactView.getCbContactType().setValue(contact.getType());

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
        contactView.getTfId().clear();
        contactView.getTfName().clear();
        contactView.getTfTelephone().clear();
        contactView.getTfAddress().clear();
        contactView.getCbContactType().setValue(null);
    }

    private void setEditMode(boolean editMode) {
        contactView.getTfName().setEditable(editMode);
        contactView.getTfTelephone().setEditable(editMode);
        contactView.getTfAddress().setEditable(editMode);

        contactView.getCbContactType().setDisable(!editMode);
        contactView.getBtnSave().setDisable(!editMode);
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
