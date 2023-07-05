package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import org.catshooter.core.Juego;
import org.lwjgl.opengl.GL20;

public class PantallaVictoria extends PantallaAbstracta {
    private final Texture imagen;
    public PantallaVictoria(Juego juego) {
        super(juego);
        imagen = new Texture("menu/gg.png");

        configurarMusica();
    }
    @Override
    public void configurarMusica() {
        gestorDeAudio.cargarMusica("audio/menu/ending.wav","ending");
        gestorDeAudio.getMusica("ending").setVolume(0.14f);
        gestorDeAudio.getMusica("ending").play();
    }
    @Override
    public void gestionarTeclas() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            juego.setScreen(new PantallaMenu(juego));
        }
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gestionarTeclas();

        batch.begin();
        batch.draw(imagen,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        gestorDeAudio.getMusica("ending").stop();
    }

    @Override
    public void dispose() {

    }
}
