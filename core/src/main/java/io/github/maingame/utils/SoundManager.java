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

    public void initialize() {
        loadMusic("menu", "Music/music_mainMenu.mp3");
        loadMusic("fight", "Music/music_fightMusic.mp3");

        loadSound("select", "Sound/sound_select.wav");
        loadSound("buy", "Sound/sound_gold.wav");
        loadSound("sound_playerScream1", "Sound/sound_playerScream1.wav");
        loadSound("sound_playerScream2", "Sound/sound_playerScream2.wav");
        loadSound("sound_playerScream3", "Sound/sound_playerScream3.wav");
        loadSound("sound_playerScream4", "Sound/sound_playerScream4.wav");
        loadSound("sound_hitSword1", "Sound/sound_hitSword1.wav");
        loadSound("sound_hitSword2", "Sound/sound_hitSword2.wav");
        loadSound("sound_hitSword3", "Sound/sound_hitSword3.wav");
        loadSound("sound_hitSword4", "Sound/sound_hitSword4.wav");
        loadSound("enemy_spawn", "Sound/sound_monsterScream.wav");
        loadSound("passWave", "Sound/sound_wavePassed.wav");
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

    public void setVolume(String name, float volume) {
            Music music = musicTracks.get(name);
            music.setVolume(volume);
    }


    // Jouer une musique
    public void playMusic(String name, boolean loop, float volume) {
        if (musicTracks.containsKey(name)) {
            Music music = musicTracks.get(name);
            music.setLooping(loop);
            music.setVolume(volume);
            music.play();
        } else {
            com.badlogic.gdx.Gdx.app.log("SoundManager", "Music not found: " + name);
        }
    }

    // Jouer un son
    public void playSound(String name) {
        if (sounds.containsKey(name)) {
            sounds.get(name).play();
        } else {
            com.badlogic.gdx.Gdx.app.log("SoundManager", "Sound not found: " + name);
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
