package org.catshooter.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.catshooter.core.Juego;
import org.catshooter.efectos.Explosion;
import org.catshooter.entidades.Enemigo;
import org.catshooter.entidades.Jugador;
import org.lwjgl.opengl.GL20;

import java.util.HashMap;

public class PantallaJuego extends PantallaJuegoAbstracta {

    public Enemigo[] enemigos;
    public Texture enemigoTextura;
    public Texture enemigoBalaTextura;
    public Explosion[] explosiones;
    public PantallaJuego(Juego juego) {
        super(juego);
        enemigoTextura = new Texture("entidades/nave.png");
        enemigoBalaTextura = new Texture("entidades/bala.png");
        enemigos = new Enemigo[2*4];
        explosiones = new Explosion[enemigos.length];

        llenarEfectos();
        generarEnemigos();
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        actualizarServidor();

        if(getJugador() != null) {

            if (!jugador.isEstaVivo()) {
                cambiarPantalla(new PantallaGameOver(juego));
            }

            actualizarEntidades(delta);

            juego.getBatch().begin();

            matarEntidad(juego.getBatch());

            dibujarJugador();
            dibujarAliados();
            dibujarEnemigos();

            juego.getBatch().end();
        }

    }
    public void actualizarEntidades(float delta) {
        getJugador().update(delta);

        for (Enemigo enemigo : enemigos) {
            enemigo.update(delta);
        }
    }
    public void dibujarJugador() {
        if (jugador.isEstaVivo()) {
            getJugador().draw(juego.getBatch());
            getJugador().getBala().draw(juego.getBatch());
        }
    }
    public void dibujarAliados() {
        for(HashMap.Entry<String, Jugador> entry : getAliados().entrySet()){
            entry.getValue().draw(juego.getBatch());
            entry.getValue().getBala().draw(juego.getBatch());
        }
    }
    public void generarEnemigos() {
        int i = 0;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 4; x++) {
                enemigos[i] = new Enemigo(new Vector2(x*120,y*120), enemigoTextura, enemigoBalaTextura);
                i++;
            }
        }
    }
    public void dibujarEnemigos() {
        for (Enemigo enemigo : enemigos) {
            enemigo.getBala().draw(juego.getBatch());
            if (enemigo.EstaVivo()) {
                enemigo.draw(juego.getBatch());
            }
            if (!enemigo.EstaVivo() && enemigo.balaEstaFueraDePantalla()) {
                enemigo.getBala().setPosition(-2000,2000);
            }
        }
    }
    public void matarEntidad(SpriteBatch batch) {
        Rectangle hitboxJugador = jugador.getBoundingRectangle();
        Rectangle hitboxBala = jugador.getBala().getBoundingRectangle();

        int i = 0;
        for (Enemigo enemigo : enemigos) {
            if (hitboxBala.overlaps(enemigo.getBoundingRectangle()) && enemigo.EstaVivo()) {
                enemigo.setEstaVivo(false);
                enemigo.setSpeed(0);
            }
            if (!enemigo.EstaVivo()) {
                float x = enemigo.getX();
                float y = enemigo.getY();

                explosiones[i].setFrameActual(explosiones[i].getFrameActual() + Gdx.graphics.getDeltaTime());
                explosiones[i].animar(batch,x,y);
            }
            if (enemigo.getBala().getBoundingRectangle().overlaps(hitboxJugador) && !jugador.EsInvencible()) {
                jugador.setTimer(2f);
                jugador.restarVida();
                jugador.setEsInvencible(true);
                if (jugador.getVidas() == 0) {
                    jugador.setEstaVivo(false);
                }
            }
            i++;
        }
    }
    public void llenarEfectos() {
        for (int i = 0; i < explosiones.length; i++) {
            explosiones[i] = new Explosion();
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
        enemigoTextura.dispose();
        enemigoBalaTextura.dispose();
    }
}
