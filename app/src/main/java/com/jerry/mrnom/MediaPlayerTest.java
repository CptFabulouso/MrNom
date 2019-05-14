package com.jerry.mrnom;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MediaPlayerTest extends Activity {

    MediaPlayer mediaPlayer;
    TextView volume;
    float volumeFloat;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        seekBar = new SeekBar(this);
        seekBar.setProgress(50);
        volumeFloat = (float) (seekBar.getProgress() / 100);
        volume = new TextView(this);
        volume.setText("Volume " + volumeFloat);
        volume.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        seekBar.setLayoutParams(new LayoutParams(800, 100));
        seekBar.setOnSeekBarChangeListener(volumeListener);
        setContentView(volume);
        addContentView(seekBar, seekBar.getLayoutParams());

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mediaPlayer = new MediaPlayer();

        //to be sure the volume is max 1
        if (volumeFloat <= 1 & volumeFloat > 0) {
            mediaPlayer.setVolume(volumeFloat, volumeFloat);
        } else {
            mediaPlayer.setVolume(1, 1);
        }
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor = assetManager.openFd("hth.mp3");
            /*
             * MediaPlayer.setDataSource() method does not directly take an
			 * AssetFileDescriptor. Instead, it wants a FileDescriptor, which we
			 * get via the AssetFileDescriptor.getFileDescriptor() method.
			 * Additionally, we have to specify the offset and the length of the
			 * audio file. Why the offset? Assets are all stored in a single
			 * file in reality. For the MediaPlayer to get to the start of the
			 * file, we have to provide it with the offset of the file within
			 * the containing asset file.
			 */
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			/*
			 * Before we can start playing back the music file, we have to call
			 * one more method that prepares the MediaPlayer for playback. This
			 * will actually open the file and check whether it can be read and
			 * played back by the MediaPlayer instance. From here on, we are
			 * free to play the audio file, pause it, stop it, set it to be
			 * looped, and change the volume.
			 */
            mediaPlayer.prepare();
            //mediaPlayer.setLooping(true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            volume.setText("Couldn't load music " + e.getMessage());
            mediaPlayer = null;
        }
    }

    private OnSeekBarChangeListener volumeListener = new OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            volumeFloat = (float) (progress / 100);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

    };

    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

    }

    ;

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            if (isFinishing()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }

    }

}
