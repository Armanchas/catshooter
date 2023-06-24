package org.catshooter.efectos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion extends Efecto {
    public Explosion() {
        imagen = new Texture("efectos/explosion.png");
        regiones = new TextureRegion[18];
        establecerFrames();

        animacion = new Animation<>(0.07f,regiones);
    }
    @Override
    public void establecerFrames() {
        for (int i = 0; i < regiones.length; i++) {
            regiones[i] = new TextureRegion(imagen,48*i,0,48, 48);
        }
    }
    @Override
    public void animar(SpriteBatch batch, float x, float y) {
        if (!efectoTermino()) {
            batch.draw(animacion.getKeyFrame(frameActual), x, y);
        }
    }
}
