package org.catshooter.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemigo extends Entidad{
    private Vector2 posicion;
    private int speed;
    private boolean estaVivo;
    private float timer;
    public Enemigo(Vector2 posicion, Texture imagen, Texture imagenBala) {
        super(imagen,imagenBala);
        this.posicion = posicion;

        estaVivo = true;

        speed = 1;
        bala.setPosition(0,-20);
        bala.setScale(1f);
        setPosition(posicion.x+200, posicion.y+400);
    }
    @Override
    public void definirMovimiento(float dt) {
        mover();
        rebotar();
    }
    @Override
    public void disparar() {
        bala.translate(0, -4);

        if (bala.getY() < -400) {
                bala.setPosition(getX(), getY());
        }
    }
    @Override
    public void update(float dt) {
        definirMovimiento(dt);
        disparar();
    }
    public void mover() {
        translate(8*speed,0);
    }
    public void rebotar() {
        if (getX() >= Gdx.graphics.getWidth()-getWidth()/2f) {
            setSpeed(-1);
        }
        if (getX() <= 0-getWidth()/2f) {
            setSpeed(1);
        }
    }
    public boolean balaEstaFueraDePantalla() {
        if (bala.getY() < -20) {
            return true;
        } else {
            return false;
        }
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public boolean EstaVivo() {
        return estaVivo;
    }
    public void setEstaVivo(boolean estaVivo) {
        this.estaVivo = estaVivo;
    }
    public Sprite getBala() {
        return bala;
    }
    public float getTimer() {
        return timer;
    }
    public void setTimer(float timer) {
        this.timer = timer;
    }
}
