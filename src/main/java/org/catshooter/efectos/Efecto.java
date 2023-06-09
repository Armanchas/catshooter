package org.catshooter.efectos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.catshooter.animacion.Animable;

public abstract class Efecto implements Animable {
    protected Texture imagen;
    protected Animation<TextureRegion> animacion;
    protected float frameActual;
    public Efecto() {

    }
    public abstract void establecerFrames();
    public void animar(SpriteBatch batch, float x, float y) {

    }
    public boolean efectoFinalizo() {
        return frameActual > animacion.getAnimationDuration();
    }
    public void setFrameActual(float frameActual) {
        this.frameActual = frameActual;
    }
    public float getFrameActual() {
        return frameActual;
    }

}
