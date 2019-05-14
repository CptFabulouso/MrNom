package com.jerry.pubcount;

import com.jerry.mrnom.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

public class ResultsFragment extends Fragment {

    TextView results;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        results = (TextView) getActivity().findViewById(R.id.totalTextView);
        if (savedInstanceState == null) {
            results.setText("0");
        } else {

        }

    }

    public void update(float price) {
        float currentTotal = Float.parseFloat(results.getText().toString());
        float newTotal = currentTotal + price;
        results.setText(String.valueOf(newTotal));
    }


}
