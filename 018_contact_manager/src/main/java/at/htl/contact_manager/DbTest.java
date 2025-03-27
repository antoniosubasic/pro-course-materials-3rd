package at.htl.contact_manager;

import at.htl.contact_manager.database.ContactRepository;

public class DbTest {
    public static void main(String[] args) {
        var contactRepository = new ContactRepository();

        contactRepository.addContact("John Doe", "+123456789", "Main Street 1");
        contactRepository.addContact("Jane Doe", "+987654321", "Second Street 2");

        var contacts = contactRepository.getAllContacts();
        contacts.forEach(System.out::println);
    }
}
