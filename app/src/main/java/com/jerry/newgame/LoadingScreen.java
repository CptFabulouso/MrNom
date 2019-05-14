package com.jerry.newgame;

import com.jerry.newgame.Assets;

import android.util.Log;

import framework.Game;
import framework.Graphics;
import framework.Graphics.PixmapFormat;
import framework.Screen;

public class LoadingScreen extends Screen {
    public static boolean loaded;

    public LoadingScreen(Game game) {
        super(game);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();

        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.stain1 = g.newPixmap("stain1.png", PixmapFormat.ARGB4444);
        Assets.stain2 = g.newPixmap("stain2.png", PixmapFormat.ARGB4444);

        loaded = true;
        game.setScreen(new NewMenuScreen(game));

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
