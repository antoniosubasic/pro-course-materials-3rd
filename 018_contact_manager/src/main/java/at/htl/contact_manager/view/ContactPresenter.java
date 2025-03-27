package at.htl.contact_manager.view;

import at.htl.contact_manager.database.ContactRepository;
import at.htl.contact_manager.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        contactView.getLvContacts().setItems(contacts);
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

        if (!searchText.isEmpty()) {
            for (var contact : contacts) {
                if (contact.getName().toLowerCase().contains(searchText)) {
                    contactView.getLvContacts().getSelectionModel().select(contact);
                    contactView.getLvContacts().scrollTo(contact);
                    break;
                }
            }
        }
    }

    private void newContact() {
        clearFields();
        setEditMode(true);
    }

    private void editContact() {
        if (contactView.getLvContacts().getSelectionModel().getSelectedItem() != null) {
            setEditMode(true);
        }
    }

    private void saveContact() {
        var idString = contactView.getTfId().getText();
        var name = contactView.getTfName().getText();
        var phone = contactView.getTfTelephone().getText();
        var address = contactView.getTfAddress().getText();

        if (idString != "") {
            var id = Integer.parseInt(idString);

            if (contacts.stream().anyMatch(contact -> contact.getId() == id)) {
                contactRepository.updateContact(id, name, phone, address);
                reloadContacts();
                for (var contact : contacts) {
                    if (contact.getId() == id) {
                        contactView.getLvContacts().getSelectionModel().select(contact);
                        contactView.getLvContacts().scrollTo(contact);
                        break;
                    }
                }
            }
        } else {
            contactRepository.addContact(name, phone, address);
            reloadContacts();
            contactView.getLvContacts().getSelectionModel().select(contacts.getLast());
            contactView.getLvContacts().scrollTo(contacts.getLast());
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
        contactView.getLvContacts().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                contactView.getTfId().setText(String.valueOf(newValue.getId()));
                contactView.getTfName().setText(newValue.getName());
                contactView.getTfTelephone().setText(newValue.getPhone());
                contactView.getTfAddress().setText(newValue.getAddress());

                setEditMode(false);
            } else {
                clearFields();
            }
        });
    }

    private void init() {
        reloadContacts();
    }

    private void reloadContacts() {
        contacts.clear();
        contacts.addAll(contactRepository.getAllContacts());
    }

    private void clearFields() {
        contactView.getTfId().clear();
        contactView.getTfName().clear();
        contactView.getTfTelephone().clear();
        contactView.getTfAddress().clear();
    }

    private void setEditMode(boolean editMode) {
        contactView.getTfName().setEditable(editMode);
        contactView.getTfTelephone().setEditable(editMode);
        contactView.getTfAddress().setEditable(editMode);

        contactView.getBtnSave().setDisable(!editMode);
    }
}
