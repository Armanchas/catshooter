package org.catshooter.pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.catshooter.core.Juego;
import org.catshooter.efectos.Explosion;
import org.catshooter.entidades.Enemigo;
import org.catshooter.entidades.Jugador;
import org.catshooter.pantallas.menu.PantallaGameOver;
import org.catshooter.powerups.*;
import org.lwjgl.opengl.GL20;

import java.util.HashMap;

public class PantallaJuego extends PantallaJuegoAbstracta {

    private final Enemigo[] enemigos;
    private final Texture enemigoTextura;
    private final Texture enemigoBalaTextura;
    private final Explosion[] explosiones;
    private final int enemigosAncho = 4;
    private final int enemigosAlto = 2;
    private final PowerUp[] powerUps;
    private final Texture vidaExtraTextura;
    private final Texture balaMejoradaTextura;
    private final Texture velocidadTextura;
    private float powerUpsCooldown;
    public PantallaJuego(Juego juego) {
        super(juego);
        enemigoTextura = new Texture("entidades/nave.png");
        enemigoBalaTextura = new Texture("entidades/bala.png");
        vidaExtraTextura = new Texture("power-ups/vidaExtra.png");
        velocidadTextura = new Texture("power-ups/velocidad.png");
        balaMejoradaTextura = new Texture("power-ups/balaMejorada.png");

        enemigos = new Enemigo[enemigosAlto*enemigosAncho];
        explosiones = new Explosion[enemigos.length];
        powerUps = new PowerUp[3];

        powerUpsCooldown = 1;

        llenarEfectos();
        generarEnemigos();
        crearPowerUps();
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

            actualizarHud();

            restarPowerUpCooldown(delta);


            if (!jugador.isEstaVivo()) {
                cambiarPantalla(new PantallaGameOver(juego));
            }

            actualizarEntidades(delta);

            actualizarPowerUps(delta,jugador);

            juego.getBatch().begin();

            matarEntidad(juego.getBatch());

            generarPowerUps((int) (Math.random() * 3));

            dibujarJugador();
            dibujarAliados();
            dibujarEnemigos();

            juego.getBatch().end();
        }
    }
    public void actualizarHud () {
        juego.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();

        hud.modificarVidas(jugador);
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
        for (int y = 0; y < enemigosAlto; y++) {
            for (int x = 0; x < enemigosAncho; x++) {
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
            if (enemigo.getBala().getBoundingRectangle().overlaps(hitboxJugador) && !jugador.EsInvencible()) {
                jugador.restarVida();
                jugador.setEsInvencible(true);
                jugador.setTimer(2f);
                if (jugador.getVidas() == 0) {
                    jugador.setEstaVivo(false);
                }
            }
            if (hitboxBala.overlaps(enemigo.getBoundingRectangle()) && enemigo.EstaVivo()) {
                enemigo.setEstaVivo(false);
                enemigo.setSpeed(0);
                hud.añadirPuntaje();

                if (!jugador.isBalaMejoradaActiva()) {
                    jugador.getBala().setPosition(-2000, 2000);
                }
            }
            if (!enemigo.EstaVivo()) {
                float x = enemigo.getX();
                float y = enemigo.getY();

                explosiones[i].setFrameActual(explosiones[i].getFrameActual() + Gdx.graphics.getDeltaTime());
                explosiones[i].animar(batch,x,y);
            }
            i++;
        }
    }
    public void llenarEfectos() {
        for (int i = 0; i < explosiones.length; i++) {
            explosiones[i] = new Explosion();
        }
    }
    public void crearPowerUps() {
        powerUps[0] = new VidaExtra(vidaExtraTextura);
        powerUps[1] = new Velocidad(velocidadTextura);
        powerUps[2] = new BalaMejorada(balaMejoradaTextura);
    }
    public void generarPowerUps(int random) {
        PowerUp powerUp = powerUps[random];

        float x = (float)(Math.random()*Gdx.graphics.getWidth());
        float y = (float)(Math.random()*Gdx.graphics.getHeight());

        if (powerUp.EstaEnPantalla()) {
            powerUp.draw(juego.getBatch());
        }
        if (powerUpsCooldown <= 0) {
            powerUpsCooldown = 15;
            powerUp.setEstaEnPantalla(true);
            powerUp.setPosition(x, y);
            powerUp.setCooldown(5);
        }
        if (powerUp.getCooldown() <= 0) {
            powerUp.setEstaEnPantalla(false);
            powerUp.setPosition(-2000,4000);
        }
    }
    public void actualizarPowerUps(float dt, Jugador jugador) {
        for (int i = 0; i < powerUps.length; i++) {
            powerUps[i].aplicarHabilidad(dt,jugador);
        }
    }
    public void restarPowerUpCooldown(float dt) {
        if (powerUpsCooldown >= 0) {
            powerUpsCooldown -= dt;
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
        vidaExtraTextura.dispose();
        velocidadTextura.dispose();
        balaMejoradaTextura.dispose();
    }
}
