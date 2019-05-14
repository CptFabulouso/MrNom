package com.jerry.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

public class Snake {
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public boolean isEnemy;
    public float speed;
    private float timeSinceLastMove;
    private int lastMove;
    private double lastDistance = 0;
    private double newDistance;

    Random random = new Random();

    public List<SnakePart> parts = new ArrayList<SnakePart>();
    public int direction;

    public Snake(boolean isEnemy, int x, int y, int xadd, int yadd, float speed) {
        direction = UP;
        this.speed = speed;
        this.isEnemy = isEnemy;
        for (int i = 0; i < 3; i++) {
            parts.add(new SnakePart(x + (i * xadd), y + (i * yadd)));
        }

    }

    public void turnLeft() {
        direction += 1;
        if (direction > RIGHT)
            direction = UP;
    }

    public void turnRight() {
        direction -= 1;
        if (direction < UP)
            direction = RIGHT;
    }

    public void eat() {
        SnakePart end = parts.get(parts.size() - 1);
        parts.add(new SnakePart(end.x, end.y));
    }

    public void advance(int stainX, int stainY) {
        SnakePart head = parts.get(0);

        int len = parts.size() - 1;
        for (int i = len; i > 0; i--) {
            SnakePart before = parts.get(i - 1);
            SnakePart part = parts.get(i);
            part.x = before.x;
            part.y = before.y;
            if (part.getOnHold() && part.x >= 0 && part.x < World.WORLD_WIDTH
                    && part.y >= 0 && part.y < World.WORLD_HEIGHT) {
                part.setOnHold(false);
                Log.w("LOG", "Showing");
            }
        }

        if (direction == UP)
            head.y -= 1;
        if (direction == LEFT)
            head.x -= 1;
        if (direction == DOWN)
            head.y += 1;
        if (direction == RIGHT)
            head.x += 1;
        if (head.x < 0)
            head.x = World.WORLD_WIDTH - 1;
        if (head.x > World.WORLD_WIDTH - 1)
            head.x = 0;
        if (head.y < 0)
            head.y = World.WORLD_HEIGHT - 1;
        if (head.y > World.WORLD_HEIGHT - 1)
            head.y = 0;

        if (isEnemy) {
            int rndm = random.nextInt(10);
            if (rndm == 1)
                turnLeft();
            if (rndm == 5)
                turnRight();

			/*
            newDistance = Math.sqrt(Math.pow(head.x - stainX, 2)
					+ Math.pow(head.y - stainY, 2));
			if (lastDistance < newDistance) {
				Log.w("SNAKE", "lastDistance < newDistance = " + lastDistance
						+ " < " + newDistance);
				turnLeft();
			}
			lastDistance = newDistance;
			if(lastDistance ==1 ){
				if(head.x - stainX <0){
					direction = RIGHT;
				} else if(head.x - stainX > 0 ){
					direction = LEFT;
				}
				if(head.y - stainY <0){
					direction = DOWN;
				} else if(head.y - stainY > 0 ){
					direction = UP;
				}
			}
			Log.w("SNAKE", "lastDistance =" + lastDistance
					+ " , newDistance = " + newDistance);
					*/

        }

    }

    public boolean hasEaten(int x, int y) {
        SnakePart head = parts.get(0);
        if (head.x == x && head.y == y) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBitten(List<SnakePart> enemyPart) {
        int len = parts.size();
        SnakePart head = parts.get(0);
        for (int i = 1; i < len; i++) {
            SnakePart part = parts.get(i);
            if (part.x == head.x && part.y == head.y)
                return true;
        }

        for (int i = 0; i < enemyPart.size(); i++) {
            if (enemyPart.get(i).x == head.x && enemyPart.get(i).y == head.y)
                return true;
        }
        return false;
    }

    public void placeSnake(int x, int y, int xadd, int yadd) {
        for (int i = 0; i < parts.size(); i++) {
            Log.w("LOG", "Hiding");
            parts.get(i).x = x + (i * xadd);
            parts.get(i).y = y + (i * yadd);
            if (i > 0) {
                parts.get(i).setOnHold(true);
            }
        }
    }

    public List<SnakePart> getParts() {
        return parts;
    }

    public float getTimeSinceLastMove() {
        return timeSinceLastMove;
    }

    public void updateTimeSinceLastMove(float timeSinceLastMove) {
        this.timeSinceLastMove += timeSinceLastMove;
    }

    public void resetTimeSinceLastMove() {
        this.timeSinceLastMove -= speed;
    }

}
