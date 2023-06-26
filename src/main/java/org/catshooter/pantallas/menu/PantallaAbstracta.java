package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.catshooter.core.Juego;

public abstract class PantallaAbstracta implements Screen {
    protected Juego juego;
    protected SpriteBatch batch;
    protected Stage stage;
    public PantallaAbstracta(Juego juego) {
        this.juego = juego;
        batch = new SpriteBatch();
        stage = new Stage();
    }
}
