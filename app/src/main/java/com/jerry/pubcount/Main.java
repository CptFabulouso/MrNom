package com.jerry.pubcount;


import java.util.ArrayList;
import java.util.Date;

import phoneapp.DateDialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.mrnom.R;

public class Main extends FragmentActivity implements addItemDialogFragment.onDialogResult, ItemFragment.OnResultListener {

    Button addNewItem, removeReceiptButton;
    private static final String NEW_ITEM = "newItemDialog";

    FragmentManager manager;
    TextView resultTextView;

    private ArrayList<Item> itemsList;
    static float total;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(R.layout.pubcount_main_activity);
        manager = getSupportFragmentManager();

        final Animation itemRemoved = AnimationUtils.loadAnimation(
                this, R.animator.item_removed);

        resultTextView = (TextView) findViewById(R.id.mainResultTextView);
        itemsList = AllItems.get(this).getItemsList();


        resultTextView.setText("Total: " + total);

        Fragment theFragment = manager
                .findFragmentById(R.id.PubCountFragmentContainer);
        if (theFragment == null) { // We need to create it
            theFragment = new ItemFragment();
            manager.beginTransaction()
                    .add(R.id.PubCountFragmentContainer, theFragment, "itemFragment").commit();

            //theFragment = new ResultsFragment();
            //manager.beginTransaction()
            //		.add(R.id.PubCountFragmentContainer, theFragment, "resultsFragment").commit();
        }

        resultTextView.setText("Total: " + total);

        addNewItem = (Button) findViewById(R.id.addNewItem);
        addNewItem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager manager = getSupportFragmentManager();

                addItemDialogFragment addItemDialog = new addItemDialogFragment();

                addItemDialog.show(manager, NEW_ITEM);

            }
        });
        addNewItem.setOnLongClickListener(new OnLongClickListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onLongClick(final View v) {
                v.animate().setDuration(300).alpha(0.5f).withEndAction(new Runnable() {

                    @Override
                    public void run() {
                        v.setAlpha(1);
                    }
                });
                Toast.makeText(getApplicationContext(), "long click listener", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        addNewItem.setOnTouchListener(new OnTouchListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //v.startAnimation(itemRemoved);
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        //v.setAlpha(1f);
                        break;
                }
                return false;
            }
        });

        removeReceiptButton = (Button) findViewById(R.id.removeReceipt);
        removeReceiptButton.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                ItemFragment fragment = (ItemFragment) manager.findFragmentByTag("itemFragment");
                fragment.createNewReceipt();
                return true;
            }
        });

    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();


    }

    @Override
    public void itemAdded(float price) {
        total += price;
        resultTextView.setText("Total: " + total);
        //Fragment theFragment = manager.findFragmentByTag("itemFragment");
        //((ItemFragment) theFragment).update();
    }

    @Override
    public void itemRemoved(float price) {
        total -= price;
        resultTextView.setText("Total: " + total);

        //Fragment theFragment = manager.findFragmentByTag("itemFragment");
        //((ItemFragment) theFragment).update();
    }


    public void update() {
        total = 0;
        for (int i = 0; i < itemsList.size(); i++) {
            Item item = itemsList.get(i);
            total = total + (item.getCount() * item.getPrice());
        }
        resultTextView.setText("Total: " + total);
    }

    @Override
    public void newItemDialog(String name, float price, boolean isNewItem) {

        ItemFragment fragment = (ItemFragment) manager.findFragmentByTag("itemFragment");
        Item newItem = new Item(name, price);
        if (isNewItem) {
            AllItems.addItem(newItem);
        }
        fragment.addNewItem(newItem);
        //AllItems.addItem(newItem);
        total += newItem.getPrice();
        resultTextView.setText("Total: " + total);

    }

    public void newReceipt(float price) {
        total = price;
        resultTextView.setText("Total: " + total);
    }


}
    /*
    Button addNewItem;
	FragmentManager manager;
	Button remove;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		addNewItem = (Button) findViewById(R.id.addNewItem);
		remove = (Button) findViewById(R.id.button1);
		
		addNewItem.setOnClickListener(addNewItemRow);
		remove.setOnClickListener(removeItem);
		manager = getSupportFragmentManager();
		
		Fragment itemFragment = manager.findFragmentById(R.id.container);
		
		if(itemFragment == null){	
			if(savedInstanceState != null ){
				return;
			}
			itemFragment = new ItemFragment();
			manager.beginTransaction().add(R.id.container, itemFragment,"fId").commit();
		}
		
	}
	
	public OnClickListener addNewItemRow = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			Fragment itemFragment = manager.findFragmentById(R.id.container);
			if(itemFragment == null){
				itemFragment = new ItemFragment();
				manager.beginTransaction().add(R.id.container, itemFragment,"fId").commit();
			}
		}
	};
	
	public OnClickListener removeItem = new OnClickListener() {
		@Override
public void onClick(View v) {
			
			ItemFragment itemFrag = (ItemFragment) manager.findFragmentByTag("fId");
			if(itemFrag != null){
			manager.beginTransaction().remove(itemFrag).commit();
			}
		}
	};
	*/


