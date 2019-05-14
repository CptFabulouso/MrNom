package com.jerry.jumpgame;

public class World {
    static final int WORLD_WIDTH = 800;
    static final int WORLD_HEIGHT = 1280;

    float tickTime = 0;
    float tick = 0.01f;

    private Player player;

    public World() {
        player = new Player(0, 600, 50, 50);
    }

    public void update(float deltaTime) {
        tickTime += deltaTime;

        while (tickTime > tick) {
            tickTime -= tick;
            player.move();
        }
    }

    public Player getPlayer() {
        return player;
    }
}
