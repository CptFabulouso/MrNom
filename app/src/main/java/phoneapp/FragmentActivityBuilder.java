package phoneapp;

import com.jerry.mrnom.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class FragmentActivityBuilder extends FragmentActivity {

    protected abstract Fragment createFragment();


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(R.layout.activity_phone_app);

        FragmentManager manager = getSupportFragmentManager();
        Fragment theFragment = manager
                .findFragmentById(R.id.PhoneAppFragmentContainer);

        if (theFragment == null) { // We need to create it

            theFragment = createFragment();

            manager.beginTransaction()
                    .add(R.id.PhoneAppFragmentContainer, theFragment).commit();
        }

    }

}
