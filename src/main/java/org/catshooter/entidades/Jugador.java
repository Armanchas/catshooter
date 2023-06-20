package org.catshooter.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Jugador extends Sprite {
    private Sprite bala;
    private int speedBala;
    private int vidas;
    private boolean estaVivo;
    private boolean esInvencible;
    private float timer;
    public Jugador(Texture texture, Texture imgBala) {
        super(texture);
        setScale(1.4f);
        setPosition(400,100);

        estaVivo = true;
        vidas = 3;

        //bala
        bala = new Sprite(imgBala);
        bala.setScale(1.2f);
        this.speedBala = 20;
        bala.setPosition(-20,4000);
    }
    public void definirControles(float dt) {
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
    public void disparar() {
        bala.translate(0,speedBala);

        if (Gdx.input.isKeyPressed(Input.Keys.Z) && bala.getY()>=Gdx.graphics.getHeight()) {
            bala.setPosition(getX()+16,getY()+16);
        }
    }
    public void update(float dt) {
        actualizarInvencibilidad(dt);
        disparar();
        definirControles(dt);
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
    public void dibujarBala(SpriteBatch batch) {
        bala.setPosition(bala.getX(), bala.getY());
        bala.draw(batch);
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
