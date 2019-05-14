package com.jerry.mrnom;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class RenderViewTest extends Activity {
    class RenderView extends View {

        Random rand = new Random();

        // We can use this RenderView as we'd use a TextView.
        public RenderView(Context context) {
            super(context);
        }

        /*
         * We get an instance of a class called Canvas passed to the onDraw()
         * method. This will be our workhorse in the following sections. It lets
         * us draw shapes and bitmaps to either another bitmap or a View (or a
         * surface, which we'll talk about in a bit).
         */
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawRGB(rand.nextInt(256), rand.nextInt(256),
                    rand.nextInt(256));
            /*
             * will tell the Android system to redraw the RenderView as soon as
			 * it finds time to do that again. All of this still happens on the
			 * UI thread, which is a bit of a lazy horse
			 */
            invalidate();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new RenderView(this));
    }
}
