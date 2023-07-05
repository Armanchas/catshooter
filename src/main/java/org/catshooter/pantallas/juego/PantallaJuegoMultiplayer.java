package org.catshooter.pantallas.juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.catshooter.core.Juego;
import org.catshooter.entidades.Jugador;
import org.catshooter.pantallas.menu.PantallaGameOver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.opengl.GL20;

import java.net.URISyntaxException;
import java.util.HashMap;

public class PantallaJuegoMultiplayer extends PantallaJuegoAbstracta {
    private final HashMap<String,Jugador> aliados;
    private Socket socket;
    public PantallaJuegoMultiplayer(Juego juego) {
        super(juego);

        aliados = new HashMap<>();

        conectarSocket();
        configurarEventos();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        actualizarServidor();

        if(jugador != null) {

            pausar();

            restarPowerUpCooldown(delta);

            restarEnemigosCooldown(delta);

            if (!jugador.isEstaVivo()) {
                cambiarPantalla(new PantallaGameOver(juego));
            }

            actualizarEntidades(delta);

            actualizarPowerUps(delta, jugador);

            Juego.BATCH.begin();

            Juego.BATCH.draw(fondo,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

            gestionarColisiones();

            gestionarPowerUps(generarRandom());

            if (powerUpEnPantalla != null) {
                powerUpEnPantalla.draw(Juego.BATCH);
            }
            animarChispa();

            dibujarJugador();
            dibujarAliados();
            dibujarEnemigos();

            respawnearEnemigos();

            Juego.BATCH.end();

            actualizarHud();

            //shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            //dibujarHitbox();
            //shapeRenderer.end();
        }
    }
    @Override
    public void cambiarPantalla(Screen pantalla) {
        PantallaJuego pantallaAnterior = (PantallaJuego) juego.getScreen();
        juego.setScreen(pantalla);
        socket.close();
        if (pantallaAnterior != null) {
            pantallaAnterior.dispose();
        }
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
        enemigoTextura.dispose();
        enemigoBalaTextura.dispose();
        vidaExtraTextura.dispose();
        velocidadTextura.dispose();
        balaMejoradaTextura.dispose();
        jugadorTextura.dispose();
        balaTextura.dispose();
        aliadoTextura.dispose();
        enemigoTextura2.dispose();
        enemigoTextura3.dispose();
    }
    public void dibujarAliados() {
        for(HashMap.Entry<String, Jugador> entry : aliados.entrySet()){
            if (entry.getValue().isEstaVivo()) {
                if (entry.getValue().getDireccion() == 1){
                    animacionIzquierda.animar(Juego.BATCH, entry.getValue().getX()-16, entry.getValue().getY());
                }
                else if (entry.getValue().getDireccion() == 2){
                    animacionDerecha.animar(Juego.BATCH, entry.getValue().getX()-16, entry.getValue().getY());
                }else {
                    animacionFrente.animar(Juego.BATCH, entry.getValue().getX()-16, entry.getValue().getY());
                }
            }
            entry.getValue().getBala().draw(Juego.BATCH);
        }
    }
    public void actualizarServidor() {
        if (jugador!= null) {
            JSONObject data = new JSONObject();
            try {
                data.put("x",jugador.getX());
                data.put("y",jugador.getY());
                data.put("xBala",jugador.getBala().getX());
                data.put("yBala",jugador.getBala().getY());
                socket.emit("actualizarPosiciones",data);
            } catch (JSONException e) {
                System.out.println("Error!");
            }
        }
    }
    public void conectarSocket() {
        try {
            socket = IO.socket("http://localhost:3000");
            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public void configurarEventos() {
        socket.on(Socket.EVENT_CONNECT, objects -> jugador = new Jugador(jugadorTextura, balaTextura));

        socket.on("socketID", objects -> {
            JSONObject data = (JSONObject) objects[0];
            try {
                String id = data.getString("id");
                System.out.println("Mi ID: " + id);
            } catch (JSONException e) {
                System.out.println("Error");
            }
        });
        socket.on("nuevoJugador", objects -> {
            JSONObject data = (JSONObject) objects[0];
            try {
                String id = data.getString("id");
                System.out.println("Nuevo Jugador ID: " + id);
                aliados.put(id, new Jugador(aliadoTextura, balaTextura));
            } catch (JSONException e) {
                System.out.println("Error");
            }
        });
        socket.on("jugadorDesconectado", objects -> {
            JSONObject data = (JSONObject) objects[0];
            try {
                String id = data.getString("id");
                aliados.remove(id);
            } catch (JSONException e) {
                System.out.println("Error");
            }
        });
        socket.on("actualizarPosiciones", objects -> {
            JSONObject data = (JSONObject) objects[0];
            try {
                String id = data.getString("id");
                double x = data.getDouble("x");
                double y = data.getDouble("y");
                double xBala = data.getDouble("xBala");
                double yBala = data.getDouble("yBala");
                if (aliados.get(id) != null) {
                    aliados.get(id).setPosition((float) x, (float) y);
                    aliados.get(id).getBala().setPosition((float) xBala, (float) yBala);
                }
            } catch (JSONException e) {
                System.out.println("Error");
            }
        });
        socket.on("obtenerJugadores", objects -> {
            JSONArray objetos = (JSONArray) objects[0];
            try {
                for (int i = 0; i < objetos.length(); i++) {
                    Jugador aliado = new Jugador(aliadoTextura, balaTextura);
                    Vector2 posicion = new Vector2();
                    posicion.x =  ((Double) objetos.getJSONObject(i).getDouble("x")).floatValue();
                    posicion.y =  ((Double) objetos.getJSONObject(i).getDouble("y")).floatValue();
                    aliado.setPosition(posicion.x, posicion.y);
                    aliados.put(objetos.getJSONObject(i).getString("id"),aliado);
                }
            } catch (JSONException e) {
                System.out.println("Error");
            }
        });
    }
    public Socket getSocket() {
        return socket;
    }
}
