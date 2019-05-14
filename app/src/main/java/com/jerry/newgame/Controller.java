package com.jerry.newgame;

import android.util.Log;

import framework.Game;

public class Controller {

    int pressedPointx;
    int pressedPointy;
    int pointer;
    boolean active;
    int innerCirclex;
    int innerCircley;
    int innerCircleRadius;
    int radius;
    int localX;
    int localY;
    float innerRadius;
    int deltaTime = 0;
    int maxSpeed = 2;

    public Controller() {
        active = false;
    }

    public Controller(int pressedPointx, int pressedPointy, int pointer) {
        this.innerCirclex = pressedPointx;
        this.innerCircley = pressedPointy;
        this.pressedPointx = pressedPointx;
        this.pressedPointy = pressedPointy;
        this.pointer = pointer;
    }

    public void update(int x, int y) {
        localX = x - pressedPointx;
        localY = y - pressedPointy;
        innerRadius = (float) Math.sqrt(Math.pow(localX, 2) + Math.pow(localY, 2));
        if ((innerRadius + innerCircleRadius) > radius) {
            innerCirclex = (int) (pressedPointx + (localX / innerRadius) * (radius - innerCircleRadius));
            innerCircley = (int) (pressedPointy + (localY / innerRadius) * (radius - innerCircleRadius));
        } else {
            innerCirclex = x;
            innerCircley = y;
        }
    }

    public void create(int pressedPointx, int pressedPointy, int pointer, int radius, int innerCircleRadius) {
        this.innerCircleRadius = innerCircleRadius;
        this.radius = radius;
        this.innerCirclex = pressedPointx;
        this.innerCircley = pressedPointy;
        this.pressedPointx = pressedPointx;
        this.pressedPointy = pressedPointy;
        this.pointer = pointer;
        active = true;
    }
}
