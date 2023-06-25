package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import org.catshooter.core.Juego;
import org.catshooter.pantallas.juego.PantallaJuego;
import org.catshooter.pantallas.juego.PantallaJuegoAbstracta;
import org.lwjgl.opengl.GL20;

public class PantallaGameOver extends PantallaAbstracta {
    private final Texture imagen = new Texture("menu/gameover.png");;
    private final PantallaJuegoAbstracta pantallaActual;
    public PantallaGameOver(Juego juego) {
        super(juego);
        pantallaActual = (PantallaJuegoAbstracta) juego.getScreen();

    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        reintentar();
        batch.begin();
        batch.draw(imagen, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void reintentar() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (pantallaActual instanceof PantallaJuego) {
                juego.setScreen(new PantallaJuego(juego));
            }
        }
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

    }

    @Override
    public void dispose() {
        imagen.dispose();
    }
}


