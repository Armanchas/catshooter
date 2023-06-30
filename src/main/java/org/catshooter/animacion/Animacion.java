package org.catshooter.animacion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animacion implements Animable{
    protected static final int FRAME_COLS = 4, FRAME_ROWS = 2;
    protected Animation<TextureRegion> animacionGato;
    protected Texture gatoSheet;
    protected TextureRegion[] gatoFrames;
    protected float stateTime;
    public Animacion() {

    }
    @Override
    public void animar(SpriteBatch batch, float x, float y) {

    }
    public float getStateTime() {
        return stateTime;
    }
    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
