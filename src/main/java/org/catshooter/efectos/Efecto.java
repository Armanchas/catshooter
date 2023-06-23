package org.catshooter.efectos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Efecto {
    private Texture imagen;
    private TextureRegion[] regiones;
    private Animation<TextureRegion> animacion;
    private float siguienteFrame;
    public Efecto() {
        imagen = new Texture("efectos/explosion.png");
        regiones = new TextureRegion[18];
        establecerFrames();

        animacion = new Animation<>(0.07f,regiones);
    }
    public void establecerFrames() {
        for (int i = 0; i < regiones.length; i++) {
            regiones[i] = new TextureRegion(imagen,48*i,0,48, 48);
        }
    }
    public void animar(SpriteBatch batch, float x, float y) {
        batch.draw(animacion.getKeyFrame(siguienteFrame,true),x,y);
        //System.out.println(siguienteFrame);
    }
    public void setSiguienteFrame(float siguienteFrame) {
        this.siguienteFrame = siguienteFrame;
    }
    public float getSiguienteFrame() {
        return siguienteFrame;
    }
}
