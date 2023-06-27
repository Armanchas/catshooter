package org.catshooter.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import org.catshooter.entidades.Jugador;

public class BalaMejorada extends PowerUp {
    public BalaMejorada(Texture imagen) {
        super(imagen);
    }
    @Override
    public void aplicarHabilidad(float dt, Jugador jugador) {
        Rectangle jugadorHitbox = jugador.getBoundingRectangle();

        if (cooldown > 0 && estaEnPantalla) {
            restarCooldown(dt);
        }
        if (jugadorHitbox.overlaps(getBoundingRectangle())) {
            duracionHabilidad = 5;
            setEstaEnPantalla(false);
            setPosition(2000,2000);
            jugador.setBalaMejoradaActiva(true);
        }
        if (duracionHabilidad > 0) {
            restarTimer(dt);
        }
        if (duracionHabilidad <= 0) {
            jugador.setBalaMejoradaActiva(false);
        }
    }
}
