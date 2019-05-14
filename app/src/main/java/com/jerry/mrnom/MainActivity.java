package com.jerry.mrnom;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    String[] tests = {"LifeCycleTest", "SingleTouchTest", "MultiTouchTest",
            "KeySet", "AccelerometrTest", "AssetsTest", "ExternalStorageTest",
            "SoundPoolTest", "MediaPlayerTest", "FullScreenTest",
            "RenderViewTest", "ShapeTest", "BitmapTest", "FontTest",
            "SurfaceViewTest", "SnakeGame"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // lets us specify the list items we want to display (our activity,
        // layout, array to display)
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tests));

    }

    /*
     * The ListActivity class has a protected method called onListItemClick()
     * that will be // called when an item is clicked. All we need to do is to
     * override that method in our MainActivity
     */
    /*
     * The arguments to this method are the ListView that the ListActivity uses
	 * to display the items, the View that got touched and that's contained in
	 * that ListView, the position of the touched item in the list, and an ID,
	 * which doesn�t interest us all that much.
	 */
    protected void onListItemClick(ListView list, View view, int position,
                                   long id) {
        super.onListItemClick(list, view, position, id);
        String testName = tests[position];
        try {
            // takes a string containing the fully-qualified name of a class for
            // which we want to get a Class instance.
            Class clazz = Class.forName("com.jerry.mrnom." + testName);
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
