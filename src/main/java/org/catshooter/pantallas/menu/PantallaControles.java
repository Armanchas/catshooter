package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.catshooter.core.Juego;
import org.lwjgl.opengl.GL20;

public class PantallaControles extends PantallaAbstracta{
    private final PantallaMenu pantallaMenu;
    public PantallaControles(Juego juego, PantallaMenu pantallaMenu) {
        super(juego);
        this.pantallaMenu = pantallaMenu;
    }
    @Override
    public void configurarMusica() {

    }
    @Override
    public void gestionarTeclas() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            juego.setScreen(pantallaMenu);
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

    }
}
