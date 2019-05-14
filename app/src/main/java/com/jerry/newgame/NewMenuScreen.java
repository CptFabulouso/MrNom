package com.jerry.newgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import com.jerry.newgame.Assets;

import framework.Game;
import framework.Graphics;
import framework.Screen;
import framework.Input.TouchEvent;

public class NewMenuScreen extends Screen {

    public List<Points> pointsList = new ArrayList<Points>();
    public Points movePoint;
    int[] movingPoints = new int[10];
    public static final int PRECISION = 64;
    public static final int SENSITIVITY = 20;
    float delta;
    boolean[] touchedExistingPoint = new boolean[10];
    boolean isTouchingPoint;
    int touchingPoint;
    float speed = 0.02f;
    ;
    String text = null;
    StringBuilder builder = new StringBuilder();
    ;
    String lastTouch = null;
    int start;
    int end;
    PressPoint pressPoint = new PressPoint();
    float moveX;
    float moveY;
    Controller controller = new Controller();
    Controller controllShoot = new Controller();
    Random random = new Random();
    float globalTime = 0;
    int speedOfAddingPoints = 1;
    Point endOfShootLine = new Point(0, 0);

    boolean[][] inCollision = new boolean[10][10];

    public NewMenuScreen(Game game) {
        super(game);
        pointsList.add(new Points(100, 100));
        pointsList.add(new Points(50, 200));
        pointsList.add(new Points(200, 50));
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();

        builder.setLength(0);
        builder.append("accelX =");
        builder.append(game.getInput().getAccelX());
        builder.append(":accelY =");
        builder.append(game.getInput().getAccelY());
        builder.append(":accelZ =");
        builder.append(game.getInput().getAccelZ());
        builder.append(":");

		/*
         * for (int j = 0; j < 10; j++) { if (game.getInput().isTouchDown(j)) {
		 * if (game.getInput().getTouchX(j) < 160 &&
		 * game.getInput().getTouchY(j) > 360) { if (!controller.active) {
		 * controller.pointer = j; controller.active = true; } } if
		 * (game.getInput().getTouchX(j) > 160 && game.getInput().getTouchY(j) >
		 * 360) { if (!controllShoot.active) { controllShoot.active = true;
		 * controllShoot.pointer = j; } } } }
		 */

        if (controller.active) {
            if (game.getInput().isTouchDown(controller.pointer)) {
                controller.update(
                        game.getInput().getTouchX(controller.pointer), game
                                .getInput().getTouchY(controller.pointer));
                int distanceX = game.getInput().getTouchX(controller.pointer)
                        - controller.pressedPointx;
                int distanceY = game.getInput().getTouchY(controller.pointer)
                        - controller.pressedPointy;

                moveX = (game.getInput().getTouchX(controller.pointer) - controller.pressedPointx)
                        / SENSITIVITY;
                moveY = (game.getInput().getTouchY(controller.pointer) - controller.pressedPointy)
                        / SENSITIVITY;
                if (moveX == 0)
                    moveX = 1;
                if (moveY == 0)
                    moveY = 1;
                pointsList.get(0).setX(pointsList.get(0).getX() + moveX);
                pointsList.get(0).setY(pointsList.get(0).getY() + moveY);
                if (controller.deltaTime > 0) {
                    controller.deltaTime = 0;
                }
            } else {
                controller.deltaTime += 1;
                if (controller.deltaTime > 2) {
                    controller.active = false;
                    controller.deltaTime = 0;
                }

            }
        }

        if (controllShoot.active) {
            if (game.getInput().isTouchDown(controllShoot.pointer)) {
                controllShoot.update(
                        game.getInput().getTouchX(controllShoot.pointer), game
                                .getInput().getTouchY(controllShoot.pointer));
                endOfShootLine.x = (int) (pointsList.get(0).getX() + (controllShoot.localX / controllShoot.innerRadius) * (500));
                endOfShootLine.y = (int) (pointsList.get(0).getY() + (controllShoot.localY / controllShoot.innerRadius) * (500));
                if (controllShoot.deltaTime > 0) {
                    controllShoot.deltaTime = 0;
                }
            } else {
                controllShoot.deltaTime += 1;
                if (controllShoot.deltaTime > 2) {
                    controllShoot.active = false;
                    controllShoot.deltaTime = 0;
                }
            }
        }

        for (int i = 0; i < pointsList.size(); i++) {
            if (pointsList.get(i).isTouched) {
                if (game.getInput().isTouchDown(
                        pointsList.get(i).pointerTouched)) {
                    pointsList.get(i).setX(
                            game.getInput().getTouchX(
                                    pointsList.get(i).pointerTouched));
                    pointsList.get(i).setY(
                            game.getInput().getTouchY(
                                    pointsList.get(i).pointerTouched));
                } else {
                    pointsList.get(i).isTouched = false;
                }
            }
        }

        for (int j = 0; j < 10; j++) {
            builder.append("pointer =");
            builder.append(j);
            builder.append(", touchDown =");
            builder.append(game.getInput().isTouchDown(j));
            builder.append(", x =");
            builder.append(game.getInput().getTouchX(j));
            builder.append(", y =");
            builder.append(game.getInput().getTouchY(j));
            builder.append(":");
        }

        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            builder.append("touchType =");
            builder.append(event.type);
            builder.append(", x =");
            builder.append(event.x);
            builder.append(", y =");
            builder.append(event.y);
            builder.append(":");

            // Player touched control zone, activate controller
            if (event.type == TouchEvent.TOUCH_DOWN) {
                boolean newEnemy = true;
                if (event.x < 400 && event.y > 980) {
                    controller.create(event.x, event.y, event.pointer, 100, 80);
                    newEnemy = false;
                }
                if (event.x > 400 && event.y > 980) {
                    controllShoot.create(event.x, event.y, event.pointer,
                            100, 80);
                    newEnemy = false;
                }
                if (!newEnemy) {
                    continue;
                }
                for (int j = 0; j < pointsList.size(); j++) {
                    Points point = pointsList.get(j);
                    if (event.x > point.getX() - PRECISION
                            && event.x < point.getX() + PRECISION
                            && event.y > point.getY() - PRECISION
                            && event.y < point.getY() + PRECISION) {
                        point.isTouched = true;
                        point.pointerTouched = event.pointer;
                        break;
                    } else {
                        if (j == pointsList.size() - 1) {
                            if (pointsList.size() < 10) {
                                pointsList.add(new Points(event.x, event.y));
                                pointsList.get(pointsList.size() - 1).isTouched = true;
                                pointsList.get(pointsList.size() - 1).pointerTouched = event.pointer;
                            }
                        }
                    }
                }
            }
        }
        text = builder.toString();
        delta += deltaTime;

