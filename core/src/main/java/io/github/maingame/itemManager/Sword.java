package io.github.maingame.itemManager;

public class Sword extends Gear{
    protected int attackIncrease;
    public Sword(String name, int gold, int attackIncrease) {
        super(name,gold);
        this.attackIncrease = attackIncrease;
    }
}
