package io.github.maingame.utilsManager;

public class GameStat {
    private int totalGold = 0;
    private int killScore = 0;
    private int killStreak = 0;
    private int killWave = 0;
    private int lvlFloor = 0;
    private int deathNumber = 0;

    public GameStat(){
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
}
