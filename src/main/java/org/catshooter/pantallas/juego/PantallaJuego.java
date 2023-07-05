package org.catshooter.pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.catshooter.core.Juego;
import org.catshooter.entidades.Jugador;
import org.catshooter.pantallas.menu.PantallaGameOver;
import org.lwjgl.opengl.GL20;

public class PantallaJuego extends PantallaJuegoAbstracta {
    private float timerIntro;
    private boolean musicaSonando;
    public PantallaJuego(Juego juego) {
        super(juego);

        timerIntro = 0;
        jugador = new Jugador(jugadorTextura,balaTextura);

    }
    @Override
    public void show() {
        gestorDeAudio.getMusica("gamemusic").play();

        if (musicaSonando) {
            gestorDeAudio.getMusica("boss").play();
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(jugador != null) {

            pausar();

            reproducirMusicaBoss();

            restarPowerUpCooldown(delta);

            restarEnemigosCooldown(delta);

            restarTimerIntro(delta);

            if (!jugador.isEstaVivo()) {
                cambiarPantalla(new PantallaGameOver(juego));
            }

            actualizarEntidades(delta);

            actualizarPowerUps(delta, jugador);

            Juego.BATCH.begin();

            Juego.BATCH.draw(fondo,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

            gestionarColisiones();

            gestionarPowerUps(generarRandom());

            if (powerUpEnPantalla != null) {
                powerUpEnPantalla.draw(Juego.BATCH);
            }
            animarChispa();

            dibujarJugador();
            dibujarEnemigos();

            respawnearEnemigos();

            Juego.BATCH.end();

            actualizarHud();

            //shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            //dibujarHitbox();
            //shapeRenderer.end();

            enviarABossFinal();
        }
    }
    @Override
    public void cambiarPantalla(Screen pantalla) {
        PantallaJuego pantallaAnterior = (PantallaJuego) juego.getScreen();
        juego.setScreen(pantalla);
        if (pantallaAnterior != null) {
            pantallaAnterior.dispose();
        }
    }
    public void enviarABossFinal() {
        if (hud.getOleadas() >= 2 && timerIntro <= 0 && musicaSonando) {
            juego.setScreen(new PantallaBossFinal(juego,jugador));
        }
    }
    public void reproducirMusicaBoss() {
        if (hud.getOleadas() >= 2 && !musicaSonando) {
            gestorDeAudio.getMusica("gamemusic").pause();
            gestorDeAudio.getMusica("boss").play();
            musicaSonando = true;
            timerIntro = 11f;
            enemigosCooldown = 100;
        }
    }
    public void restarTimerIntro(float delta) {
        if (timerIntro > 0 && hud.getOleadas() >= 2) {
            timerIntro-=delta;
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
        gestorDeAudio.getMusica("boss").pause();
        gestorDeAudio.getMusica("gamemusic").pause();
    }
    @Override
    public void dispose() {
        enemigoTextura.dispose();
        enemigoBalaTextura.dispose();
        vidaExtraTextura.dispose();
        velocidadTextura.dispose();
        balaMejoradaTextura.dispose();
        jugadorTextura.dispose();
        balaTextura.dispose();
        aliadoTextura.dispose();
        enemigoTextura2.dispose();
        enemigoTextura3.dispose();
        gestorDeAudio.getMusica("boss").dispose();
        gestorDeAudio.getMusica("gamemusic").dispose();
    }

}
