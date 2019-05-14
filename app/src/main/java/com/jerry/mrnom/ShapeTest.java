package com.jerry.mrnom;

import com.jerry.mrnom.RenderViewTest.RenderView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

public class ShapeTest extends Activity implements OnTouchListener {

    int[] circleX = new int[10];
    int[] circleY = new int[10];
    int count = 0;

    class RenderView extends View {
        Paint paint;

        public RenderView(Context context) {
            super(context);
            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // fill the screen with white
            canvas.drawRGB(255, 255, 255);
            paint.setColor(Color.RED);
            canvas.drawLine(0, 0, canvas.getWidth() - 1,
                    canvas.getHeight() - 1, paint);

            paint.setStyle(Style.STROKE);
            paint.setColor(0xff00ff00);
            paint.setStrokeWidth(5);
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2,
                    40, paint);
            // because it gets rendered constantly, so the line isn't bold
            paint.setStrokeWidth(1);

            paint.setStyle(Style.FILL);
            paint.setColor(0x770000ff);
            canvas.drawRect(100, 100, 200, 200, paint);

            paint.setColor(Color.BLUE);
            for (int i = 0; i < 10; i++) {
                if (circleX[i] != 0) {
                    canvas.drawCircle(circleX[i], circleY[i], 50, paint);
                }
            }
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        circleX[count] = (int) event.getX();
        circleY[count] = (int) event.getY();
        count += 1;
        if (count > 9) {
            count = 0;
        }

        return false;
    }
}
