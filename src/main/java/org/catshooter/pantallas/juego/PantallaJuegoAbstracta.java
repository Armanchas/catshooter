package org.catshooter.pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.socket.client.Socket;
import org.catshooter.animacion.AnimacionDerecha;
import org.catshooter.animacion.AnimacionFrente;
import org.catshooter.animacion.AnimacionIzquierda;
import org.catshooter.controladores.GestorDeAudio;
import org.catshooter.efectos.Chispa;
import org.catshooter.efectos.Explosion;
import org.catshooter.entidades.enemigos.Alien;
import org.catshooter.entidades.enemigos.Ufo;
import org.catshooter.entidades.enemigos.Ufo2;
import org.catshooter.hud.Hud;
import org.catshooter.core.Juego;
import org.catshooter.entidades.enemigos.Enemigo;
import org.catshooter.entidades.Jugador;
import org.catshooter.pantallas.menu.PantallaPausa;
import org.catshooter.powerups.BalaMejorada;
import org.catshooter.powerups.PowerUp;
import org.catshooter.powerups.Velocidad;
import org.catshooter.powerups.VidaExtra;

public abstract class PantallaJuegoAbstracta implements Screen {
    protected Juego juego;
    protected Jugador jugador;
    protected Texture jugadorTextura;
    protected Texture aliadoTextura;
    protected Texture balaTextura;
    protected Texture enemigoTextura, enemigoTextura2, enemigoTextura3, finalBossTexture, proyectilJefe;
    protected Texture enemigoBalaTextura;
    protected Enemigo[] enemigos;
    protected int enemigosAncho = 4;
    protected int enemigosAlto = 2;
    protected GestorDeAudio gestorDeAudio;
    protected Hud hud;
    protected Texture fondo;
    protected Explosion[] explosiones;
    protected PowerUp[] powerUps;
    protected PowerUp powerUpEnPantalla;
    protected Texture vidaExtraTextura;
    protected Texture balaMejoradaTextura;
    protected Texture velocidadTextura;
    protected Chispa chispa;
    protected AnimacionFrente animacionFrente;
    protected AnimacionDerecha animacionDerecha;
    protected AnimacionIzquierda animacionIzquierda;
    protected float powerUpsCooldown;
    protected float enemigosCooldown;
    protected float aumento;
    protected ShapeRenderer shapeRenderer;

    public PantallaJuegoAbstracta(Juego juego) {
        this.juego = juego;
        fondo = new Texture("juego/fondo3.gif");

        hud = new Hud(Juego.BATCH);

        gestorDeAudio = new GestorDeAudio();

        vidaExtraTextura = new Texture("power-ups/vidaExtra.png");
        velocidadTextura = new Texture("power-ups/velocidad.png");
        balaMejoradaTextura = new Texture("power-ups/mejoraBala.png");

        animacionFrente = new AnimacionFrente();
        animacionDerecha = new AnimacionDerecha();
        animacionIzquierda = new AnimacionIzquierda();
        shapeRenderer = new ShapeRenderer();

        powerUpsCooldown = 1;
        enemigosCooldown = 0;

        chispa = new Chispa();

        enemigos = new Enemigo[enemigosAlto * enemigosAncho];
        powerUps = new PowerUp[3];
        explosiones = new Explosion[enemigos.length];

        cargarMusica();
        cargarSonidos();
        cargarTexturas();
        llenarEfectos();
        generarEnemigos();
        crearPowerUps();
    }

    public void cargarMusica() {
        gestorDeAudio.cargarMusica("audio/juego/boss.wav", "boss");
        gestorDeAudio.getMusica("boss").setVolume(0.14f);

        gestorDeAudio.cargarMusica("audio/juego/musicajuego.wav","gamemusic");
        gestorDeAudio.getMusica("gamemusic").setVolume(0.08f);
        gestorDeAudio.getMusica("gamemusic").setLooping(true);
    }

