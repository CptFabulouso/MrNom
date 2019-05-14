package phoneapp;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class AllContacts {

    // This class will only have one instance that will
    // contain an arraylist with all contacts in it.
    // Singleton
    private static AllContacts allContacts;

    // By creating a Context you gain access to the
    // current state of the complete application.
    // With it you can get information about all the Activitys
    // in the app among other things.

    // By accessing the Context you control every part of
    // the application along with everything that app
    // is allowed to access on the device.

    private Context applicationContext;

    private ArrayList<Contact> contactList;

    private AllContacts(Context apllicationContext) {

        this.applicationContext = apllicationContext;

        contactList = new ArrayList<Contact>();

        Contact paulSmith = new Contact();
        paulSmith.setName("Paul Smith");
        paulSmith.setStreetAddress("123 Main St");
        paulSmith.setContacted(true);
        contactList.add(paulSmith);

        Contact sallySmith = new Contact();
        sallySmith.setName("Sally Smith");
        sallySmith.setStreetAddress("125 Main St");
        sallySmith.setContacted(false);
        contactList.add(sallySmith);

        Contact markSmith = new Contact();
        markSmith.setName("Mark Smith");
        markSmith.setStreetAddress("127 Main St");
        markSmith.setContacted(false);
        contactList.add(markSmith);
    }

    // Checks if an instance of allContacts exists. If it does
    // the one instance is returned. Otherwise the instance is
    // created.

    public static AllContacts get(Context context) {

        if (allContacts == null) {
            // getApplicationContext returns the global Application object
            // This Context is global to every part of the application
            allContacts = new AllContacts(context.getApplicationContext());
        }
        return allContacts;

    }

    public ArrayList<Contact> getContactList() {
        return contactList;

    }

    public Contact getContact(UUID id) {

        for (Contact theContact : contactList) {
            if (theContact.getIdNumber().equals(id)) {
                return theContact;
            }
        }
        return null;
    }

}
