package org.catshooter.entidades.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import org.catshooter.entidades.Entidad;

public abstract class Enemigo extends Entidad {
    protected final Vector2 posicion;
    protected int speed;
    protected boolean estaVivo;
    protected float timer;
    protected float aumentoVelBala;
    public Enemigo(Vector2 posicion, Texture imagen, Texture imagenBala) {
        super(imagen,imagenBala);
        this.posicion = posicion;

        estaVivo = true;

        speed = 1;
        bala.setPosition(0,-20);
        bala.setScale(1f);
        setPosition(posicion.x+200, posicion.y+360);
    }
    @Override
    public void definirMovimiento(float dt) {
        translate(8*speed,0);

        if (getX() >= Gdx.graphics.getWidth()-getWidth()/2f) {
            speed = -1;
        }
        if (getX() <= 0-getWidth()/2f) {
            speed = 1;
        }
    }
    @Override
    public void disparar() {
        bala.translate(0, -3.6f-aumentoVelBala);

        if (bala.getY() < -400) {
                bala.setPosition(getX(), getY());
        }
    }
    @Override
    public void update(float dt) {
        definirMovimiento(dt);
        disparar();
    }
    public boolean balaEstaFueraDePantalla() {
        return bala.getY() < -20;
    }
    public void setAumentoVelBala(float aumentoVelBala) {
        this.aumentoVelBala = aumentoVelBala;
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
