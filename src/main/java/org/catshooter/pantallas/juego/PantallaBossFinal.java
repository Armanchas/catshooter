package org.catshooter.pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.catshooter.core.Juego;
import org.catshooter.entidades.Jugador;
import org.catshooter.entidades.enemigos.Boss;
import org.catshooter.pantallas.menu.PantallaGameOver;
import org.lwjgl.opengl.GL20;

public class PantallaBossFinal extends PantallaJuegoAbstracta {
    private final Boss jefeFinal;

    public PantallaBossFinal(Juego juego) {
        super(juego);
        fondo = new Texture("juego/fondo2.png");

        jugador = new Jugador(jugadorTextura, balaTextura);
        jefeFinal = new Boss(new Vector2(0, 0), finalBossTexture, proyectilJefe);
    }

    public void matarEntidad() {
        Rectangle hitboxJugador = jugador.getBoundingRectangle();
        Rectangle hitboxBala = jugador.getBala().getBoundingRectangle();

        comprobarColisionBalaConJugador(hitboxJugador);
        comprobarColisionEnemigoConBala(hitboxBala);
    }

    public void comprobarColisionBalaConJugador(Rectangle hitboxJugador) {
        for (int i = 0; i < jefeFinal.getBalas().length; i++) {
            if (jefeFinal.getBalas()[i].getBoundingRectangle().overlaps(hitboxJugador) && !jugador.EsInvencible()) {
                reproducirSonidoRecibirDaño();
                jefeFinal.getBala().setPosition(-2000, 2000);
                jugador.restarVida();
                jugador.setEsInvencible(true);
                jugador.setTimer(2f);

                if (jugador.getVidas() == 0) {
                    jugador.setEstaVivo(false);
                }
            }
        }
    }
    public void comprobarColisionEnemigoConBala(Rectangle hitboxBala) {
        if (hitboxBala.overlaps(jefeFinal.getBoundingRectangle()) && jefeFinal.EstaVivo()) {
            reproducirSonidoExplosion();
            jefeFinal.setEstaVivo(false);
            jefeFinal.setSpeed(0);
            hud.añadirPuntaje();

            if (!jugador.isBalaMejoradaActiva()) {
                jugador.getBala().setPosition(-2000, 2000);
            }
        }
    }
    @Override
    public void cambiarPantalla(Screen pantalla) {
        PantallaJuegoAbstracta pantallaAnterior = (PantallaJuegoAbstracta) juego.getScreen();
        juego.setScreen(pantalla);
        if (pantallaAnterior != null) {
            pantallaAnterior.dispose();
        }
    }
    public void dibujarBoss() {
        if (jefeFinal.EstaVivo()) {
            jefeFinal.draw(Juego.BATCH);
            for (int i = 0; i < jefeFinal.getBalas().length; i++) {
                jefeFinal.getBalas()[i].draw(Juego.BATCH);
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

        pausar();

        restarPowerUpCooldown(delta);

        if (!jugador.isEstaVivo()) {
            cambiarPantalla(new PantallaGameOver(juego));
        }

        jefeFinal.update(delta);
        jugador.update(delta);

        matarEntidad();

        actualizarPowerUps(delta, jugador);

        Juego.BATCH.begin();

        Juego.BATCH.draw(fondo,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        generarPowerUps(generarRandom());

        if (powerUpEnPantalla != null) {
            powerUpEnPantalla.draw(Juego.BATCH);
        }
        animarChispa();

        dibujarJugador();
        dibujarBoss();

        Juego.BATCH.end();

        actualizarHud();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        dibujarHitbox();
        shapeRenderer.end();
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
