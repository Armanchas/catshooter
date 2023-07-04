package org.catshooter.pantallas.juego;

import org.catshooter.core.Juego;
import com.badlogic.gdx.Gdx.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PantallaJuegoTest {

    PantallaJuego pantallaJuego;

    @BeforeEach
    void setUp() {
        Juego juego = new Juego();
        pantallaJuego = new PantallaJuego(juego);
    }
    @AfterEach
    void tearDown() {
        pantallaJuego = null;
    }
    @Test
    void test() {

    }
}