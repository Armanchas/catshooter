package org.catshooter.animacion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimacionFrente {
    private static final int FRAME_COLS = 4, FRAME_ROWS = 2;
    private Animation<TextureRegion> animacionGato;
    private Texture gatoSheet;
    private TextureRegion[] gatoFrames;
    private float stateTime;
    public AnimacionFrente() {
        gatoSheet = new Texture("entidades/orange_2_front.png");
        establecerFrames();
        animacionGato = new Animation<TextureRegion>(0.025f, gatoFrames);
        stateTime = 0f;
    }
    public void establecerFrames() {
        TextureRegion[][] tmp = TextureRegion.split(gatoSheet, gatoSheet.getWidth()/FRAME_COLS, gatoSheet.getHeight()/FRAME_ROWS);
        gatoFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                gatoFrames[index++] = tmp[i][j];
            }
        }
    }
    public void animar(SpriteBatch batch, float x, float y) {
        batch.draw(animacionGato.getKeyFrame(stateTime,true), x, y,46,39);
    }
    public float getStateTime() {
        return stateTime;
    }
    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
}
