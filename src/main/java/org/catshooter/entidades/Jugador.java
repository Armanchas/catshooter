package org.catshooter.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.catshooter.core.Juego;
import org.catshooter.efectos.Chispa;

public class Jugador extends Entidad {
    private int speedBala;
    private int vidas;
    private float speed;
    private boolean esInvencible;
    private float timer;
    private boolean balaMejoradaActiva;
    public Jugador(Texture imagen, Texture imgBala) {
        super(imagen,imgBala);
        setScale(1.2f);
        setPosition(400,100);

        speed = 200*Gdx.graphics.getDeltaTime();
        vidas = 3;
        estaVivo = true;

        bala.setScale(1f);
        this.speedBala = 8;
        bala.setPosition(-20,4000);

        timer = 0;
    }
    @Override
    public void definirMovimiento(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) &&  getX() >= 4) {
            setPosition(getX() + (-speed), getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && getX() <= Gdx.graphics.getWidth()-52) {
            setPosition(getX() + (speed), getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && getY() <= Gdx.graphics.getHeight()-38) {
            setPosition(getX(), getY()+ (speed));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && getY() >= 4) {
            setPosition(getX(), getY()+ (-speed));
        }
    }
    @Override
    public void disparar() {
        bala.translate(0,speedBala);

        if (Gdx.input.isKeyPressed(Input.Keys.Z) && bala.getY()>=Gdx.graphics.getHeight()) {
            bala.setPosition(getX()+16,getY()+16);
        }
    }
    @Override
    public void update(float dt) {
        actualizarInvencibilidad(dt);
        disparar();
        definirMovimiento(dt);
        balaMejorada();
    }

    public void actualizarInvencibilidad(float dt) {
        if (esInvencible && timer < 0) {
            esInvencible = false;
        }
        if (timer > 0) {
            restarTimer(dt);
        }
    }
    public void balaMejorada() {
        if (balaMejoradaActiva) {
            speedBala = 14;
            bala.setScale(0);
        }
        else {
            speedBala = 8;
            bala.setScale(1f);
        }
    }
    public void restarTimer(float dt) {
        timer-=dt;
    }
    public void restarVida() {
        vidas--;
    }
    public void sumarVida() {
        vidas++;
    }
    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public boolean EsInvencible() {
        return esInvencible;
    }
    public void setEsInvencible(boolean esInvencible) {
        this.esInvencible = esInvencible;
    }
    public int getVidas() {
        return vidas;
    }
    public float getTimer() {
        return timer;
    }
    public void setTimer(float timer) {
        this.timer = timer;
    }
    public Sprite getBala() {
        return bala;
    }
    public boolean isEstaVivo() {
        return estaVivo;
    }
    public void setEstaVivo(boolean estaVivo) {
        this.estaVivo = estaVivo;
    }
    public boolean isBalaMejoradaActiva() {
        return balaMejoradaActiva;
    }
    public void setBalaMejoradaActiva(boolean balaMejoradaActiva) {
        this.balaMejoradaActiva = balaMejoradaActiva;
    }
}
