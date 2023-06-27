package org.catshooter.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.catshooter.entidades.Jugador;

public abstract class PowerUp extends Sprite {
    protected float cooldown;
    protected float duracionHabilidad;
    protected boolean estaEnPantalla;
    public PowerUp(Texture imagen) {
        super(imagen);

        cooldown = 0;
        duracionHabilidad = 0;
    }
    public abstract void aplicarHabilidad(float dt, Jugador jugador);
    public void restarTimer(float dt) {
        duracionHabilidad -=dt;
    }
    public void restarCooldown(float dt) {
        cooldown-=dt;
    }
    public float getDuracionHabilidad() {
        return duracionHabilidad;
    }
    public void setDuracionHabilidad(float duracionHabilidad) {
        this.duracionHabilidad = duracionHabilidad;
    }
    public boolean EstaEnPantalla() {
        return estaEnPantalla;
    }
    public void setEstaEnPantalla(boolean estaEnPantalla) {
        this.estaEnPantalla = estaEnPantalla;
    }
    public float getCooldown() {
        return cooldown;
    }
    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }
}
