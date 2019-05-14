package phoneapp;

import android.support.v4.app.Fragment;


public class ContactListActivity extends FragmentActivityBuilder {


    @Override
    protected Fragment createFragment() {

        return new FragmentContactList();
    }


}
