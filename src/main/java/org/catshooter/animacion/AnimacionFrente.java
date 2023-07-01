package org.catshooter.animacion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimacionFrente extends Animacion{
    public AnimacionFrente() {
        gatoSheet = new Texture("entidades/jugador/orange_2_front.png");
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
        batch.draw(animacionGato.getKeyFrame(stateTime,true), x, y,66,59);
    }
}
