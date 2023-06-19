package org.catshooter.pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.catshooter.core.Juego;
import org.catshooter.entidades.Jugador;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;

public abstract class PantallaAbstracta implements Screen {
    protected Juego juego;
    protected Jugador jugador;
    protected Texture jugadorTextura;
    protected Texture aliadoTextura;
    protected Texture imgBala;
    protected HashMap<String,Jugador> aliados;
    protected Socket socket;
    public PantallaAbstracta(Juego juego) {
        this.juego = juego;
        imgBala = new Texture("bala.png");
        jugadorTextura = new Texture("nave.png");
        aliadoTextura = new Texture("nave.png");
        aliados = new HashMap<>();

        conectarSocket();
        eventos();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

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
        jugador = null;
    }

    @Override
    public void dispose() {

    }
    public void cambiarPantalla(Screen pantalla) {
        PantallaJuego pantallaAnterior = (PantallaJuego) juego.getScreen();
        juego.setScreen(pantalla);
        socket.close();
        if (pantallaAnterior != null) {
            pantallaAnterior.dispose();
        }
    }
    public void actualizarServidor() {
        //Se almacenan las coordenadas del jugador y su disparo en una objeto JSON,
        //luego se envian los datos al servidor emitiendo el evento "actualizarPosiciones".

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

    public HashMap<String, Jugador> getAliados() {
        return aliados;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void conectarSocket() {
        try {
            socket = IO.socket("http://localhost:3000");
            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public void eventos() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                jugador = new Jugador(jugadorTextura,imgBala);
            }
        });
        socket.on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                JSONObject data = (JSONObject) objects[0];
                try {
                    String id = data.getString("id");
                    System.out.println("Mi ID: " + id);
                } catch (JSONException e) {
                    System.out.println("Error");
                }
            }
        });
        socket.on("nuevoJugador", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                JSONObject data = (JSONObject) objects[0];
                try {
                    String id = data.getString("id");
                    System.out.println("Nuevo Jugador ID: " + id);
                    aliados.put(id, new Jugador(aliadoTextura,imgBala));
                } catch (JSONException e) {
                    System.out.println("Error");
                }
            }
        });
        socket.on("jugadorDesconectado", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                JSONObject data = (JSONObject) objects[0];
                try {
                    String id = data.getString("id");
                    aliados.remove(id);
                } catch (JSONException e) {
                    System.out.println("Error");
                }
            }
        });
        socket.on("actualizarPosiciones", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                //se extraen las posiciones almacenadas en el objeto JSON para actualizarlas
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
            }
        });
        socket.on("obtenerJugadores", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                JSONArray objetos = (JSONArray) objects[0];
                try {
                    for (int i = 0; i < objetos.length(); i++) {
                        Jugador aliado = new Jugador(aliadoTextura,imgBala);
                        Vector2 posicion = new Vector2();
                        posicion.x =  ((Double) objetos.getJSONObject(i).getDouble("x")).floatValue();
                        posicion.y =  ((Double) objetos.getJSONObject(i).getDouble("y")).floatValue();
                        aliado.setPosition(posicion.x, posicion.y);
                        aliados.put(objetos.getJSONObject(i).getString("id"),aliado);
                    }
                } catch (JSONException e) {
                    System.out.println("Error");
                }
            }
        });
    }
}
