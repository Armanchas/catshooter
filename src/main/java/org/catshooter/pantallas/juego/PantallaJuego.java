package org.catshooter.pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.catshooter.animacion.*;
import org.catshooter.core.Juego;
import org.catshooter.efectos.Chispa;
import org.catshooter.efectos.Explosion;
import org.catshooter.entidades.Enemigo;
import org.catshooter.entidades.Jugador;
import org.catshooter.pantallas.menu.PantallaGameOver;
import org.catshooter.pantallas.menu.PantallaPausa;
import org.catshooter.powerups.*;
import org.lwjgl.opengl.GL20;

import java.util.HashMap;

public class PantallaJuego extends PantallaJuegoAbstracta {
    private final Texture fondo;
    private final Explosion[] explosiones;
    private final PowerUp[] powerUps;
    private PowerUp powerUpEnPantalla;
    private final Texture vidaExtraTextura;
    private final Texture balaMejoradaTextura;
    private final Texture velocidadTextura;
    private final Chispa chispa;
    private final AnimacionFrente animacionFrente;
    private final AnimacionDerecha animacionDerecha;
    private final AnimacionIzquierda animacionIzquierda;
    private float powerUpsCooldown;
    private float enemigosCooldown;
    private float aumento;
    public PantallaJuego(Juego juego) {
        super(juego);
        fondo = new Texture("juego/fondo3.gif");

        vidaExtraTextura = new Texture("power-ups/vidaExtra.png");
        velocidadTextura = new Texture("power-ups/velocidad.png");
        balaMejoradaTextura = new Texture("power-ups/mejoraBala.png");

        animacionFrente = new AnimacionFrente();
        animacionDerecha = new AnimacionDerecha();
        animacionIzquierda = new AnimacionIzquierda();

        powerUpsCooldown = 1;
        enemigosCooldown = 0;

        chispa = new Chispa();

        enemigos = new Enemigo[enemigosAlto * enemigosAncho];
        powerUps = new PowerUp[3];
        explosiones = new Explosion[enemigos.length];

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

        if(jugador != null) {

            pausar();

            restarPowerUpCooldown(delta);

            restarEnemigosCooldown(delta);

            if (!jugador.isEstaVivo()) {
                cambiarPantalla(new PantallaGameOver(juego));
            }

            actualizarEntidades(delta);

            actualizarPowerUps(delta, jugador);

            Juego.BATCH.begin();

            Juego.BATCH.draw(fondo,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

            matarEntidad(Juego.BATCH);

            generarPowerUps(generarRandom());

            if (powerUpEnPantalla != null) {
                powerUpEnPantalla.draw(Juego.BATCH);
            }
            animarChispa();

            dibujarJugador();
            dibujarAliados();
            dibujarEnemigos();

            respawnearEnemigos();

            Juego.BATCH.end();

            actualizarHud();
        }
    }
    public void actualizarHud () {
        Juego.BATCH.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();

        hud.modificarVidas(jugador);
    }
    public void actualizarEntidades(float delta) {
        jugador.update(delta);

        for (Enemigo enemigo : enemigos) {
            enemigo.update(delta);
        }
    }
    public void dibujarJugador() {
        animacionFrente.setStateTime(animacionFrente.getStateTime() + Gdx.graphics.getDeltaTime());
        animacionDerecha.setStateTime(animacionDerecha.getStateTime() + Gdx.graphics.getDeltaTime());
        animacionIzquierda.setStateTime(animacionIzquierda.getStateTime() + Gdx.graphics.getDeltaTime());

        if (jugador.isEstaVivo()) {
            if (jugador.getDireccion() == 1){
                animacionIzquierda.animar(Juego.BATCH, jugador.getX()-16, jugador.getY());
            }
            else if (jugador.getDireccion() == 2){
                animacionDerecha.animar(Juego.BATCH, jugador.getX()-16, jugador.getY());
            }else {
                animacionFrente.animar(Juego.BATCH, jugador.getX()-16, jugador.getY());
            }
        }
        if (!jugador.isBalaMejoradaActiva()) {
            jugador.getBala().draw(Juego.BATCH);
        }
    }
    public void dibujarAliados() {
        for(HashMap.Entry<String, Jugador> entry : aliados.entrySet()){
            if (entry.getValue().isEstaVivo()) {
                if (entry.getValue().getDireccion() == 1){
                    animacionIzquierda.animar(Juego.BATCH, entry.getValue().getX()-16, entry.getValue().getY());
                }
                else if (entry.getValue().getDireccion() == 2){
                    animacionDerecha.animar(Juego.BATCH, entry.getValue().getX()-16, entry.getValue().getY());
                }else {
                    animacionFrente.animar(Juego.BATCH, entry.getValue().getX()-16, entry.getValue().getY());
                }
            }
            entry.getValue().getBala().draw(Juego.BATCH);
        }
    }
    public void generarEnemigos() {
        reproducirSonidoEnemigos();
        aumento+=0.4f;
        int i = 0;
        for (int y = 0; y < enemigosAlto; y++) {
            for (int x = 0; x < enemigosAncho; x++) {
                enemigos[i] = new Enemigo(new Vector2(x*120,y*120), randomizarEnemigos(), enemigoBalaTextura);
                enemigos[i].setAumentoVelBala(aumento);
                i++;
            }
        }
    }
    public Texture randomizarEnemigos() {
        int random = generarRandom();
        return switch (random) {
            case 0 -> enemigoTextura;
            case 1 -> enemigoTextura2;
            case 2 -> enemigoTextura3;
            default -> null;
        };
    }
    public void dibujarEnemigos() {
        for (Enemigo enemigo : enemigos) {
            enemigo.getBala().draw(Juego.BATCH);
            if (enemigo.EstaVivo()) {
                enemigo.draw(Juego.BATCH);
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
                reproducirSonidoRecibirDaño();
                enemigo.getBala().setPosition(-2000, 2000);
                jugador.restarVida();
                jugador.setEsInvencible(true);
                jugador.setTimer(2f);

                if (jugador.getVidas() == 0) {
                    jugador.setEstaVivo(false);
                }
            }
            if (hitboxBala.overlaps(enemigo.getBoundingRectangle()) && enemigo.EstaVivo()) {
                reproducirSonidoExplosion();
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
    public void respawnearEnemigos() {
        if (enemigosMuertos() && enemigosCooldown <= 0) {
            enemigosCooldown = 5;
        }
        if (enemigosMuertos() && enemigosCooldown <= 1) {
            generarEnemigos();
            llenarEfectos();
        }
    }
    public boolean enemigosMuertos() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.EstaVivo()) {
                return false;
            }
        }
        return true;
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

        float x = (float)(Math.random()*680)+120;
        float y = (float)(Math.random()*200)+100;

        if (powerUp.EstaEnPantalla()) {
            powerUpEnPantalla = powerUp;
        }
        if (powerUp.getCooldown() <= 0) {
            powerUp.setEstaEnPantalla(false);
            powerUp.setPosition(2000,2000);
        }
        if (powerUpsCooldown <= 0) {
            powerUp.setPosition(x, y);
            powerUp.setEstaEnPantalla(true);
            powerUpsCooldown = 15;
            powerUp.setCooldown(5);
        }

    }
    public void actualizarPowerUps(float dt, Jugador jugador) {
        for (PowerUp powerUp : powerUps) {
            powerUp.aplicarHabilidad(dt, jugador);
        }
    }
    public void restarPowerUpCooldown(float dt) {
        if (powerUpsCooldown >= 0) {
            powerUpsCooldown -= dt;
        }
    }
    public void restarEnemigosCooldown(float dt) {
        if (enemigosCooldown >= 0) {
            enemigosCooldown -= dt;
        }
    }
    public void animarChispa() {
        if (jugador.isBalaMejoradaActiva()) {
            chispa.setFrameActual(chispa.getFrameActual() + Gdx.graphics.getDeltaTime());
            chispa.animar(Juego.BATCH, jugador.getBala().getX()-14, jugador.getBala().getY()-45);
        }
    }
    public void pausar() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            juego.setScreen(new PantallaPausa(juego));
        }
    }
    public int generarRandom() {
        return (int) (Math.random() * 3);
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
        jugadorTextura.dispose();
        balaTextura.dispose();
        aliadoTextura.dispose();
        enemigoTextura2.dispose();
        enemigoTextura3.dispose();
    }
}
