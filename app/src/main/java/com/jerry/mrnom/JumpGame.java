package com.jerry.mrnom;

import impl.AndroidGame;

import com.jerry.jumpgame.LoadingScreen;

import framework.Screen;

public class JumpGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }
}
