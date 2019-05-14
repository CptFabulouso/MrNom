package com.jerry.mrnom;

import com.jerry.newgame.LoadingScreen;

import framework.Screen;
import impl.AndroidGame;

public class NewGame extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        // TODO Auto-generated method stub
        return new LoadingScreen(this);
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }


}
