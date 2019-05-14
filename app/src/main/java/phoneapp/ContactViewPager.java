package phoneapp;

import java.util.ArrayList;
import java.util.UUID;

import com.jerry.mrnom.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class ContactViewPager extends FragmentActivity {

    private ViewPager theViewPager;
    private ArrayList<Contact> contactList;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        theViewPager = new ViewPager(this);

        theViewPager.setId(R.id.viewPager);

        setContentView(theViewPager);

        contactList = AllContacts.get(this).getContactList();

        FragmentManager manager = getSupportFragmentManager();

        theViewPager.setAdapter(new FragmentStatePagerAdapter(manager) {

            @Override
            public Fragment getItem(int position) {

                Contact theContact = contactList.get(position);
                return ContactFragment.newContactFragment(theContact.getIdNumber());

            }

            @Override
            public int getCount() {
                return contactList.size();
            }

        });

        UUID contactId = (UUID) getIntent().getSerializableExtra(ContactFragment.CONTACT_ID);

        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getIdNumber().equals(contactId)) {
                theViewPager.setCurrentItem(i);
                setTitle(contactList.get(i).getName());
                break;
            }
        }

        theViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                setTitle(contactList.get(arg0).getName());

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

    }


}
