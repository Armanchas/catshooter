package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.catshooter.core.Juego;
import org.lwjgl.opengl.GL20;

public class PantallaVictoria extends PantallaAbstracta {
    private final Texture imagen;
    private int contador;
    public PantallaVictoria(Juego juego) {
        super(juego);
        imagen = new Texture("menu/GG.png");
        contador = 0;
    }
    private void enviarAMenuPrincipal(){
        if (contador > 100) {
            juego.setScreen(new PantallaMenu(juego));
        }
    }
    @Override
    public void configurarMusica() {

    }
    @Override
    public void gestionarTeclas() {

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(imagen,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
        contador++;
        enviarAMenuPrincipal();
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
