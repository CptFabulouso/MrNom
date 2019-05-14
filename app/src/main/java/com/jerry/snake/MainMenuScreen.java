package com.jerry.snake;

import java.util.List;

import framework.Game;
import framework.Graphics;
import framework.Input.TouchEvent;
import framework.Screen;

public class MainMenuScreen extends Screen {

    public MainMenuScreen(Game game) {
        super(game);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            /*
             * We first fetch the TouchEvent and KeyEvent instances from the
			 * Input instance the Game provides us. Note that we do not use the
			 * KeyEvent instances, but we fetch them anyway in order to clear
			 * the internal buffer (yes, that�s a tad bit nasty, but let�s make
			 * it a habit). We then loop over all the TouchEvent instances until
			 * we find one with the type TouchEvent.TOUCH_UP. (We could
			 * alternatively look for TouchEvent.TOUCH_DOWN events, but in most
			 * UIs the up event is used to indicate that a UI component was
			 * pressed.)
			 */
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if (Settings.soundEnabled) {
                        Assets.click.play(1);
                        Assets.music.play();
                    } else {
                        Assets.music.pause();
                    }
                }
                if (inBounds(event, 160, 550, 192, 42)) {
                    game.setScreen(new GameScreen(game));
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if (inBounds(event, 160, 550 + 42, 192, 42)) {
                    game.setScreen(new HighscoreScreen(game));
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if (inBounds(event, 160, 550 + 84, 192, 42)) {
                    game.setScreen(new HelpScreen(game));
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
            }
        }

    }

    /*
     * put in a TouchEvent and a rectangle, and it tells you whether the touch
     * event�s coordinates are inside that rectangle.
     */
    private boolean inBounds(TouchEvent event, int x, int y, int width,
                             int height) {
        if (event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.logo, 80, 50);
        g.drawPixmap(Assets.mainmenu, 160, 550);
        if (Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, 0, 1216, 0, 0, 64, 64);
        else
            g.drawPixmap(Assets.buttons, 0, 1216, 64, 0, 64, 64);

    }

    @Override
    public void backButton() {
        pause();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void pause() {
        Settings.save(game.getFileIO());
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
