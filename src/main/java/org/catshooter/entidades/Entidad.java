package org.catshooter.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entidad extends Sprite {
    protected Texture imgEntidad;
    protected Texture imgBala;
    protected Sprite bala;
    protected boolean estaVivo;
    public Entidad(Texture imgEntidad, Texture imgBala) {
        super(imgEntidad);

        bala = new Sprite(imgBala);
    }
    public abstract void definirMovimiento(float dt);
    public abstract void disparar();
    public abstract void update(float dt);
}
