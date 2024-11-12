package io.github.maingame.utilsManager;

public class GameStat {
    private int totalGold;
    private int killScore;
    private int killStreak;
    private int killWave;
    private int lvlFloor;
    private int deathNumber;
    private boolean gameOver;

    public GameStat(){
        this.totalGold = 0;
        this.killScore = 0;
        this.killStreak = 0;
        this.killWave = 0;
        this.lvlFloor = 0;
        this.deathNumber = 0;
        this.gameOver = false;
    }

    public int getKillScore() {
        return killScore;
    }

    public void setKillScore(int killScore) {
        this.killScore = killScore;
    }

    public int getTotalGold() {
        return totalGold;
    }

    public void setTotalGold(int totalGold) {
        this.totalGold = totalGold;
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
}