        if (delta > speed) {
            for (int i = 3; i < pointsList.size(); i++) {
                /*
				 * pointsList.get(j).drawPLine((int) pointsList.get(1).getX(),
				 * (int) pointsList.get(1).getY(), (int)
				 * pointsList.get(2).getX(), (int) pointsList.get(2).getY());
				 */
                //pointsList.get(i).move(delta, pointsList.get(0).getX(),
                //		 pointsList.get(0).getY());
                for (int j = i + 1; j < pointsList.size(); j++) {
                    inCollision[i][j] = pointsList.get(i).checkCollision(
                            pointsList.get(j).getX(), pointsList.get(j).getY());
                    if (inCollision[i][j]) {

                    } else {
                        //	pointsList.get(i).move(delta, pointsList.get(0).getX(),
                        //			 pointsList.get(0).getY());
                    }
                }
                if (controllShoot.active) {
                    pointsList.get(i).checkDamage(
                            (int) pointsList.get(0).getX(),
                            (int) pointsList.get(0).getY(),
                            endOfShootLine.x, endOfShootLine.y);
                    if (pointsList.get(i).health <= 0) {
                        pointsList.remove(i);
                        continue;
                    }

                }
            }
            delta -= speed;
            globalTime += deltaTime;
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawLine((int) pointsList.get(1).getX(), (int) pointsList.get(1)
                .getY(), (int) pointsList.get(2).getX(), (int) pointsList
                .get(2).getY(), Color.BLACK);
        if (controllShoot.active) {
            g.drawLine((int) pointsList.get(0).getX(), (int) pointsList.get(0)
                    .getY(), endOfShootLine.x, endOfShootLine.y, Color.RED);
        }
        // draw player
        g.drawPixmap(Assets.stain2, (int) pointsList.get(0).getX() - 32,
                (int) pointsList.get(0).getY() - 32);
        // draw the rest of points
        for (int i = 1; i < pointsList.size(); i++) {
            g.drawRect((int) pointsList.get(i).getX() - 40, (int) pointsList
                            .get(i).getY() - 40, (int) (pointsList.get(i).health / 10),
                    5, Color.GREEN);

            g.drawCircle((int) pointsList.get(i).getX(), (int) pointsList
                    .get(i).getY(), 64, pointsList.get(i).color);

        }
        for (int i = 1; i < pointsList.size(); i++) {
            if (i > 2) {
                for (int j = i + 1; j < pointsList.size(); j++)
                    if (inCollision[i][j]) {
                        g.drawLine((int) pointsList.get(i).getX(),
                                (int) pointsList.get(i).getY(),
                                (int) pointsList.get(j).getX(),
                                (int) pointsList.get(j).getY(), Color.RED);
                    } else {
                        g.drawLine((int) pointsList.get(i).getX(),
                                (int) pointsList.get(i).getY(),
                                (int) pointsList.get(j).getX(),
                                (int) pointsList.get(j).getY(), Color.GRAY);
                    }
            }
        }
        if (controller.active) {
            g.drawCircle(controller.pressedPointx, controller.pressedPointy,
                    controller.radius, Color.BLACK);
            g.drawCircle(controller.innerCirclex, controller.innerCircley,
                    controller.innerCircleRadius, Color.RED);
        }
        if (controllShoot.active) {
            g.drawCircle(controllShoot.pressedPointx,
                    controllShoot.pressedPointy, controllShoot.radius,
                    Color.GRAY);
            g.drawCircle(controllShoot.innerCirclex,
                    controllShoot.innerCircley,
                    controllShoot.innerCircleRadius, Color.RED);
        }

        g.drawLine(0, 980, g.getWidth(), 980, Color.BLACK);
        g.drawLine(400, 980, 400, g.getHeight(), Color.BLACK);
        if (text != null) {
            start = 0;
            for (int i = 0; i < 30; i++) {
                end = text.indexOf(':', start);
                g.drawText(text.substring(start, end), 10, 40 + (i * 40),
                        Color.BLACK, 32);
                start = end + 1;
                if (text.lastIndexOf(':') == end) {
                    break;
                }
            }
        }

    }

    @Override
    public void pause() {
        android.os.Process.killProcess(android.os.Process.myPid());

    }

    public void addPoints() {
        int rndm = random.nextInt(100);
        if (rndm > 50) {
            pointsList.add(new Points(0, rndm));
        }
        if (rndm < 50) {
            pointsList.add(new Points(rndm, 0));
        }
    }

    @Override
    public void backButton() {
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

    public boolean checkTouching(TouchEvent event, float x, float y) {
        if (event.x > x - PRECISION && event.x < x + PRECISION
                && event.y > y - PRECISION && event.y < y + PRECISION) {
            Log.w("LOG", "eventX = " + event.x + ", x = " + x + ", eventY = "
                    + event.y + ", y =" + y);
            return true;
        } else {
            return false;
        }

    }
}
