package io.github.maingame.utilsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public class GameStat {
    private int golds;
    private int kills;
    private int killStreak;
    private int waves;
    private int floors;
    private int maxFloors;
    private int deaths;
    private boolean gameOver;

    public GameStat(){
        this.golds = 0;
        this.kills = 0;
        this.killStreak = 0;
        this.waves = 0;
        this.maxFloors = 0;
        this.deaths = 0;
        this.floors = 0;
        this.gameOver = false;
    }

    public void addGolds(int amount) {
        this.golds += amount;
    }

    public void removeGolds(int amount) {
        this.golds = Math.max(0, this.golds - amount);
    }


    public void saveGame() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        String saveData = json.toJson(this);
        Gdx.files.local("saveData.json").writeString(saveData, false);
    }

    public void loadGame() {
        if (Gdx.files.local("saveData.json").exists()) {
            Json json = new Json();
            GameStat loadedData = json.fromJson(GameStat.class, Gdx.files.local("saveData.json").readString());
            this.golds = loadedData.golds;
            this.kills = loadedData.kills;
            this.maxFloors = loadedData.maxFloors;
            this.killStreak = loadedData.killStreak;
            this.waves = loadedData.waves;
            this.deaths = loadedData.deaths;
            this.gameOver = loadedData.gameOver;
        }
    }

    public int getKillScore() {
        return kills;
    }

    public void setKillScore(int killScore) {
        this.kills = killScore;
    }

    public int getTotalGold() {
        return golds;
    }

    public void setTotalGold(int totalGold) {
        this.golds = totalGold;
    }

    public int getKillStreak() {
        return killStreak;
    }

    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameState(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
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

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addKills (int kills) {
        this.kills += kills;
    }

    public int getWaves() {
        return waves;
    }

    public void setWaves(int waves) {
        this.waves = waves;
    }
}
