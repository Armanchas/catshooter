package org.catshooter.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class PowerUp extends Sprite {
    protected Texture imagen;
    protected float timer;
    protected boolean esVisible;
    public PowerUp(Texture imagen) {
        super(imagen);
    }
    public PowerUp() {
    }
    public abstract void definirHabilidad();
    public abstract void update(float dt);
    public void restarTimer(float dt) {
        timer-=dt;
    }
    public float getTimer() {
        return timer;
    }
    public void setTimer(float timer) {
        this.timer = timer;
    }
    public boolean EsVisible() {
        return esVisible;
    }
    public void setEsVisible(boolean esVisible) {
        this.esVisible = esVisible;
    }
}
