package phoneapp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.jerry.mrnom.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DateDialogFragment extends DialogFragment {

    public static final String CONTACT_BIRTHDAY = "phoneapp.contact_birthday";

    private Date contactBirthday;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        contactBirthday = (Date) getArguments().getSerializable(CONTACT_BIRTHDAY);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(contactBirthday);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View theView = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker) theView.findViewById(R.id.birthday_picker);

        datePicker.init(year, month, day, new OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int month, int day) {
                contactBirthday = new GregorianCalendar(year, month, day).getTime();

                getArguments().putSerializable(CONTACT_BIRTHDAY, contactBirthday);

            }

        });

        Builder aDialog = new AlertDialog.Builder(getActivity());
        aDialog.setView(theView);
        aDialog.setTitle(R.string.contact_birthday);
        aDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                sendResult(Activity.RESULT_OK);

            }
        });

        return aDialog.create();
        /*
        return new AlertDialog.Builder(getActivity()).setView(theView).setTitle(R.string.contact_birthday).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

					sendResult(Activity.RESULT_OK);
				
			}
		}).create();
		*/


    }


    public static DateDialogFragment newInstance(Date date) {
        Bundle dataPassed = new Bundle();

        dataPassed.putSerializable(CONTACT_BIRTHDAY, date);
        DateDialogFragment dateFragment = new DateDialogFragment();

        dateFragment.setArguments(dataPassed);

        return dateFragment;
    }


    private void sendResult(int resultCode) {

        if (getTargetFragment() == null) {
            return;
        } else {
            Intent theIntent = new Intent();
            theIntent.putExtra(CONTACT_BIRTHDAY, contactBirthday);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, theIntent);

        }

    }


}
