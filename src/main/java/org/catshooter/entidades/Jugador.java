package org.catshooter.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Jugador extends Sprite {
    private Sprite spriteBala;
    private int speedBala;
    private boolean estaVivo;
    public Jugador(Texture texture, Texture imgBala) {
        super(texture);
        setScale(1.4f);
        setPosition(400,100);

        estaVivo = true;
        spriteBala = new Sprite(imgBala);
        spriteBala.setScale(1.2f);
        this.speedBala = 20;
        spriteBala.setPosition(-20,4000);
    }
    public void definirControles(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) &&  getX() >= 0) {
            setPosition(getX() + (-200 * dt), getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && getX() <= Gdx.graphics.getWidth()) {
            setPosition(getX() + (200 * dt), getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && getY() <= Gdx.graphics.getHeight()) {
            setPosition(getX(), getY()+ (200 * dt));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && getY() >= 0) {
            setPosition(getX(), getY()+ (-200 * dt));
        }
    }
    public void disparar() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z) && spriteBala.getY()>=Gdx.graphics.getHeight()) {
            spriteBala.setPosition(getX()+16,getY()+16);
        }
    }
    public void update(float dt) {
        spriteBala.translate(0,speedBala);
        disparar();
        definirControles(dt);
    }
    public void dibujarBala(SpriteBatch batch) {
        spriteBala.setPosition(spriteBala.getX(), spriteBala.getY());
        spriteBala.draw(batch);
    }
    public Sprite getSpriteBala() {
        return spriteBala;
    }
    public boolean isEstaVivo() {
        return estaVivo;
    }
    public void setEstaVivo(boolean estaVivo) {
        this.estaVivo = estaVivo;
    }
}
