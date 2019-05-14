package com.jerry.snake;

import java.util.Random;

public class World {
    static final int WORLD_WIDTH = 12;
    static final int WORLD_HEIGHT = 19;
    static final int SCORE_INCREMENT = 20;
    static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;

    public Snake snake;
    public Snake enemySnake;
    public Stain stain;
    public boolean gameOver = false;
    ;
    public int score = 0;

    boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    float tick = TICK_INITIAL;

    public World() {
        snake = new Snake(false, 5, 6, 0, 1, 0.5f);
        enemySnake = new Snake(true, 1, 6, 0, 1, 0.5f);
        placeStain();
    }

    private void placeStain() {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                fields[x][y] = false;
            }
        }

        int len = 0;
        if (snake.parts.size() > enemySnake.parts.size()) {
            len = snake.parts.size();
        } else {
            len = enemySnake.parts.size();
        }

        for (int i = 0; i < len; i++) {
            if (snake.parts.size() > i) {
                SnakePart part = snake.parts.get(i);
                fields[part.x][part.y] = true;
            }
            if (enemySnake.parts.size() > i) {
                SnakePart part = enemySnake.parts.get(i);
                if (!part.getOnHold()) {
                    fields[part.x][part.y] = true;
                }
            }
        }

        int stainX = random.nextInt(WORLD_WIDTH);
        int stainY = random.nextInt(WORLD_HEIGHT);
        while (true) {
            if (fields[stainX][stainY] == false)
                break;
            stainX += 1;
            if (stainX >= WORLD_WIDTH) {
                stainX = 0;
                stainY += 1;
                if (stainY >= WORLD_HEIGHT) {
                    stainY = 0;
                }
            }
        }
        stain = new Stain(stainX, stainY, random.nextInt(3));
    }

    public void update(float deltaTime) {
        if (gameOver) {
            return;
        }

        tickTime += deltaTime;

		/*
         * The while loop will use up as many ticks that have been accumulated
		 * (for example, when tickTime is 1.2 and one tick should take 0.5
		 * seconds, we can update the world twice, leaving 0.2 seconds in the
		 * accumulator). This is called a fixed-time-step simulation.
		 */
        while (tickTime > tick) {

            tickTime -= tick;
            snake.advance(0, 0);
            enemySnake.advance(stain.x, stain.y);

            if (snake.checkBitten(enemySnake.parts)) {
                gameOver = true;
                return;
            }

			/*
			if (enemySnake.checkBitten(snake.parts)) {
				enemySnake.placeSnake(0, 0, -1, 0);
			}
			*/

            SnakePart head = snake.parts.get(0);

            if (snake.hasEaten(stain.x, stain.y)) {
                score += SCORE_INCREMENT;
                snake.eat();
                if (snake.parts.size() == WORLD_HEIGHT * WORLD_WIDTH) {
                    gameOver = true;
                    return;
                } else {
                    placeStain();
                }
                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
			/*
			if (enemySnake.hasEaten(stain.x, stain.y)) {
				enemySnake.eat();
				placeStain();

			}
			*/

            if (head.x == stain.x && head.y == stain.y) {
                score += SCORE_INCREMENT;
                snake.eat();
                if (snake.parts.size() == WORLD_HEIGHT * WORLD_WIDTH) {
                    gameOver = true;
                    return;
                } else {
                    placeStain();
                }

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }

        }

    }

}
