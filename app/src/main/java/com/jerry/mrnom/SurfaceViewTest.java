package com.jerry.mrnom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SurfaceViewTest extends Activity {
    FastRenderView renderView;
    public static int color;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);

        float scaleX = (float) getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) getWindowManager().getDefaultDisplay().getHeight();

        textView.setText("x = " + scaleX + " y = " + scaleY);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        color = 0;
        renderView = new FastRenderView(this);
        setContentView(renderView);
        setContentView(textView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        renderView.resume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        renderView.pause();
    }

    class FastRenderView extends SurfaceView implements Runnable {
        Thread renderThread = null;
        SurfaceHolder holder;
        volatile boolean running = false;

        public FastRenderView(Context context) {
            super(context);
            // to acquire an instance of the SurfaceHolder class
            holder = getHolder();
        }

        public void resume() {
            running = true;
            renderThread = new Thread(this);
            renderThread.start();
        }

        @Override
        public void run() {
            while (running) {
                if (!holder.getSurface().isValid()) {
                    continue;
                }

                // locks the Surface for rendering and returns a nice Canvas
                // instance we
                // can use
                Canvas canvas = holder.lockCanvas();
                canvas.drawRGB(255, color, 0);
                // unlocks the Surface again and makes sure that what
                // we�ve drawn via the Canvas gets displayed on the screen
                holder.unlockCanvasAndPost(canvas);
                /*
                 * The Canvas we have to pass to the
				 * SurfaceHolder.unlockAndPost() method must be the one we
				 * received from the SurfaceHolder.lockCanvas() method The
				 * Surface is not immediately created when the SurfaceView is
				 * instantiated. Instead it is created asynchronously. The
				 * surface will be destroyed each time the activity is paused
				 * and recreated when the activity is resumed. If this method
				 * returns true, we can safely lock the surface and draw to it
				 * via the Canvas we receive. We have to make absolutely sure
				 * that we unlock the Surface again after a call to
				 * SurfaceHolder.lockCanvas(), or else our activity might lock
				 * up the phone!
				 */
            }
        }

        public void pause() {
            running = false;
            while (true) {
                try {
                    renderThread.join();
                    return;
                } catch (InterruptedException e) {
                    // retry
                }
            }
        }

    }

    // we can check whether the Surface has been created or not via:
    // boolean isCreated = surfaceHolder.getSurface().isValid();
}
