package phoneapp;

import java.util.UUID;

import com.jerry.mrnom.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class PhoneApp extends FragmentActivityBuilder {

    @Override
    protected Fragment createFragment() {

        UUID contactIdNumber = (UUID) getIntent().getSerializableExtra(ContactFragment.CONTACT_ID);

        return new ContactFragment().newContactFragment(contactIdNumber);
    }

}
