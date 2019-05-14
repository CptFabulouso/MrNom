package com.jerry.mrnom;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MultiTouchTest extends Activity implements OnTouchListener {

    StringBuilder builder = new StringBuilder();
    TextView textView;
    float[] x = new float[10];
    float[] y = new float[10];
    boolean[] touched = new boolean[10];
    int[] id = new int[10];

    private void updateTextView() {

        builder.setLength(0);
        for (int i = 0; i < 10; i++) {
            builder.append("touched = ");
            builder.append(touched[i]);
            builder.append(", id = ");
            builder.append(id[i]);
            builder.append(", x = ");
            builder.append(x[i]);
            builder.append(", y = ");
            builder.append(y[i]);
            builder.append("\n");
        }
        textView.setText(builder.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        textView.setText("Touch and drag screen - multi touch supported");
        textView.setOnTouchListener(this);
        setContentView(textView);
        for (int i = 0; i < 10; i++) {
            id[i] = -1;
        }
        updateTextView();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //We start off by getting the event type by masking the integer returned by
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        //we extract the pointer index and fetch the corresponding pointer identifier from the MotionEvent
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < 10; i++) {
            if (i >= pointerCount) {
                touched[i] = false;
                id[i] = -1;
                continue;
            }
            // if it's an up/down/cancel/out event, mask the id to see if we should
            //process it for this touch point
            if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                continue;
            }
            int pointerId = event.getPointerId(i);
            switch (action) {
                //A touch-down event happened
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touched[i] = true;
                    id[i] = pointerId;
                    x[i] = (int) event.getX(i);
                    y[i] = (int) event.getY(i);
                    break;

                //A touch-up event happened
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touched[i] = false;
                    id[i] = -1;
                    x[i] = (int) event.getX(i);
                    y[i] = (int) event.getY(i);
                    break;

                //One or more fingers were dragged across the screen
                case MotionEvent.ACTION_MOVE:
                    touched[i] = true;
                    id[i] = pointerId;
                    x[i] = (int) event.getX(i);
                    y[i] = (int) event.getY(i);
                    break;

            }
        }

        updateTextView();

        //indicating that we processed the touch event
        return true;
    }

}
