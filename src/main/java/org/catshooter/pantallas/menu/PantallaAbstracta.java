package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import org.catshooter.controladores.GestorDeAudio;
import org.catshooter.core.Juego;

public abstract class PantallaAbstracta implements Screen {
    protected Juego juego;
    protected SpriteBatch batch;
    protected Stage stage;
    protected GestorDeAudio gestorDeAudio;
    public PantallaAbstracta(Juego juego) {
        this.juego = juego;
        batch = new SpriteBatch();
        stage = new Stage();
        gestorDeAudio = new GestorDeAudio();
    }
    public abstract void configurarMusica();
    public abstract void gestionarTeclas();
}
