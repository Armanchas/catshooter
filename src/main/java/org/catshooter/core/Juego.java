package org.catshooter.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.catshooter.pantallas.PantallaJuego;

public class Juego extends Game {
    private SpriteBatch batch;
    public SpriteBatch getBatch() {
        return batch;
    }
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PantallaJuego(this));
    }
}