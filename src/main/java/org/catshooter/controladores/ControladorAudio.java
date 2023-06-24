package org.catshooter.controladores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class ControladorAudio {
    private final HashMap<String,Music> musica;
    private final HashMap<String,Sound> sonido;
    public ControladorAudio() {
        musica = new HashMap<>();
        sonido = new HashMap<>();
    }
    public void cargarMusica(String ruta, String clave) {
        Music archivoMusica = Gdx.audio.newMusic(Gdx.files.internal(ruta));
        musica.put(clave, archivoMusica);
    }
    public void cargarSonido(String ruta, String clave) {
        Sound archivoSonido = Gdx.audio.newSound(Gdx.files.internal(ruta));
        sonido.put(clave, archivoSonido);
    }
    public Music getMusica(String clave) {
        return musica.get(clave);
    }
    public Sound getSonido(String clave) {
        return sonido.get(clave);
    }
}
