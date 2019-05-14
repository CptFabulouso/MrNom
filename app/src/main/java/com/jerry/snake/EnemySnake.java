package com.jerry.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

public class EnemySnake {
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    Random random = new Random();
    public boolean isHided = false;

    public List<SnakePart> parts = new ArrayList<SnakePart>();
    public int direction;

    public EnemySnake(int x, int y) {
        direction = UP;
        parts.add(new SnakePart(x, y));
        parts.add(new SnakePart(x, y + 1));
        parts.add(new SnakePart(x, y + 2));
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

    public void advance() {
        SnakePart head = parts.get(0);
        int len = parts.size() - 1;
        for (int i = len; i > 0; i--) {
            SnakePart before = parts.get(i - 1);
            SnakePart part = parts.get(i);
            part.x = before.x;
            part.y = before.y;
        }
        /*
        if(parts.get(0).onHold){
			for (int i = 1 ; i < len; i++) {
				if (!parts.get(i).onHold){
					hideSnakePart(i);
					if(i==len-1){
						isHided = true;
					}
					break;
				}
			}
			return;
		}
		*/
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
        int rndm = random.nextInt(10);
        if (1 == rndm) {
            turnLeft();
        }
        if (5 == rndm) {
            turnRight();
        }
		
		/*
		if (head.x >= 0 && head.x <= World.WORLD_WIDTH - 1 && head.y >= 0
				&& head.y <= World.WORLD_HEIGHT - 1) {
			if (1 == random.nextInt(10)) {
				turnLeft();
			}
			if (5 == random.nextInt(10)) {
				turnRight();
			}
		} else {// snake is hiding
			hideSnakePart(0);
		}
		*/
    }

    public void hideSnakePart(int i) {
        parts.get(i).x = World.WORLD_WIDTH + 1;
        parts.get(i).y = 0;
        parts.get(i).setOnHold(true);
    }

    public void showSnakePart() {

    }

    public void placeSnake(int x, int y) {
        int len = parts.size();
        for (int i = 0; i > len; i++) {
            parts.get(i).x = x + i;
            parts.get(i).y = y;
            if (parts.get(i).onHold) {
                parts.get(i).setOnHold(false);
                if (i == len - 1) {
                    isHided = false;
                }
                break;
            }
        }
    }

    public boolean checkBitten() {
        int len = parts.size();
        SnakePart head = parts.get(0);
        for (int i = 1; i < len; i++) {
            SnakePart part = parts.get(i);
            if ((part.x == head.x && part.y == head.y))
                return true;
        }
        return false;
    }

}
