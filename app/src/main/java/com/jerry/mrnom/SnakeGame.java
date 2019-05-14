package com.jerry.mrnom;

import android.content.Intent;
import android.util.Log;

import com.jerry.snake.LoadingScreen;
import com.jerry.snake.MainMenuScreen;

import framework.Screen;
import impl.AndroidFastRenderView;
import impl.AndroidGame;

public class SnakeGame extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }

}
