package com.jerry.snake;

import java.util.List;

import android.graphics.Color;

import framework.Game;
import framework.Graphics;
import framework.Input.TouchEvent;
import framework.Pixmap;
import framework.Screen;

public class GameScreen extends Screen {
    enum GameState {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;
    World world;
    int oldScore = 0;
    String score = "0";

    public GameScreen(Game game) {
        super(game);
        world = new World();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if (touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y < 64) {
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    state = GameState.Paused;
                    return;
                }
            }
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (event.x < 200 && event.y > 1000) {
                    world.snake.turnLeft();
                }
                if (event.x > 600 && event.y > 1000) {
                    world.snake.turnRight();
                }
            }
        }

        world.update(deltaTime);
        if (world.gameOver) {
            if (Settings.soundEnabled)
                Assets.bitten.play(1);
            state = GameState.GameOver;
        }

        if (oldScore != world.score) {
            oldScore = world.score;
            score = "" + oldScore;
            if (Settings.soundEnabled)
                Assets.eat.play(1);
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 80 && event.x <= 240) {
                    if (event.y > 100 && event.y <= 148) {
                        if (Settings.soundEnabled)
                            Assets.click.play(1);
                        state = GameState.Running;
                        return;
                    }
                    if (event.y > 148 && event.y < 196) {
                        if (Settings.soundEnabled)
                            Assets.click.play(1);
                        game.setScreen(new MainMenuScreen(game));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x >= 128 && event.x <= 192 && event.y >= 200
                        && event.y <= 264) {
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.clear(Color.BLACK);

        g.drawPixmap(Assets.background, 0, 0);
        drawWorld(world);
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

        drawText(g, score, g.getWidth() / 2 - score.length() * 20 / 2,
                g.getHeight() - 42);
    }

    private void drawWorld(World world) {
        Graphics g = game.getGraphics();
        Snake snake = world.snake;
        Snake enemySnake = world.enemySnake;

        SnakePart head = snake.parts.get(0);
        SnakePart enemyHead = enemySnake.parts.get(0);
        Stain stain = world.stain;

        Pixmap stainPixmap = null;
        if (stain.type == Stain.TYPE_1)
            stainPixmap = Assets.stain1;
        if (stain.type == Stain.TYPE_2)
            stainPixmap = Assets.stain2;
        if (stain.type == Stain.TYPE_3)
            stainPixmap = Assets.stain3;

        int x = stain.x * 64;
        int y = stain.y * 64;
        g.drawPixmap(stainPixmap, x, y);

        int len = 0;
        if (snake.parts.size() > enemySnake.parts.size()) {
            len = snake.parts.size();
        } else {
            len = enemySnake.parts.size();
        }

        for (int i = 1; i < len; i++) {
            if (snake.parts.size() > i) {
                SnakePart part = snake.parts.get(i);
                x = part.x * 64;
                y = part.y * 64;
                g.drawPixmap(Assets.tail, x, y);
            }
            if (enemySnake.parts.size() > i) {
                SnakePart part = enemySnake.parts.get(i);
                x = part.x * 64;
                y = part.y * 64;
                g.drawPixmap(Assets.tailEn, x, y);
            }
        }

        Pixmap headPixmap = null;
        if (snake.direction == Snake.UP)
            headPixmap = Assets.headup;
        if (snake.direction == Snake.LEFT)
            headPixmap = Assets.headleft;
        if (snake.direction == Snake.DOWN)
            headPixmap = Assets.headdown;
        if (snake.direction == Snake.RIGHT)
            headPixmap = Assets.headright;
        x = head.x * 64 + 32;
        y = head.y * 64 + 32;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2,
                y - headPixmap.getHeight() / 2);
        if (enemySnake.direction == Snake.UP)
            headPixmap = Assets.headupEn;
        if (enemySnake.direction == Snake.LEFT)
            headPixmap = Assets.headleftEn;
        if (enemySnake.direction == Snake.DOWN)
            headPixmap = Assets.headdownEn;
        if (enemySnake.direction == Snake.RIGHT)
            headPixmap = Assets.headrightEn;
        x = enemyHead.x * 64 + 32;
        y = enemyHead.y * 64 + 32;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2,
                y - headPixmap.getHeight() / 2);
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.ready, 47, 100);
        g.drawLine(0, 1216, 800, 1216, Color.BLACK);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 1216, 800, 1216, Color.BLACK);
        g.drawPixmap(Assets.buttons, 0, 1216, 64, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 736, 1216, 0, 64, 64, 64);
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.pause, 80, 100);
        g.drawLine(0, 1216, 800, 1216, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.gameover, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 1216, 800, 1216, Color.BLACK);
    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {
        if (state == GameState.Running)
            state = GameState.Paused;

        if (world.gameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void backButton() {
        // TODO Auto-generated method stub
        if (state == GameState.Running) {
            pause();
            //} else if(state == GameState.Paused) {
            //state = GameState.Running;
        } else {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
