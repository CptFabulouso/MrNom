package phoneapp;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.mrnom.R;

public class FragmentContactList extends ListFragment {

    private ArrayList<Contact> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.fragment_contact_list_item);

        contactList = AllContacts.get(getActivity()).getContactList();

        // Get the ArrayList from AllContacts
        ContactAdapter contactAdapter = new ContactAdapter(contactList);


        // Provides the data for the ListView by setting the Adapter
        setListAdapter(contactAdapter);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                Toast.makeText(getActivity(), "On long click listener", Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Contact clickedContact = ((ContactAdapter) getListAdapter())
                .getItem(position);

        // when we called the phoneApp class
        // Intent newIntent = new Intent(getActivity(), PhoneApp.class);

        Intent newIntent = new Intent(getActivity(), ContactViewPager.class);

        newIntent.putExtra(ContactFragment.CONTACT_ID,
                clickedContact.getIdNumber());

		/*
         * when we called the phoneApp class startActivityForResult(newIntent,
		 * 0);
		 */
        // startActivity(newIntent);
        startActivityForResult(newIntent, 0);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        ((ContactAdapter) getListAdapter()).notifyDataSetChanged();

    }

    private class ContactAdapter extends ArrayAdapter<Contact> {

        // An Adapter acts as a bridge between an AdapterView and the
        // data for that view. The Adapter also makes a View for each
        // item in the data set. (Each list item in our ListView)

        // The constructor gets a Context so it can use the
        // resource being the simple_list_item and the ArrayList
        // android.R.layout.simple_list_item_1 is a predefined
        // layout provided by Android that stands in as a default

        public ContactAdapter(ArrayList<Contact> contacts) {
            super(getActivity(), android.R.layout.simple_list_item_1, contacts);
        }

        // getView is called each time it needs to display a new list item
        // on the screen because of scrolling for example.
        // The Adapter is asked for the new list row and getView provides
        // it.
        // position represents the position in the Array from which we will
        // be pulling data.
        // convertView is a pre-created list item that will be reconfigured
        // in the code that follows.
        // ViewGroup is our ListView

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Check if this is a recycled list item and if not we inflate it
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.list_item_contact, null);
            }

            // Find the right data to put in the list item
            Contact theContact = getItem(position);

            // Put the right data into the right components
            TextView contactNameTextView = (TextView) convertView
                    .findViewById(R.id.contactName);
            contactNameTextView.setText(theContact.getName());

            TextView contactStreetTextView = (TextView) convertView
                    .findViewById(R.id.contactStreet);
            contactStreetTextView.setText(theContact.getStreetAddress());

            CheckBox contactedCheckBox = (CheckBox) convertView
                    .findViewById(R.id.contact_contactedCheckBox);
            contactedCheckBox.setChecked(theContact.isContacted());

            // Return the finished list item for display

            return convertView;
        }

    }

}
