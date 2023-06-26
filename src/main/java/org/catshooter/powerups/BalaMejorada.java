package org.catshooter.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import org.catshooter.entidades.Jugador;

public class BalaMejorada extends PowerUp {
    public BalaMejorada(Texture imagen) {
        super(imagen);
    }

    @Override
    public void definirHabilidad() {

    }
    @Override
    public void update(float dt, Jugador jugador) {
        Rectangle jugadorHitbox = jugador.getBoundingRectangle();

        if (jugadorHitbox.overlaps(getBoundingRectangle())) {
            timer = 10;
            setPosition(2000,2000);
            jugador.setBalaMejoradaActiva(true);
        }
        if (timer > 0) {
            restarTimer(dt);
        }
        if (timer <= 0) {
            jugador.setBalaMejoradaActiva(false);
        }
    }
}
