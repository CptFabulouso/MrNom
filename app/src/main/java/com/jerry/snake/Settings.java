package com.jerry.snake;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import framework.FileIO;

public class Settings {

    public static boolean soundEnabled = true;
    public static int[] highscores = new int[]{100, 80, 50, 30, 10};

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(".snake")));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException e) {
            Log.w("LOG", "couldn't load (IOException) defaults will be used");
        } catch (NumberFormatException e) {
            Log.w("LOG", "couldn't load (NumFormatExc) defaults will be used");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public static void save(FileIO files) {
        BufferedWriter out = null;
        StringBuilder builder = new StringBuilder();
        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(".snake")));
            builder.append(Boolean.toString(soundEnabled));
            builder.append("\n");
            out.write(builder.toString());
            builder.delete(0, builder.length());
            for (int i = 0; i < 5; i++) {
                builder.append(Integer.toString(highscores[i]));
                builder.append("\n");
            }
            out.write(builder.toString());
        } catch (IOException e) {
            Log.w("LOG", "couldn't save (IOException) defaults will be used");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public static void addScore(int score) {
        for (int i = 0; i < 5; i++) {
            if (highscores[i] < score) {
                for (int j = 4; j > i; j--) {
                    highscores[j] = highscores[j - 1];
                }
                highscores[i] = score;
                break;
            }
        }

    }


}
