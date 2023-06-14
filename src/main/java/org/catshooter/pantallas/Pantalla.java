package org.catshooter.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.catshooter.core.Juego;

public abstract class Pantalla implements Screen {
    protected Juego juego;
    protected SpriteBatch batch;
    protected Viewport viewport;
    protected Stage stage;
    public Pantalla(Juego juego) {
        this.juego = juego;
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage();
    }
}
