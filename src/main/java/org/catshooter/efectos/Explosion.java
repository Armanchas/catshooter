package org.catshooter.efectos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion extends Efecto {
    private TextureRegion[] regiones;
    public Explosion() {
        imagen = new Texture("efectos/explosion2.png");
        establecerFrames();

        animacion = new Animation<>(0.06f,regiones);
    }
    @Override
    public void establecerFrames() {
        TextureRegion[][] tmp = TextureRegion.split(imagen, imagen.getWidth()/4, imagen.getHeight()/4);
        regiones = new TextureRegion[4*4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                regiones[index++] = tmp[i][j];
            }
        }
    }
    @Override
    public void animar(SpriteBatch batch, float x, float y) {
        if (!efectoFinalizo()) {
            batch.draw(animacion.getKeyFrame(frameActual), x, y-10);
        }
    }
}
