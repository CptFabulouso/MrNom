package com.jerry.jumpgame;

import android.util.Log;

public class Player {

    public static final int MOVE_LEFT = -1;
    public static final int MOVE_RIGHT = 1;

    int x, y, width, height;
    int speedx, speedy;
    int direction;
    int numOfIterations;

    boolean isJumping;


    public Player(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        isJumping = false;
        speedx = 0;
        speedy = 0;
        direction = MOVE_RIGHT;
    }

    public void jump() {
        if (isJumping)
            return;

        isJumping = true;
        speedx = direction * 10;
        speedy = -25;
        if (direction == MOVE_LEFT)
            direction = MOVE_RIGHT;
        else
            direction = MOVE_LEFT;
    }

    public void move() {
        if (!isJumping)
            return;
        numOfIterations++;
        x += speedx;
        y += speedy;
        if (y % 2 == 0)
            speedy++;
        if (x < 0) {
            isJumping = false;
            x = 0;
            Log.d("Jumpy", "numOfIterations: " + numOfIterations);
            numOfIterations = 0;
        } else if (x > (World.WORLD_WIDTH - width)) {
            isJumping = false;
            x = World.WORLD_WIDTH - width;
            Log.d("Jumpy", "numOfIterations: " + numOfIterations);
            numOfIterations = 0;
        }

    }

}
