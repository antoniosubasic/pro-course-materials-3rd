package at.htl.contact_manager;

import at.htl.contact_manager.database.ContactRepository;
import at.htl.contact_manager.database.LocationRepository;
import at.htl.contact_manager.model.ContactType;

public class DbTest {
    public static void main(String[] args) {
        var contactRepository = new ContactRepository();
        var locationRepository = new LocationRepository();

        contactRepository.addContact("John Doe", "+123456789", "Main Street 1", ContactType.NONE, null);
        contactRepository.addContact("Jane Doe", "+987654321", "Second Street 2", ContactType.BUSINESS, null);
        locationRepository.addLocation("4020", "Linz");
        locationRepository.addLocation("4060", "Leonding");
        locationRepository.addLocation("4050", "Traun");

        var contacts = contactRepository.getAllContacts();
        contacts.forEach(System.out::println);
        var locations = locationRepository.getAllLocations();
        locations.forEach(System.out::println);
    }
}
