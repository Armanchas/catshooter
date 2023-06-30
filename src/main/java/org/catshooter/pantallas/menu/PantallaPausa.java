package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import org.catshooter.core.Juego;
import org.catshooter.pantallas.juego.PantallaJuego;
import org.lwjgl.opengl.GL20;

public class PantallaPausa extends PantallaAbstracta{
    private Texture fondo;
    private PantallaJuego pantallaActual;
    public PantallaPausa(Juego juego) {
        super(juego);
        fondo = new Texture("menu/gameover.png");
        pantallaActual = (PantallaJuego) juego.getScreen();

        configurarMusica();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        establecerAcciones();
        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
    }
    public void establecerAcciones() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            juego.setScreen(pantallaActual);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            juego.setScreen(new PantallaMenu(juego));
        }
    }
    public void configurarMusica() {
        gestorDeAudio.cargarMusica("audio/menu/pausa.wav","pausa");
        gestorDeAudio.getMusica("pausa").setVolume(0.1f);
        gestorDeAudio.getMusica("pausa").setLooping(true);
        gestorDeAudio.getMusica("pausa").play();
    }
    @Override
    public void show() {

    }
    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {
        gestorDeAudio.getMusica("pausa").pause();
    }
    @Override
    public void dispose() {
        fondo.dispose();
        gestorDeAudio.getMusica("pausa").dispose();
    }
}