    public void cargarTexturas() {
        balaTextura = new Texture("entidades/bala.png");
        enemigoBalaTextura = new Texture("entidades/bala.png");
        jugadorTextura = new Texture("entidades/nave.png");
        aliadoTextura = new Texture("entidades/nave.png");
        enemigoTextura = new Texture("entidades/ufo.png");
        enemigoTextura2 = new Texture("entidades/alien.png");
        enemigoTextura3 = new Texture("entidades/ufo2.png");
        finalBossTexture = new Texture("entidades/finalBoss.png");
        proyectilJefe = new Texture("entidades/proyectilJefe.png");
    }

    public void cargarSonidos() {
        gestorDeAudio.cargarSonido("audio/efecto/nuevosEnemigos.wav", "enemigos");
        gestorDeAudio.cargarSonido("audio/efecto/explosion.wav", "explosion");
        gestorDeAudio.cargarSonido("audio/efecto/recibirDaño.wav", "recibirDaño");
    }

    public void reproducirSonidoEnemigos() {
        long id = gestorDeAudio.getSonido("enemigos").play();
        gestorDeAudio.getSonido("enemigos").setVolume(id, 0.07f);
    }

    public void reproducirSonidoExplosion() {
        long id = gestorDeAudio.getSonido("explosion").play();
        gestorDeAudio.getSonido("explosion").setVolume(id, 0.07f);
    }

    public void reproducirSonidoRecibirDaño() {
        long id = gestorDeAudio.getSonido("recibirDaño").play();
        gestorDeAudio.getSonido("recibirDaño").setVolume(id, 0.09f);
    }

    public abstract void cambiarPantalla(Screen screen);

    public void dibujarHitbox() {
        Rectangle hitbox = jugador.getBoundingRectangle();

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);

        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public void actualizarHud() {
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
            if (jugador.getDireccion() == 1) {
                animacionIzquierda.animar(Juego.BATCH, jugador.getX() - 16, jugador.getY() - 10);
            } else if (jugador.getDireccion() == 2) {
                animacionDerecha.animar(Juego.BATCH, jugador.getX() - 16, jugador.getY() - 10);
            } else {
                animacionFrente.animar(Juego.BATCH, jugador.getX() - 16, jugador.getY() - 10);
            }
        }
        if (!jugador.isBalaMejoradaActiva()) {
            jugador.getBala().draw(Juego.BATCH);
        }
    }

    public void generarEnemigos() {
        reproducirSonidoEnemigos();
        aumento += 0.4f;
        int i = 0;
        for (int y = 0; y < enemigosAlto; y++) {
            for (int x = 0; x < enemigosAncho; x++) {
                enemigos[i] = randomizarEnemigos(x, y);
                enemigos[i].setAumentoVelBala(aumento);
                i++;
            }
        }
    }

    public Enemigo randomizarEnemigos(int x, int y) {
        int random = generarRandom();
        return switch (random) {
            case 0 -> new Alien(new Vector2(x * 120, y * 120), enemigoTextura2, enemigoBalaTextura);
            case 1 -> new Ufo(new Vector2(x * 120, y * 120), enemigoTextura, enemigoBalaTextura);
            case 2 -> new Ufo2(new Vector2(x * 120, y * 120), enemigoTextura3, enemigoBalaTextura);
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
                enemigo.getBala().setPosition(-2000, 2000);
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
                explosiones[i].animar(batch, x, y);
            }
            i++;
        }
    }

    public void respawnearEnemigos() {
        if (enemigosMuertos() && enemigosCooldown <= 0) {
            enemigosCooldown = 5;
            hud.añadirOleada();
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

        float x = (float) (Math.random() * 680) + 120;
        float y = (float) (Math.random() * 200) + 100;

        if (powerUp.EstaEnPantalla()) {
            powerUpEnPantalla = powerUp;
        }
        if (powerUp.getCooldown() <= 0) {
            powerUp.setEstaEnPantalla(false);
            powerUp.setPosition(2000, 2000);
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
            chispa.animar(Juego.BATCH, jugador.getBala().getX() - 14, jugador.getBala().getY() - 45);
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
    public Jugador getJugador() {
        return jugador;
    }

    public GestorDeAudio getGestorDeAudio() {
        return gestorDeAudio;
    }
}
