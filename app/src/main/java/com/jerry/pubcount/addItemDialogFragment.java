package com.jerry.pubcount;

import java.util.ArrayList;

import com.jerry.pubcount.ItemFragment.OnResultListener;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.jerry.mrnom.R;

public class addItemDialogFragment extends DialogFragment {


    EditText nameOfItemEditText;
    EditText priceOfItemEditText;
    private String nameOfItem;
    private float priceOfItem;
    onDialogResult mCallBack;
    CheckBox cb;
    Spinner allItemsSpinner;
    private ArrayList<Item> itemsList;
    private ArrayList<String> itemsStringList;
    boolean itemIsNew;

    public interface onDialogResult {
        public void newItemDialog(String name, float price, boolean newItem);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View theView = getActivity().getLayoutInflater().inflate(R.layout.pubcount_dialog_new_item, null);
        nameOfItemEditText = (EditText) theView.findViewById(R.id.pubcountItemNameEditText);
        priceOfItemEditText = (EditText) theView.findViewById(R.id.pubcountItemPriceEditText);
        cb = (CheckBox) theView.findViewById(R.id.createNewItemcheckBox);
        allItemsSpinner = (Spinner) theView.findViewById(R.id.allItemsSpinner);
        itemsList = AllItems.get(getActivity()).getItemsList();
        itemsStringList = new ArrayList<String>();

        for (int i = 0; i < itemsList.size(); i++) {
            itemsStringList.add(itemsList.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, itemsStringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allItemsSpinner.setAdapter(adapter);

        allItemsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (!cb.isChecked()) {
                    nameOfItemEditText.setHint(itemsList.get(position).getName());
                    priceOfItemEditText.setHint(String.valueOf(itemsList.get(position).getPrice()));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.e("JERRY", "should be focusable");
                    nameOfItemEditText.setHint("Name of item");
                    priceOfItemEditText.setHint("Price of item");
                    nameOfItemEditText.setFocusableInTouchMode(true);
                    nameOfItemEditText.setFocusable(true);
                    priceOfItemEditText.setFocusableInTouchMode(true);
                    priceOfItemEditText.setFocusable(true);
                } else {
                    Log.e("JERRY", "should NOT be focusable");
                    nameOfItemEditText.setFocusable(false);
                    priceOfItemEditText.setFocusable(false);
                }

            }
        });

        TextWatcher editTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before,
                                      int count) {
                if (nameOfItemEditText.hasFocus() == true) {
                    nameOfItem = arg0.toString();

                } else if (priceOfItemEditText.hasFocus() == true) {
                    if (arg0.length() != 0) {
                        priceOfItem = Float.parseFloat(arg0.toString());
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        };

        nameOfItemEditText.addTextChangedListener(editTextWatcher);
        priceOfItemEditText.addTextChangedListener(editTextWatcher);

        Builder aDialog = new AlertDialog.Builder(getActivity());
        aDialog.setView(theView);
        aDialog.setTitle(R.string.pubcount_dialog_add_item);
        aDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (!cb.isChecked()) {
                    int position = allItemsSpinner.getSelectedItemPosition();
                    nameOfItem = itemsList.get(position).getName();
                    priceOfItem = itemsList.get(position).getPrice();
                    itemIsNew = false;
                } else {
                    itemIsNew = true;
                }
                //sendResult(Activity.RESULT_OK, nameOfItem, priceOfItem);
                mCallBack.newItemDialog(nameOfItem, priceOfItem, itemIsNew);
            }
        });

        aDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return aDialog.create();

    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);

        try {
            mCallBack = (onDialogResult) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnResultListener");
        }
    }


}
