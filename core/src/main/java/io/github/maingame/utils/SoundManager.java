package io.github.maingame.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private AssetManager assetManager;
    private Map<String, Sound> sounds;
    private Map<String, Music> musicTracks;

    public SoundManager() {
        assetManager = new AssetManager();
        sounds = new HashMap<>();
        musicTracks = new HashMap<>();
    }

    // Charger un son
    public void loadSound(String name, String filePath) {
        assetManager.load(filePath, Sound.class);
        assetManager.finishLoading();
        sounds.put(name, assetManager.get(filePath, Sound.class));
    }

    // Charger une musique
    public void loadMusic(String name, String filePath) {
        assetManager.load(filePath, Music.class);
        assetManager.finishLoading();
        musicTracks.put(name, assetManager.get(filePath, Music.class));
    }

    private float globalVolume = 1.0f;

    public void setGlobalVolume(float volume) {
        this.globalVolume = volume;
        for (Music music : musicTracks.values()) {
            music.setVolume(volume);
        }
    }

    // Jouer une musique
    public void playMusic(String name, boolean loop, float volume) {
        if (musicTracks.containsKey(name)) {
            Music music = musicTracks.get(name);
            music.setLooping(loop);
            music.setVolume(volume);
            music.play();
        } else {
            System.out.println("Music not found: " + name);
        }
    }

    // Arrêter une musique
    public void stopMusic(String name) {
        if (musicTracks.containsKey(name)) {
            musicTracks.get(name).stop();
        }
    }


    // Libérer les ressources
    public void dispose() {
        assetManager.dispose();
    }
}
