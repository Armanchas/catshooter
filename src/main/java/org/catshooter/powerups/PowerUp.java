package org.catshooter.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class PowerUp extends Sprite {
    private Texture imagen;
    private float timer;

    public PowerUp(Texture imagen) {
        super(imagen);
    }
    public PowerUp() {
    }
    public abstract void definirHabilidad();
    public float getTimer() {
        return timer;
    }
    public void setTimer(float timer) {
        this.timer = timer;
    }
}
