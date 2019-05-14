package com.jerry.mrnom;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class SoundPoolTest extends Activity implements OnTouchListener {

    TextView textView;
    SoundPool soundPool;
    int maybeId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        textView.setBackgroundColor(Color.GRAY);
        textView.setOnTouchListener(this);
        textView.setText("actual playing rate is ");
        setContentView(textView);

        // to control audio volume in our game
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // We can simply instantiate new SoundPool instances
        // (maximum number of sound effects we can play simultaneously, audio
        // stream where the SoundPool will output the audio, 0)
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        try {
            // get files in assets folder
            AssetManager assetManager = getAssets();
            // soundpool takes an AssetFileDescriptor as parametr
            AssetFileDescriptor descriptor = assetManager.openFd("maybe.mp3");
            /*
             * method returns an integer, which serves as a handle to the loaded
			 * sound effect. When we want to play the sound effect, we specify
			 * this handle so that the SoundPool knows what effect to play
			 */
            maybeId = soundPool.load(descriptor, 1);

        } catch (IOException e) {
            textView.setText("Couldn't load sound from asset " + e.getMessage());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (maybeId != -1) {
                float rate = (float) (Math.random() * (Math.random() * 5));
                soundPool.play(maybeId, 1, 1, 0, 0, rate);
                textView.setText("actual playing rate is " + rate);
                Log.d("SoundPoolTest", "rate: " + String.valueOf(rate));
            }
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unload the sound
        soundPool.stop(maybeId);
        soundPool.unload(maybeId);
    }
}
