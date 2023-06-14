package org.catshooter.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.catshooter.core.Juego;
import org.catshooter.entidades.Enemigo;
import org.catshooter.entidades.Jugador;
import org.lwjgl.opengl.GL20;

import java.util.HashMap;

public class PantallaJuego extends PantallaAbstracta{

    public Enemigo[] enemigos;
    public Texture imagen;
    public Texture imagen2;
    public PantallaJuego(Juego juego) {
        super(juego);
        enemigos = new Enemigo[2*4];
        imagen = new Texture("nave.png");
        imagen2 = new Texture("bala.png");

    }
    @Override
    public void show() {
        int i = 0;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 4; x++) {
                enemigos[i] = new Enemigo(new Vector2(x*120,y*120),imagen,imagen2);
                i++;
            }
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        actualizarServidor();

        if(getJugador() != null) {

            matarEntidad();

            if (!jugador.isEstaVivo()) {
                cambiarPantalla(new PantallaGameOver(juego));
            }

            getJugador().update(Gdx.graphics.getDeltaTime());

            for (Enemigo enemigo : enemigos) {
                enemigo.update();
            }

            juego.getBatch().begin();


            for (Enemigo enemigo : enemigos) {
                if (enemigo.EstaVivo()) {
                    enemigo.draw(juego.getBatch());
                    enemigo.dibujarBala(juego.getBatch());
                }
            }

            if (jugador.isEstaVivo()) {
                getJugador().draw(juego.getBatch());
                getJugador().dibujarBala(juego.getBatch());

            }
            //se dibujan todos los aliados y sus disparos
            for(HashMap.Entry<String, Jugador> entry : getAliados().entrySet()){
                entry.getValue().draw(juego.getBatch());
                entry.getValue().dibujarBala(juego.getBatch());
            }
            juego.getBatch().end();
        }

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

    }

    @Override
    public void dispose() {
        imagen.dispose();
        imagen2.dispose();
        imgBala.dispose();
    }
    public void matarEntidad() {
        for (Enemigo enemigo : enemigos) {
            if (jugador.getSpriteBala().getBoundingRectangle().overlaps(enemigo.getBoundingRectangle())) {
                enemigo.setEstaVivo(false);
            }
            if (enemigo.getBala().getBoundingRectangle().overlaps(jugador.getBoundingRectangle())) {
                jugador.setEstaVivo(false);
            }
        }
    }
}
