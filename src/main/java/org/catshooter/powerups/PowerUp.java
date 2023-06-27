package org.catshooter.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;
import org.catshooter.controladores.GestorDeAudio;
import org.catshooter.entidades.Jugador;

public abstract class PowerUp extends Sprite implements Disposable {
    protected float cooldown;
    protected float duracionHabilidad;
    protected boolean estaEnPantalla;
    protected GestorDeAudio gestorDeAudio;
    protected long id;
    public PowerUp(Texture imagen) {
        super(imagen);

        setPosition(2000,2000);

        gestorDeAudio = new GestorDeAudio();
        cargarSonido();

        cooldown = 0;
        duracionHabilidad = 0;
    }
    public void cargarSonido() {
        gestorDeAudio.cargarSonido("audio/tomarPowerUp.wav","powerUp");
    }
    public void reproducirSonido() {
        long id = gestorDeAudio.getSonido("powerUp").play();
        gestorDeAudio.getSonido("powerUp").setVolume(id,0.04f);
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
