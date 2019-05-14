package com.jerry.snake;

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
        Assets.background2 = g.newPixmap("background2.png", PixmapFormat.RGB565);
        Assets.gameover = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
        Assets.headdown = g.newPixmap("headdown.png", PixmapFormat.ARGB4444);
        Assets.headup = g.newPixmap("headup.png", PixmapFormat.ARGB4444);
        Assets.headleft = g.newPixmap("headleft.png", PixmapFormat.ARGB4444);
        Assets.headright = g.newPixmap("headright.png", PixmapFormat.ARGB4444);
        Assets.headdownEn = g.newPixmap("headdownEn.png", PixmapFormat.ARGB4444);
        Assets.headupEn = g.newPixmap("headupEn.png", PixmapFormat.ARGB4444);
        Assets.headleftEn = g.newPixmap("headleftEn.png", PixmapFormat.ARGB4444);
        Assets.headrightEn = g.newPixmap("headrightEn.png", PixmapFormat.ARGB4444);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.mainmenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("pause.png", PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.stain1 = g.newPixmap("stain1.png", PixmapFormat.ARGB4444);
        Assets.stain2 = g.newPixmap("stain2.png", PixmapFormat.ARGB4444);
        Assets.stain3 = g.newPixmap("stain3.png", PixmapFormat.ARGB4444);
        Assets.tail = g.newPixmap("tail.png", PixmapFormat.ARGB4444);
        Assets.tailEn = g.newPixmap("tailEn.png", PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);

        Assets.click = game.getAudio().newSound("click.ogg");
        Assets.eat = game.getAudio().newSound("eat.ogg");
        Assets.bitten = game.getAudio().newSound("bitten.ogg");

        Assets.music = game.getAudio().newMusic("hth.mp3");

        Settings.load(game.getFileIO());
        if (Settings.soundEnabled) {
            Assets.music.setVolume(0.5f);
            Assets.music.setLooping(true);
            Assets.music.play();
        }

        loaded = true;
        game.setScreen(new MainMenuScreen(game));

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
