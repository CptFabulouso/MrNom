package com.jerry.mrnom;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class BitmapTest extends Activity {
    class RenderView extends View {
        Bitmap bob565;
        Bitmap bob4444;
        Rect dst = new Rect();

        public RenderView(Context context) {
            super(context);

            try {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open("bob.png");
                bob565 = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                Log.d("BitmapText",
                        "bobRGB888.png format: " + bob565.getConfig());

                inputStream = assetManager.open("bob.png");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                /*
                 * To save some memory, we tell the BitmapFactory to convert
				 * this image of Bob to an ARGB4444 bitmap.The factory may not
				 * obey this request (for unknown reasons)
				 */
                bob4444 = BitmapFactory
                        .decodeStream(inputStream, null, options);
                inputStream.close();
                Log.d("BitmapText",
                        "bobRGB888.png format: " + bob4444.getConfig());

            } catch (IOException e) {
                // coce
            } finally {
                // bob565.recycle();
                // bob4444.recycle();
            }

        }

        @Override
        protected void onDraw(Canvas canvas) {
            dst.set(50, 50, 350, 350);
            canvas.drawRGB(0, 0, 0);
            canvas.drawBitmap(bob565, null, dst, null);
            canvas.drawBitmap(bob4444, 100, 100, null);
			/*
			 * The call to the View.invalidate() method at the end of onDraw()
			 * will tell the Android system to redraw the RenderView as soon as
			 * it finds time to do that again
			 */
            invalidate();
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
