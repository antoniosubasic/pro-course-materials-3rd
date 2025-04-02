package at.htl.contact_manager;

import at.htl.contact_manager.database.ContactRepository;
import at.htl.contact_manager.model.ContactType;

public class DbTest {
    public static void main(String[] args) {
        var contactRepository = new ContactRepository();

        contactRepository.addContact("John Doe", "+123456789", "Main Street 1", ContactType.NONE);
        contactRepository.addContact("Jane Doe", "+987654321", "Second Street 2", ContactType.BUSINESS);

        var contacts = contactRepository.getAllContacts();
        contacts.forEach(System.out::println);
    }
}
