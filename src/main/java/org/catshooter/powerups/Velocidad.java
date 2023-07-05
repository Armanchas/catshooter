package org.catshooter.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import org.catshooter.entidades.Jugador;

public class Velocidad extends PowerUp {
    public Velocidad(Texture imagen) {
        super(imagen);
    }
    @Override
    public void aplicarHabilidad(float dt, Jugador jugador) {
        Rectangle jugadorHitbox = jugador.getBoundingRectangle();

        gestionarTiempoEnPantalla(dt);

        gestionarDuracionHabilidad(dt);

        if (jugadorHitbox.overlaps(getBoundingRectangle())) {
            reproducirSonido();
            duracionHabilidad = 5;
            setEstaEnPantalla(false);
            setPosition(2000,2000);
            jugador.setSpeed(400*dt);
        }
        if (duracionHabilidad <= 0) {
            jugador.setSpeed(200*dt);
        }
    }
    @Override
    public void dispose() {
        gestorDeAudio.getSonido("powerUp").dispose();
    }
}
