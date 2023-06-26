package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Gdx;
import org.catshooter.core.Juego;
import org.catshooter.pantallas.juego.PantallaJuego;
import org.lwjgl.opengl.GL20;

public class PantallaMenu extends PantallaAbstracta {
    private Menu menu;
    public PantallaMenu(Juego juego) {
        super(juego);

        menu = new Menu(stage,"JUGAR","NIVELES","OPCIONES","SALIR");
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        acciones();

        stage.act();
        stage.draw();
    }
    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    private void acciones() {
        if (menu.SePresionoBoton1())
            juego.setScreen(new PantallaJuego(juego));
        if (menu.SePresionoBoton4())
            Gdx.app.exit();
    }
}
