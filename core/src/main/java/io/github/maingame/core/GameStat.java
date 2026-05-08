package io.github.maingame.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameStat {
    private int golds;
    private int kills;
    private int killStreak;
    private int waves;
    private int floors;
    private int maxFloors;
    private int deaths;
    private boolean gameOver;
    private int speedPotionUse;
    private boolean isFirstGame = true;

    private static final String PREFS_NAME = "echoes_of_the_abyss_save";

    public GameStat() {
        this.golds = 0;
        this.kills = 0;
        this.waves = 0;
        this.maxFloors = 0;
        this.deaths = 0;
        this.speedPotionUse = 0;
        this.gameOver = false;
    }

    public void addGolds(int amount) {
        this.golds += amount;
    }

    public void removeGolds(int amount) {
        this.golds = Math.max(0, this.golds - amount);
    }

    public void saveGame() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        prefs.putInteger("golds", golds);
        prefs.putInteger("kills", kills);
        prefs.putInteger("waves", waves);
        prefs.putInteger("deaths", deaths);
        prefs.putInteger("maxFloors", maxFloors);
        prefs.putInteger("speedPotionUse", speedPotionUse);
        prefs.putBoolean("gameOver", gameOver);
        prefs.putBoolean("isFirstGame", isFirstGame);
        prefs.flush();
    }

    public void loadGame() {
        Preferences prefs = Gdx.app.getPreferences(PREFS_NAME);
        if (prefs.contains("golds")) {
            this.golds = prefs.getInteger("golds", 0);
            this.kills = prefs.getInteger("kills", 0);
            this.waves = prefs.getInteger("waves", 0);
            this.deaths = prefs.getInteger("deaths", 0);
            this.maxFloors = prefs.getInteger("maxFloors", 0);
            this.speedPotionUse = prefs.getInteger("speedPotionUse", 0);
            this.gameOver = prefs.getBoolean("gameOver", false);
            this.isFirstGame = prefs.getBoolean("isFirstGame", true);
        }
    }

    public boolean isFirstGame() {
        return isFirstGame;
    }

    public void setFirstGame(boolean firstGame) {
        this.isFirstGame = firstGame;
    }

    public int getSpeedPotionUse() {
        return speedPotionUse;
    }

    public void setSpeedPotionUse(int speedPotionUse) {
        this.speedPotionUse = speedPotionUse;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public int getGolds() {
        return golds;
    }

    public void setGolds(int golds) {
        this.golds = golds;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getWaves() {
        return waves;
    }


    public void setWaves(int waves) {
        this.waves = waves;
    }

    public int getMaxFloors() {
        return maxFloors;
    }

    public void setMaxFloors(int maxFloors) {
        this.maxFloors = maxFloors;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
