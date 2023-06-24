package org.catshooter.efectos;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GatoIzquierda implements ApplicationListener {

    private static final int FRAME_COLS = 4, FRAME_ROWS = 2;
    Animation<TextureRegion> animacionGato;
    Texture gatoSheet;
    SpriteBatch spriteBatch;

    float stateTime;

    @Override
    public void create() {
        gatoSheet = new Texture("entidades/orange_2_left.png");
        TextureRegion[][] tmp = TextureRegion.split(gatoSheet, gatoSheet.getWidth()/FRAME_COLS, gatoSheet.getHeight()/FRAME_ROWS);
        TextureRegion[] gatoFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                gatoFrames[index++] = tmp[i][j];
            }
        }

        animacionGato = new Animation<TextureRegion>(0.025f, gatoFrames);
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        stateTime += 0.025f;
        TextureRegion currentFrame = animacionGato.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, 50, 50);
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        gatoSheet.dispose();
    }
}