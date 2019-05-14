package com.jerry.mrnom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

public class ExternalStorageTest extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);

        String state = Environment.getExternalStorageState();
        //check if SC card is mounted
        if (!state.equals(Environment.MEDIA_MOUNTED)) { //if not mounted
            textView.setText("No external storage mounted");
        } else { //if mounted
            //get the external storage directory
            File externalDir = Environment.getExternalStorageDirectory();
            //construct a new file instance that points to the file we are going to create in the next statemen
            File textFile = new File(externalDir.getAbsolutePath()
                    + File.separator + "text.txt");

            try {
                writeTextFile(textFile, "This is a test.");
                String text = readTextFile(textFile);
                textView.setText(text);
                if (!textFile.delete()) {
                    textView.setText("Couldn't remowe temporary file");
                }
            } catch (IOException e) {
                textView.setText("Something wen horribly wrong, "
                        + e.getMessage());
            }

        }
    }

    public void writeTextFile(File file, String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(text);
        writer.close();
    }

    public String readTextFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder text = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            text.append(line);
            text.append("\n");
        }
        reader.close();
        return text.toString();
    }
}
