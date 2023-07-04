package org.catshooter.pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.catshooter.core.Juego;
import org.catshooter.entidades.Jugador;
import org.catshooter.entidades.enemigos.Boss;
import org.catshooter.pantallas.menu.PantallaGameOver;
import org.lwjgl.opengl.GL20;

public class PantallaBossFinal extends PantallaJuegoAbstracta{
    private final Boss jefeFinal;
    public PantallaBossFinal(Juego juego) {
        super(juego);
        fondo = new Texture("juego/fondo2.png");

        jugador = new Jugador(jugadorTextura,balaTextura);
        jefeFinal = new Boss(new Vector2(0,0),enemigoTextura2, enemigoBalaTextura);
    }
    @Override
    public void cambiarPantalla(Screen screen) {

    }
    public void dibujarBoss() {
        jefeFinal.draw(Juego.BATCH);
        jefeFinal.getBala().draw(Juego.BATCH);
        if (jefeFinal.balaEstaFueraDePantalla()) {
            jefeFinal.getBala().setPosition(-2000,2000);
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
