package com.jerry.pubcount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import android.content.res.Resources;

import phoneapp.ContactFragment;
import phoneapp.DateDialogFragment;

import com.jerry.mrnom.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class ItemFragment extends ListFragment {

    private Receipt rec;
    private ArrayList<Item> itemsList;
    private ArrayList<Item> allItemsList;

    StringBuilder builder;

    Button newItemButton;

    private static final int REQUEST_NEW_ITEM = 0;
    private static final String NEW_ITEM = "new item";
    private static final String ITEMS_ENTERED = "itemsEntered";

    private SharedPreferences itemsEntered;

    OnResultListener mCallBack;

    public interface OnResultListener {

        public void itemAdded(float price);

        public void itemRemoved(float price);

        public void newReceipt(float price);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        itemsEntered = getActivity().getSharedPreferences(ITEMS_ENTERED, getActivity().MODE_PRIVATE);
        String savedItemsList = itemsEntered.getString(ITEMS_ENTERED, null);
        if (savedItemsList == null)
            rec = new Receipt();
        else {
            rec = new Receipt(savedItemsList);
            mCallBack.newReceipt(rec.getTotal());
        }
        itemsList = rec.getReceiptItemsList();
        allItemsList = AllItems.get(getActivity()).getItemsList();
        boolean foundItem = false;

        for (int i = 0; i < itemsList.size(); i++) {
            foundItem = false;
            for (int j = 0; j < allItemsList.size(); j++) {
                if (itemsList.get(i).getName().equals(allItemsList.get(j).getName())) {
                    foundItem = true;
                }
            }
            if (!foundItem) {
                allItemsList.add(itemsList.get(i));
            }
        }


        ItemsAdapter adapter = new ItemsAdapter(itemsList);
        setListAdapter(adapter);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallBack = (OnResultListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnResultListener");
        }

    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        SharedPreferences.Editor preferencesEditor = itemsEntered.edit();
        preferencesEditor.clear();
        String savedItems = rec.save();
        if (savedItems.length() != 0)
            preferencesEditor.putString(ITEMS_ENTERED, savedItems);
        preferencesEditor.apply();

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "Item clicked", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                Toast.makeText(getActivity(), "On long click listener",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        ((ItemsAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private class ItemsAdapter extends ArrayAdapter<Item> {

        final Animation animScale = AnimationUtils.loadAnimation(getActivity(),
                R.animator.add_item_click_animation);
        final Animation newListItem = AnimationUtils.loadAnimation(
                getActivity(), R.animator.new_list_item);
        final Animation itemRemoved = AnimationUtils.loadAnimation(
                getActivity(), R.animator.item_removed);

        public ItemsAdapter(ArrayList<Item> items) {
            super(getActivity(), android.R.layout.simple_list_item_1, items);
        }

        private class MyViewHolder {

            TextView pubcountItemName;
            TextView pubcountItemCount;
            TextView priceTextView;
            Button pubcountItemButton;

            public MyViewHolder(View v) {
                pubcountItemName = (TextView) v
                        .findViewById(R.id.pubcountItemName);
                pubcountItemCount = (TextView) v
                        .findViewById(R.id.pubcountItemCount);
                pubcountItemButton = (Button) v
                        .findViewById(R.id.pubcountItemButton);
                priceTextView = (TextView) v.findViewById(R.id.priceTextView);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyViewHolder holder = null;

            if (convertView == null) {
                // if (position % 2 == 0) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.pubcount_item_list, null);
                //convertView.startAnimation(newListItem);

                // } else {
                // convertView = getActivity().getLayoutInflater().inflate(
                // R.layout.pubcount_item_list_second, null);
                // }
                holder = new MyViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }

			/*
             * convertView.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) {
			 * Toast.makeText(getActivity(), "Item clicked",
			 * Toast.LENGTH_LONG).show(); } });
			 */
            /*
			 * MarginLayoutParams params = (MarginLayoutParams)
			 * holder.pubcountItemButton.getLayoutParams(); if (position % 2 ==
			 * 0) { params.setMargins(R.dimen.activity_word_padding,
			 * R.dimen.activity_word_padding, R.dimen.right_button_margin,
			 * R.dimen.activity_word_padding);
			 * holder.pubcountItemButton.setLayoutParams(params); } else {
			 * params.setMargins(R.dimen.activity_word_padding,
			 * R.dimen.activity_word_padding, R.dimen.activity_word_padding,
			 * R.dimen.activity_word_padding);
			 * holder.pubcountItemButton.setLayoutParams(params); }
			 */
            final Item theItem = getItem(position);
            holder.pubcountItemName.setText(theItem.getName());
            holder.pubcountItemCount
                    .setText(String.valueOf(theItem.getCount()));
            float price = theItem.getCount() * theItem.getPrice();
            price = precision(2, price);
            holder.priceTextView.setText(String.valueOf(price));
            holder.pubcountItemButton.setTag(theItem.getIdNumber());
            holder.pubcountItemButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.startAnimation(animScale);
                    rec.addItem(theItem);
                    mCallBack.itemAdded(theItem.getPrice());
                    notifyDataSetChanged();

                }
            });


            convertView.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(final View v) {

                    //v.startAnimation(itemRemoved);
                    mCallBack.itemRemoved(theItem.getPrice());
                    rec.removeItem(theItem);

                    if (theItem.getCount() > 0) {

                        v.animate().setDuration(200).alpha(0.5f).withEndAction(new Runnable() {

                            @Override
                            public void run() {
                                v.setAlpha(1);
                            }
                        });

                        //v.startAnimation(itemRemoved);
                        Toast.makeText(getActivity(), "One item removed",
                                Toast.LENGTH_LONG).show();
                    }

                    notifyDataSetChanged();
                    return true;
                }
            });

            return convertView;

        }

    }

    public static float precision(int decimalPlace, float d) {

        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public void addNewItem(Item item) {
        rec.addNewItem(item);
        update();
    }

    public void update() {
        ((ItemsAdapter) getListAdapter()).notifyDataSetChanged();
    }


    public void createNewReceipt() {
        mCallBack.newReceipt(0f);
        rec = new Receipt();
        ItemsAdapter adapter = new ItemsAdapter(rec.getReceiptItemsList());
        setListAdapter(adapter);
        update();
    }


}
