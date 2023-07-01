package org.catshooter.pantallas.juego;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.catshooter.controladores.GestorDeAudio;
import org.catshooter.hud.Hud;
import org.catshooter.core.Juego;
import org.catshooter.entidades.Enemigo;
import org.catshooter.entidades.Jugador;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;

public abstract class PantallaJuegoAbstracta implements Screen {
    protected Juego juego;
    protected Jugador jugador;
    protected Texture jugadorTextura;
    protected Texture aliadoTextura;
    protected Texture balaTextura;
    protected Texture enemigoTextura;
    protected Texture enemigoBalaTextura;
    protected Enemigo[] enemigos;
    protected int enemigosAncho = 4;
    protected int enemigosAlto = 2;
    protected HashMap<String,Jugador> aliados;
    protected Socket socket;
    protected GestorDeAudio gestorDeAudio;
    protected Hud hud;
    public PantallaJuegoAbstracta(Juego juego) {
        this.juego = juego;
        balaTextura = new Texture("entidades/bala.png");
        enemigoBalaTextura = new Texture("entidades/bala.png");
        jugadorTextura = new Texture("entidades/nave.png");
        aliadoTextura = new Texture("entidades/nave.png");
        enemigoTextura = new Texture("entidades/ufo.png");
        aliados = new HashMap<>();

        hud = new Hud(Juego.BATCH);

        gestorDeAudio = new GestorDeAudio();
        cargarSonidos();

        conectarSocket();
        configurarEventos();
    }
    public void cargarSonidos() {
        gestorDeAudio.cargarSonido("audio/efecto/nuevosEnemigos.wav","enemigos");
        gestorDeAudio.cargarSonido("audio/efecto/explosion.wav","explosion");
        gestorDeAudio.cargarSonido("audio/efecto/recibirDaño.wav","recibirDaño");
    }
    public void reproducirSonidoEnemigos() {
        long id = gestorDeAudio.getSonido("enemigos").play();
        gestorDeAudio.getSonido("enemigos").setVolume(id,0.07f);
    }
    public void reproducirSonidoExplosion() {
        long id = gestorDeAudio.getSonido("explosion").play();
        gestorDeAudio.getSonido("explosion").setVolume(id,0.07f);
    }
    public void reproducirSonidoRecibirDaño() {
        long id = gestorDeAudio.getSonido("recibirDaño").play();
        gestorDeAudio.getSonido("recibirDaño").setVolume(id,0.09f);
    }
    @Override
    public void dispose() {
        jugadorTextura.dispose();
        balaTextura.dispose();
        aliadoTextura.dispose();
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
    public Socket getSocket() {
        return socket;
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

}
