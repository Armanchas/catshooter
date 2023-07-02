package org.catshooter.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.catshooter.entidades.Jugador;

public class Hud {
    private final Stage stage;
    private final Viewport viewport;
    private int puntos,oleadas;
    private final int vidas;
    private Label puntosLabel, contadorVidasLabel, vidasLabel, puntajeLabel,oleadasLabel,contadorOleadasLabel;

    public Hud(SpriteBatch batch) {
        puntos = 0;
        oleadas = 1;
        vidas = 3;

        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        puntosLabel = new Label(String.format("%03d", puntos), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        vidasLabel = new Label(String.format("%01d",vidas), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        contadorOleadasLabel = new Label(String.format("%01d",oleadas), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        puntajeLabel = new Label("PUNTAJE", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        contadorVidasLabel = new Label("VIDAS", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        oleadasLabel = new Label("OLEADA", new Label.LabelStyle(new BitmapFont(),Color.WHITE));

        table.add(puntajeLabel).expandX().padTop(10);
        table.add(oleadasLabel).expandX().padTop(10);
        table.add(contadorVidasLabel).expandX().padTop(10);
        table.row();
        table.add(puntosLabel).expandX();
        table.add(contadorOleadasLabel).expandX();
        table.add(vidasLabel).expandX();

        stage.addActor(table);
    }
    public void añadirPuntaje() {
        puntosLabel.setText(puntos+=10*oleadas);
    }
    public void añadirOleada() {
        contadorOleadasLabel.setText(oleadas+=1);
    }
    public void modificarVidas(Jugador jugador) {
        vidasLabel.setText(jugador.getVidas());
    }
    public Stage getStage() {
        return stage;
    }
}
