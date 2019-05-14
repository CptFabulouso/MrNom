package com.jerry.mrnom;

import com.jerry.mrnom.BitmapTest.RenderView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class FontTest extends Activity {

    class RenderView extends View {
        Paint paint;
        Typeface font;
        Rect bounds = new Rect();

        public RenderView(Context context) {
            super(context);
            paint = new Paint();
            // Load font from assets
            font = Typeface.createFromAsset(context.getAssets(),
                    "AllerDisplay.ttf");
        }

        @Override
        protected void onDraw(Canvas canvas) {
            paint.setColor(Color.YELLOW);
            paint.setTypeface(font);
            paint.setTextSize(28);
            // Paint.Align.LEFT/RIGHT/CENTER
            // when we draw text to canvas, the x,y coordinates will depend on
            // this setting
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("This is a test", canvas.getWidth() / 2, 100,
                    paint);

            String text = "Another test, booooring";
            paint.setColor(Color.BLUE);
            paint.setTextSize(18);
            paint.setTextAlign(Paint.Align.LEFT);
            // bounds is a rectangle initialized before
            paint.getTextBounds(text, 0, text.length(), bounds);
            /*
             * we want the text to be rightaligned with the right edge of the
			 * screen. We could do this by using Paint.Align.RIGHT and an
			 * x-coordinate of Canvas.getWidth() � 1. Instead, we do it the hard
			 * way by using the bounds of the string to practice very basic text
			 * layout a little
			 */
            canvas.drawText(text, canvas.getWidth() - bounds.width(), 140,
                    paint);
			/*
			 * The call to the View.invalidate() method at the end of onDraw()
			 * will tell the Android system to redraw the RenderView as soon as
			 * it finds time to do that again
			 */

            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(text, canvas.getWidth(), 200, paint);
            invalidate();

            super.onDraw(canvas);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new RenderView(this));
    }

}
