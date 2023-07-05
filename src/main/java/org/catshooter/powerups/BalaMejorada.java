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

        gestionarTiempoEnPantalla(dt);

        gestionarDuracionHabilidad(dt);

        if (jugadorHitbox.overlaps(getBoundingRectangle())) {
            reproducirSonido();
            duracionHabilidad = 8;
            setEstaEnPantalla(false);
            setPosition(2000,2000);
            jugador.setBalaMejoradaActiva(true);
        }
        if (duracionHabilidad <= 0) {
            jugador.setBalaMejoradaActiva(false);
        }
    }
    @Override
    public void dispose() {
        gestorDeAudio.getSonido("powerUp").dispose();
    }
}
