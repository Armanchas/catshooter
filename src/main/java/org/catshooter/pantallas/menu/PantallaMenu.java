package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Gdx;
import org.catshooter.core.Juego;
import org.catshooter.pantallas.juego.PantallaJuego;
import org.catshooter.pantallas.juego.PantallaJuegoMultiplayer;
import org.lwjgl.opengl.GL20;

public class PantallaMenu extends PantallaAbstracta {
    private final Menu menu;
    public PantallaMenu(Juego juego) {
        super(juego);

        menu = new Menu(stage,"JUGAR","MULTIPLAYER","CONTROLES","SALIR");
        configurarMusica();
    }
    public void gestionarTeclas() {
        if (menu.SePresionoBoton1()) {
            gestorDeAudio.getMusica("menu").stop();
            juego.setScreen(new PantallaJuego(juego));
        }
        if (menu.SePresionoBoton2()) {
            gestorDeAudio.getMusica("menu").stop();
            juego.setScreen(new PantallaJuegoMultiplayer(juego));
        }
        if (menu.SePresionoBoton3()) {
            juego.setScreen(new PantallaControles(juego,this));
            menu.setSePresionoBoton3(false);
        }
        if (menu.SePresionoBoton4()) {
            Gdx.app.exit();
        }
    }
    public void configurarMusica() {
        gestorDeAudio.cargarMusica("audio/menu/menu.wav","menu");
        gestorDeAudio.getMusica("menu").setVolume(0.1f);
        gestorDeAudio.getMusica("menu").setLooping(true);
        gestorDeAudio.getMusica("menu").play();
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gestionarTeclas();

        stage.act();
        stage.draw();
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

    }

    @Override
    public void dispose() {
        gestorDeAudio.getMusica("menu").dispose();
    }
}
