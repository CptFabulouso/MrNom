package com.jerry.jumpgame;

import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.jerry.snake.Assets;
import com.jerry.snake.Settings;

import framework.Game;
import framework.Graphics;
import framework.Screen;
import framework.Input.TouchEvent;

public class GameScreen extends Screen {

    World world;
    Player player;

    public GameScreen(Game game) {
        super(game);
        world = new World();
        player = world.getPlayer();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        updateRunning(touchEvents, deltaTime);

    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {

        int len = touchEvents.size();
        if (len > 0)
            player.jump();
        /*
        for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			
			if(event.type == TouchEvent.TOUCH_UP){
				player.jump();
			}
		}
		*/
        world.update(deltaTime);
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.clear(Color.BLACK);

        drawRunningUI();

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();


        g.drawRect(player.x, player.y, player.width, player.height, Color.BLUE);

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void backButton() {
        pause();
        android.os.Process.killProcess(android.os.Process.myPid());

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
