package org.catshooter.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.catshooter.pantallas.menu.PantallaMenu;

public class Juego extends Game {
    public static SpriteBatch BATCH;
    @Override
    public void create() {
        BATCH = new SpriteBatch();
        setScreen(new PantallaMenu(this));
    }
}