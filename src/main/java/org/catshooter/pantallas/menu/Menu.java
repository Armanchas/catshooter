package org.catshooter.pantallas.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Arrays;
import java.util.List;

public class Menu extends Actor {
    private List<Button> botones;
    private final Skin skin;
    private final Table table;
    private boolean sePresionoBoton1, sePresionoBoton2, sePresionoBoton3, sePresionoBoton4;
    public Menu(Stage stage, String boton1, String boton2, String boton3, String boton4) {
        skin = new Skin(Gdx.files.internal("menu/skin/vhs-ui.json"));
        table = new Table();

        establecerBotones(boton1,boton2,boton3,boton4);
        añadirBotones();
        añadirListeners();
        centrarTable();
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }
    public void establecerBotones(String nombreBoton1, String nombreBoton2, String nombreBoton3, String nombreBoton4) {
        botones = Arrays.asList(
                new TextButton(nombreBoton1,skin),
                new TextButton(nombreBoton2,skin),
                new TextButton(nombreBoton3,skin),
                new TextButton(nombreBoton4,skin)
        );
    }
    public void añadirBotones() {
        table.add(botones.get(0)).width(200).height(70).space(2).row();
        table.add(botones.get(1)).width(200).height(70).space(2).row();
        table.add(botones.get(2)).width(200).height(70).space(2).row();
        table.add(botones.get(3)).width(200).height(70).space(2).row();
    }
    public void añadirListeners() {
        botones.get(0).addListener((new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                setSePresionoBoton1(true);
            }
        }));
        botones.get(1).addListener((new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                setSePresionoBoton2(true);
            }
        }));
        botones.get(2).addListener((new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                setSePresionoBoton3(true);
            }
        }));
        botones.get(3).addListener((new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                setSePresionoBoton4(true);
            }
        }));
    }
    public void centrarTable() {
        table.setPosition((Gdx.graphics.getWidth() / 2f) - (table.getX() / 2)-20,(Gdx.graphics.getHeight() / 2f) - (table.getY() / 2));
    }
    public boolean SePresionoBoton1() {
        return sePresionoBoton1;
    }

    public void setSePresionoBoton1(boolean sePresionoBoton1) {
        this.sePresionoBoton1 = sePresionoBoton1;
    }

    public boolean SePresionoBoton2() {
        return sePresionoBoton2;
    }
    public void setSePresionoBoton2(boolean sePresionoBoton2) {
        this.sePresionoBoton2 = sePresionoBoton2;
    }

    public boolean SePresionoBoton3() {
        return sePresionoBoton3;
    }

    public void setSePresionoBoton3(boolean sePresionoBoton3) {
        this.sePresionoBoton3 = sePresionoBoton3;
    }

    public boolean SePresionoBoton4() {
        return sePresionoBoton4;
    }

    public void setSePresionoBoton4(boolean sePresionoBoton4) {
        this.sePresionoBoton4 = sePresionoBoton4;
    }
}
