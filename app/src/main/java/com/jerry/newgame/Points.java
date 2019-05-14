package com.jerry.newgame;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import framework.Game;
import framework.Pixmap;

public class Points {
    enum State {
        falling, raising
    }

    private float x;
    private float y;
    State state;
    private float timeFalling;
    private float yzero;
    private float velocity;
    private float accel;
    int health = 400;
    Pixmap pixmap = Assets.stain1;
    Point gun1 = new Point(0, 0);
    Point gun2 = new Point(0, 0);
    int color = Color.GREEN;
    boolean isTouched = false;
    int pointerTouched;
    int collisionColor = Color.GRAY;

    public Points(float x, float y) {
        state = State.falling;
        this.x = x;
        this.y = y;
        this.yzero = y;
        timeFalling = 0;
    }

    public void move() {
        x += 10;
        y += 10;
    }

    public void drawPLine(int x1, int y1, int x2, int y2) {

        float LAB = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        float Dx = (x2 - x1) / LAB;
        float Dy = (y2 - y1) / LAB;
        float t = Dx * (x - x1) + Dy * (y - y1);
        float Ex = t * Dx + x1;
        float Ey = t * Dy + y1;
        gun1.x = (int) Ex;
        gun1.y = (int) Ey;
                /*
        float k1 = 0;
				float k2 =0;
		if(x1 != x2){
		k1 = (y1-y2)/(x2-x1);
		}
		if(y1 != y2){
		k2 = (x2-x1)/(y2-y1);
		}
		float y01 = y1-(k1*x1);
		float y02 = y-(k2*x);
		int xh = (int)  ((y01-y02)/(k2-k1));
		int yh = (int) (y01 + k1*xh);
		gun1.x = (int) xh;
		gun1.y = (int) yh;
		*/
    }

    public void checkDamage(int x1, int y1, int x2, int y2) {
        float LAB = (float) Math.sqrt(Math.pow(x2 - x1, 2)
                + Math.pow(y2 - y1, 2));
        float Dx = (x2 - x1) / LAB;
        float Dy = (y2 - y1) / LAB;
        float t = Dx * (x - x1) + Dy * (y - y1);
        float Ex = t * Dx + x1;
        float Ey = t * Dy + y1;
        gun1.x = (int) Ex;
        gun1.y = (int) Ey;
        float R = (float) Math.sqrt(Math.pow(Ex - x, 2) + Math.pow(Ey - y, 2));
        if (R <= 64) {
            color = Color.RED;
            pixmap = Assets.stain2;
            health -= 10;
        } else {
            color = Color.GREEN;
            pixmap = Assets.stain1;
        }
    }

    public boolean checkCollision(float x1, float y1) {
        if (Math.abs(x - x1) < 96 && Math.abs(y - y1) < 96) {
            float radius = (float) Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
            if (radius < 64) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public void move(float delta, float x, float y) {
        if (x > this.x)
            this.x += 1;
        else
            this.x -= 1;
        if (y > this.y)
            this.y += 1;
        else
            this.y -= 1;

		/*
		 * if(state == State.falling){ if(y < 460){ timeFalling +=delta; accel =
		 * (float) (1*timeFalling*timeFalling/2); y = yzero+accel; } else {
		 * state = State.raising; yzero = y; velocity = accel/2; timeFalling=0;
		 * } } if(state == State.raising){ timeFalling -= delta; if(y > 16 &&
		 * timeFalling > 0){ accel = (float) (9.81*timeFalling*timeFalling/2); y
		 * = yzero-velocity+accel; } else { state = State.falling; yzero=y;
		 * timeFalling = 0; velocity = 0; } } Log.w("LOG", "delta =" + delta
		 * +" , timeFalling = " + timeFalling + ", accel = " + accel + ", y = "
		 * + y + ", state = " + state.toString());
		 */
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}
