package com.jerry.pubcount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jerry.mrnom.R;

public abstract class FragmentActivityBuilder extends FragmentActivity {

    protected abstract Fragment createFragment();

    protected abstract int passLayout();


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(passLayout());
        FragmentManager manager = getSupportFragmentManager();
        Fragment theFragment = manager
                .findFragmentById(R.id.PubCountFragmentContainer);

        if (theFragment == null) { // We need to create it

            theFragment = createFragment();

            manager.beginTransaction()
                    .add(R.id.PubCountFragmentContainer, theFragment).commit();
        }

    }

}
