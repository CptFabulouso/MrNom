package com.jerry.mrnom;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndroidBasicStarter extends ListActivity {

    String[] tests = {"LifeCycleTest", "SingleTouchTest", "MultiTouchTest",
            "KeyTest", "AccelerometrTest", "CompassTest", "AssetsTest", "ExternalStorageTest",
            "SoundPoolTest", "MediaPlayerTest", "FullScreenTest",
            "RenderViewTest", "ShapeTest", "BitmapTest", "FontTest",
            "SurfaceViewTest", "FilePlacement", "SnakeGame", "NewGame", "PubCount", "PhoneApp", "JumpGame"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //lets us specify the list items we want to display (our activity, layout, array to display)
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tests));

    }

    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        String testName = tests[position];
        try {
            Class clazz;
            if (testName.equals("PubCount")) {
                clazz = Class.forName("com.jerry.pubcount.Main");
            } else if (testName.equals("PhoneApp")) {
                clazz = Class.forName("phoneapp.ContactListActivity");
            } else {
                clazz = Class.forName("com.jerry.mrnom." + testName);
            }
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
