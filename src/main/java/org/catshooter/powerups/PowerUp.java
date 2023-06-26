package org.catshooter.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.catshooter.entidades.Jugador;

public abstract class PowerUp extends Sprite {
    protected float timer;
    public PowerUp(Texture imagen) {
        super(imagen);

        timer = 0;
    }
    public abstract void definirHabilidad();
    public abstract void update(float dt, Jugador jugador);
    public void restarTimer(float dt) {
        timer-=dt;
    }
    public float getTimer() {
        return timer;
    }
    public void setTimer(float timer) {
        this.timer = timer;
    }
}
