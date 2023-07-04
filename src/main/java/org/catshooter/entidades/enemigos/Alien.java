package org.catshooter.entidades.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Alien extends Enemigo{
    public Alien(Vector2 posicion, Texture imagen, Texture imagenBala) {
        super(posicion, imagen, imagenBala);
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
}
