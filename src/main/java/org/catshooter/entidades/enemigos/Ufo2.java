package org.catshooter.entidades.enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Ufo2 extends Enemigo{
    public Ufo2(Vector2 posicion, Texture imagen, Texture imagenBala) {
        super(posicion, imagen, imagenBala);
    }
    @Override
    public void disparar() {
        bala.translate(0, -4.4f-aumentoVelBala);

        if (bala.getY() < -400) {
            bala.setPosition(getX(), getY());
        }
    }
}
