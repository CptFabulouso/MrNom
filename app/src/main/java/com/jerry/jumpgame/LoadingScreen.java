package com.jerry.jumpgame;


import framework.Game;
import framework.Screen;

public class LoadingScreen extends Screen {

    public LoadingScreen(Game game) {
        super(game);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(float deltaTime) {

        game.setScreen(new GameScreen(game));

    }

    @Override
    public void present(float deltaTime) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void backButton() {
        // TODO Auto-generated method stub

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
