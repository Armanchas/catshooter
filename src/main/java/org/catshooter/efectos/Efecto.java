package org.catshooter.efectos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Efecto {
    protected Texture imagen;
    protected TextureRegion[] regiones;
    protected Animation<TextureRegion> animacion;
    protected float frameActual;
    public Efecto() {

    }
    public abstract void establecerFrames();
    public abstract void animar(SpriteBatch batch, float x, float y);
    public boolean efectoTermino() {
        if (frameActual > animacion.getAnimationDuration()) {
            return true;
        } else {
            return false;
        }
    }
    public void setFrameActual(float frameActual) {
        this.frameActual = frameActual;
    }
    public float getFrameActual() {
        return frameActual;
    }

}
