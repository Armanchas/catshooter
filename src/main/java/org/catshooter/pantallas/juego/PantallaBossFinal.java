package org.catshooter.pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.catshooter.core.Juego;
import org.catshooter.entidades.Jugador;
import org.catshooter.entidades.enemigos.Boss;
import org.catshooter.pantallas.menu.PantallaGameOver;
import org.catshooter.pantallas.menu.PantallaVictoria;
import org.lwjgl.opengl.GL20;

public class PantallaBossFinal extends PantallaJuegoAbstracta {
    private final Boss jefeFinal;
    private final boolean musicaSonando;
    public PantallaBossFinal(Juego juego, Jugador jugador) {
        super(juego);
        fondo = new Texture("juego/fondo2.png");

        this.jugador = jugador;
        jefeFinal = new Boss(new Vector2(0, 0), finalBossTexture, proyectilJefe);

        gestorDeAudio.getMusica("boss").play();
        gestorDeAudio.getMusica("boss").setPosition(11);

        musicaSonando = true;
    }

    public void gestionarColisiones() {
        Rectangle hitboxJugador = jugador.getBoundingRectangle();
        Rectangle hitboxBala = jugador.getBala().getBoundingRectangle();

        comprobarColisionBalaConJugador(hitboxJugador);
        comprobarColisionEnemigoConBala(hitboxBala);
    }
    public void comprobarColisionBalaConJugador(Rectangle hitboxJugador) {
        jefeFinal.getBalas().forEach(bala -> {
            if (bala.getBoundingRectangle().overlaps(hitboxJugador) && !jugador.EsInvencible()) {
                reproducirSonidoRecibirDaño();
                bala.setPosition(-2000, 2000);
                jugador.restarVida();
                jugador.setEsInvencible(true);
                jugador.setTimer(2f);

                if (jugador.getVidas() == 0) {
                    jugador.setEstaVivo(false);
                }
            }
        });
    }
    public void comprobarColisionEnemigoConBala(Rectangle hitboxBala) {
        if (hitboxBala.overlaps(jefeFinal.getBoundingRectangle()) && !jefeFinal.EsInvencible()) {
            reproducirSonidoExplosion();
            jefeFinal.restarVida();
            jefeFinal.setEsInvencible(true);
            jefeFinal.setTiempoInvencibilidad(1f);
            hud.añadirPuntaje();

            if (jefeFinal.getVidas() == 0) {
                cambiarPantalla(new PantallaVictoria(juego));
            }
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
            jefeFinal.getBalas().forEach(bala -> bala.draw(Juego.BATCH));
        }
    }
    @Override
    public void show() {
        if (musicaSonando) {
            gestorDeAudio.getMusica("boss").play();
        }
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

        gestionarColisiones();

        jefeFinal.restarTimer();

        actualizarPowerUps(delta, jugador);

        Juego.BATCH.begin();

        Juego.BATCH.draw(fondo,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        gestionarPowerUps(generarRandom());

        if (powerUpEnPantalla != null) {
            powerUpEnPantalla.draw(Juego.BATCH);
        }
        animarChispa();

        dibujarJugador();
        dibujarBoss();

        Juego.BATCH.end();

        actualizarHud();
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
        gestorDeAudio.getMusica("boss").pause();
    }

    @Override
    public void dispose() {
        finalBossTexture.dispose();
    }
}
