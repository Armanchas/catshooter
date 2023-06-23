package org.catshooter.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Jugador extends Entidad {
    private int speedBala;
    private int vidas;
    private boolean esInvencible;
    private float timer;
    public Jugador(Texture imagen, Texture imgBala) {
        super(imagen,imgBala);
        setScale(1.4f);
        setPosition(400,100);

        vidas = 3;
        estaVivo = true;

        bala.setScale(1.2f);
        this.speedBala = 20;
        bala.setPosition(-20,4000);
    }
    @Override
    public void definirMovimiento(float dt) {
        float speed = 200*dt;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) &&  getX() >= 0) {
            setPosition(getX() + (-speed), getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && getX() <= Gdx.graphics.getWidth()) {
            setPosition(getX() + (speed), getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && getY() <= Gdx.graphics.getHeight()) {
            setPosition(getX(), getY()+ (speed));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && getY() >= 0) {
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
    }

    public void actualizarInvencibilidad(float dt) {
        if (esInvencible && timer < 0) {
            esInvencible = false;
        }
        if (timer > 0) {
            restarTimer(dt);
        }
    }
    public void restarTimer(float dt) {
        timer-=dt;
    }
    public void restarVida() {
        vidas--;
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
}
