package org.catshooter.core;

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

public class Hud {
    private final Stage stage;
    private Viewport viewport;
    private int puntos, vidas;
    private Label puntosLabel, contadorVidasLabel, vidasLabel, puntajeLabel;

    public Hud(SpriteBatch batch) {
        puntos = 0;
        vidas = 3;

        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        puntosLabel = new Label(String.format("%03d", puntos), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        vidasLabel = new Label(String.format("%01d",vidas), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        puntajeLabel = new Label("PUNTAJE", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        contadorVidasLabel = new Label("VIDAS", new Label.LabelStyle(new BitmapFont(),Color.WHITE));

        table.add(puntajeLabel).expandX().padTop(10);
        table.add(contadorVidasLabel).expandX().padTop(10);
        table.row();
        table.add(puntosLabel).expandX();
        table.add(vidasLabel).expandX();

        stage.addActor(table);
    }
    public void añadirPuntaje() {
        puntosLabel.setText(puntos+=10);
    }
    public void restarVida() {
        vidasLabel.setText(vidas-=1);
    }
    public void sumarVida() {
        vidasLabel.setText(vidas+=1);
    }
    public Stage getStage() {
        return stage;
    }
}
