/*This class is exactly the same as compassTest class, 
only Sensor.TYPE_ACCELEROMETER is replaced with Sensor.TYPE_ORIENTATION*/

package com.jerry.mrnom;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class CompassTest extends Activity implements SensorEventListener {

    StringBuilder builder = new StringBuilder();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);

        // To get the SensorManager, we use a method of the Context interface

        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // if accelerometer is not available
        if (manager.getSensorList(Sensor.TYPE_ORIENTATION).size() == 0) {
            textView.setText("No compass installed");
        } else {
            // an accelerometer is installed, we can fetch it from the
            // SensorManager and register the
            // SensorEventListener

            Sensor compass = manager
                    .getSensorList(Sensor.TYPE_ORIENTATION).get(0);
            if (!manager.registerListener(this, compass,
                    SensorManager.SENSOR_DELAY_GAME)) {
                textView.setText("Could'n register sensor listener");
            }
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final int[] as = compass_AXIS_SWAP[screenOrientation];
        float screenX = (float) as[0] * event.values[as[2]];
        float screenY = (float) as[1] * event.values[as[3]];
        float screenZ = event.values[2];

		/*
         * We can also receive the same compass data from a call to
		 * SensorManager.getOrientation(float[] R, float[] values) where R is a
		 * rotation matrix (see SensorManager.getRotationMatrix()) and values
		 * holds the three return values, this time in radians.
		 */
        builder.setLength(0);
        builder.append("x:");
        builder.append(event.values[0]); // should be screenX to take affect
        builder.append("\ny:");
        builder.append(event.values[1]); // should be screenY to take affect
        builder.append("\nz:");
        builder.append(event.values[2]); // should be screenZ to take affect
        textView.setText(builder);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // now to check the andorid orientation
    int screenOrientation;

    public void onResume() {
        super.onResume();
        WindowManager windowMgr = (WindowManager) this
                .getSystemService(this.WINDOW_SERVICE);
        screenOrientation = windowMgr.getDefaultDisplay().getOrientation();
    }

    // matrix for rotating the axis
    static final int compass_AXIS_SWAP[][] = {{1, -1, 0, 1}, // rotation
            // 0
            {-1, -1, 1, 0}, // rotation 90
            {-1, 1, 0, 1}, // rotation 180
            {1, 1, 1, 0}}; // ROTATION_270
}
