package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import org.catshooter.core.Juego;
import org.catshooter.pantallas.juego.PantallaBossFinal;
import org.catshooter.pantallas.juego.PantallaJuego;
import org.catshooter.pantallas.juego.PantallaJuegoAbstracta;
import org.catshooter.pantallas.juego.PantallaJuegoMultiplayer;
import org.lwjgl.opengl.GL20;

public class PantallaGameOver extends PantallaAbstracta {
    private final Texture imagen = new Texture("menu/gameover.png");
    private final PantallaJuegoAbstracta pantallaActual;
    public PantallaGameOver(Juego juego) {
        super(juego);
        pantallaActual = (PantallaJuegoAbstracta) juego.getScreen();

        configurarMusica();
    }
    @Override
    public void configurarMusica() {
        gestorDeAudio.cargarMusica("audio/menu/gameover.wav","gameover");
        gestorDeAudio.getMusica("gameover").setVolume(0.12f);
        gestorDeAudio.getMusica("gameover").play();
    }
    public void gestionarTeclas() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (pantallaActual instanceof PantallaJuegoMultiplayer) {
                juego.setScreen(new PantallaJuegoMultiplayer(juego));
            }
            if (pantallaActual instanceof PantallaJuego) {
                juego.setScreen(new PantallaJuego(juego));
            }
            if (pantallaActual instanceof PantallaBossFinal) {
                juego.setScreen(new PantallaJuego(juego));
            }
        }
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gestionarTeclas();
        batch.begin();
        batch.draw(imagen, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }
    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        gestorDeAudio.getMusica("gameover").stop();
    }
    @Override
    public void dispose() {
        imagen.dispose();
        gestorDeAudio.getMusica("gameover").dispose();
    }
}


