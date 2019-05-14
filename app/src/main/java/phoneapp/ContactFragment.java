package phoneapp;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.jerry.mrnom.R;

public class ContactFragment extends Fragment {

    public static final String CONTACT_ID = "com.jerry.phoneapp.contact_id";

    private Contact contact;
    private EditText contactNameEditText;
    private EditText contactStreetEditText;
    private EditText contactCityEditText;
    private EditText contactPhoneEditText;
    private CheckBox contactedCheckBox;

    private static final String DATE_OF_BIRTH = "Date of Birth";
    private EditText contactBirthdayEditText;
    private static final int REQUEST_DATE = 0;

    public static ContactFragment newContactFragment(UUID contactId) {
        Bundle passedData = new Bundle();

        passedData.putSerializable(CONTACT_ID, contactId);

        ContactFragment contactFragment = new ContactFragment();
        contactFragment.setArguments(passedData);

        return contactFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        UUID contactId = (UUID) getArguments().getSerializable(CONTACT_ID);

        contact = AllContacts.get(getActivity()).getContact(contactId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.fragment_contact, container,
                false);

        contactNameEditText = (EditText) theView
                .findViewById(R.id.contactNameEditText);
        contactStreetEditText = (EditText) theView
                .findViewById(R.id.contactStreetEditText);
        contactCityEditText = (EditText) theView
                .findViewById(R.id.contactCityEditText);
        contactPhoneEditText = (EditText) theView
                .findViewById(R.id.contactPhoneEditText);

        contactedCheckBox = (CheckBox) theView.findViewById(R.id.contactedCheckBox);

        contactBirthdayEditText = (EditText) theView
                .findViewById(R.id.contactBirthdayEditText);
        contactBirthdayEditText.setText(contact.getDateString());
        contactBirthdayEditText.setInputType(InputType.TYPE_NULL);

        contactBirthdayEditText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager manager = getActivity().getSupportFragmentManager();

                DateDialogFragment dateDialog = DateDialogFragment.newInstance(contact.getDateOfBirth());

                dateDialog.setTargetFragment(ContactFragment.this, REQUEST_DATE);

                dateDialog.show(manager, DATE_OF_BIRTH);
            }
        });

        contactedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                contact.setContacted(isChecked);

            }
        });


        contactNameEditText.setText(contact.getName());
        contactStreetEditText.setText(contact.getStreetAddress());
        contactCityEditText.setText(contact.getCity());
        contactPhoneEditText.setText(contact.getPhoneNumber());
        contactedCheckBox.setChecked(contact.isContacted());


        TextWatcher editTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before,
                                      int count) {
                if (contactNameEditText.hasFocus() == true) {

                    contact.setName(arg0.toString());

                } else if (contactStreetEditText.hasFocus() == true) {

                    contact.setStreetAddress(arg0.toString());

                } else if (contactCityEditText.hasFocus() == true) {

                    contact.setCity(arg0.toString());

                } else if (contactPhoneEditText.hasFocus() == true) {

                    contact.setPhoneNumber(arg0.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        };

        contactNameEditText.addTextChangedListener(editTextWatcher);
        contactStreetEditText.addTextChangedListener(editTextWatcher);
        contactCityEditText.addTextChangedListener(editTextWatcher);
        contactPhoneEditText.addTextChangedListener(editTextWatcher);


        return theView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        } else if (requestCode == REQUEST_DATE) {

            Date birthDate = (Date) data.getSerializableExtra(DateDialogFragment.CONTACT_BIRTHDAY);

            contact.setDateOfBirth(birthDate);

            contactBirthdayEditText.setText(contact.getDateString());


        }

    }


}
