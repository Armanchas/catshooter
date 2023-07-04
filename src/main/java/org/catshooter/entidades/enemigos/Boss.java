package org.catshooter.entidades.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Boss extends Enemigo {
    private Sprite[] balas;
    private int vidas;
    private boolean esInvencible;
    private float tiempoInvencibilidad;
    public Boss(Vector2 posicion, Texture imagen, Texture imagenBala) {
        super(posicion, imagen, imagenBala);
        setScale(2.4f);

        tiempoInvencibilidad = 0;

        vidas = 20;

        balas = new Sprite[6];
        llenarBalas(imagenBala);
    }
    public void restarTimer() {
        if (tiempoInvencibilidad > 0) {
            tiempoInvencibilidad-=Gdx.graphics.getDeltaTime();
        }
        if (tiempoInvencibilidad <= 0) {
            esInvencible = false;
        }
    }
    public void llenarBalas(Texture imagenBala) {
        for (int i = 0; i < balas.length; i++) {
            balas[i] = new Sprite(imagenBala);
        }
    }
    @Override
    public void definirMovimiento(float dt) {
        translate(10*speed,0);

        if (getX() >= Gdx.graphics.getWidth()-getWidth()/2f) {
            speed = -1;
        }
        if (getX() <= 0-getWidth()/2f) {
            speed = 1;
        }
    }
    @Override
    public void disparar() {
        for (int i = 0; i < balas.length; i++) {
            balas[i].translate(0, -25);

            if (balas[i].getY() < -400) {
                balas[i].setPosition(getX()+18, getY()-22);
            }
        }
    }
    public void restarVida() {
        vidas--;
    }
    public Sprite[] getBalas() {
        return balas;
    }
    public int getVidas() {
        return vidas;
    }
    public boolean EsInvencible() {
        return esInvencible;
    }
    public void setEsInvencible(boolean esInvencible) {
        this.esInvencible = esInvencible;
    }
    public void setTiempoInvencibilidad(float tiempoInvencibilidad) {
        this.tiempoInvencibilidad = tiempoInvencibilidad;
    }
}
