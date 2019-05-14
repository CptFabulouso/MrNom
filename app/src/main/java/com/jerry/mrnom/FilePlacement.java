package com.jerry.mrnom;

import java.util.Random;
import java.util.ResourceBundle.Control;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class FilePlacement extends Activity {
    TextView textView;
    String[] files;
    View mDecorView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textView = new TextView(this);
        files = fileList(this);

        StringBuilder builder = new StringBuilder();
        builder.append("Environment:");
        builder.append(Environment.getExternalStorageDirectory().toString());
        builder.append("\n");
        builder.append("Context:");
        builder.append(getExternalFilesDir("MrSnake").toString());
        for (int i = 0; i < files.length; i++) {
            builder.append(files[i]);
            builder.append("\n");
        }
        if (files.length == 1) {
            textView.setText("Tam nic neniii");
        } else {
            textView.setText(builder.toString());
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mDecorView = getWindow().getDecorView();
        hideSystemUI();
        setContentView(textView);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void hideSystemUI() {
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    public String[] fileList(Context context) {
        context = context.getApplicationContext();
        return context.fileList();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

}
