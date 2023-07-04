package org.catshooter.entidades.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Boss extends Enemigo {
    private Sprite[] balas;
    public Boss(Vector2 posicion, Texture imagen, Texture imagenBala) {
        super(posicion, imagen, imagenBala);
        setScale(2.4f);

        estaVivo = true;

        balas = new Sprite[6];
        llenarBalas(imagenBala);
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
            balas[i].translate(0, -40);

            if (balas[i].getY() < -400) {
                balas[i].setPosition(getX()+18, getY()-22);
            }
        }
    }
    public Sprite[] getBalas() {
        return balas;
    }
}
