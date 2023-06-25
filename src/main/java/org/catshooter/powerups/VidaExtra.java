package org.catshooter.powerups;

import com.badlogic.gdx.graphics.Texture;

public class VidaExtra extends PowerUp {
    public VidaExtra(Texture imagen) {
        super(imagen);
    }
    @Override
    public void definirHabilidad() {

    }
    @Override
    public void update(float dt) {
        if (timer > 0) {
            restarTimer(dt);
        }
    }
}
