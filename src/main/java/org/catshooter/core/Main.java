package org.catshooter.core;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("test");
        config.setForegroundFPS(60);
        config.useVsync(true);
        config.setResizable(false);
        config.setWindowedMode(900,600);
        new Lwjgl3Application(new Juego(), config);
    }
}