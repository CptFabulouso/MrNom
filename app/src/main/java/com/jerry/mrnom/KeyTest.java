package com.jerry.mrnom;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class KeyTest extends Activity implements OnKeyListener {

    StringBuilder builder = new StringBuilder();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        textView.setText("Press keys (if you have)");
        textView.setOnKeyListener(this);
        //we make sure the TextView has the focus so it can receive	key events
        textView.setFocusableInTouchMode(true);
        textView.requestFocus();
        setContentView(textView);

		/*only will trigger it if no physical keyboard is open
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		//to close
		//mgr.hideSoftInputFromWindow(view.getWindowToken(),0);
		*/

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        builder.setLength(0);
        switch (event.getAction()) {
            case KeyEvent.ACTION_DOWN:
                builder.append("down, ");
                break;
            case KeyEvent.ACTION_UP:
                builder.append("up, ");
                break;
        }
        builder.append(event.getKeyCode());
        builder.append(", ");
        builder.append((char) event.getUnicodeChar());
        String text = (builder.toString());
        Log.d("KeyTest", text);
        textView.setText(text);

        //if the back key is pressed, we return false from the
        //onKey() method, making the TextView process the event.
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return false;
        } else {
            return true;
        }
    }

}
