package com.jerry.snake;

public class SnakePart {
    public int x, y;
    public boolean onHold = false;

    public SnakePart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setOnHold(boolean onHold) {
        this.onHold = onHold;
    }

    public boolean getOnHold() {
        return onHold;
    }
}
