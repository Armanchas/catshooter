package org.catshooter.efectos;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Chispa extends Efecto{
    private TextureAtlas textureAtlas;
    public Chispa() {
        establecerFrames();
        animacion = new Animation<>(0.09f,textureAtlas.getRegions());
    }
    @Override
    public void establecerFrames() {
        textureAtlas = new TextureAtlas("efectos/superDisparo.atlas");
    }
    @Override
    public void animar(SpriteBatch batch, float x, float y) {
        batch.draw(animacion.getKeyFrame(frameActual,true), x, y);
    }
}
