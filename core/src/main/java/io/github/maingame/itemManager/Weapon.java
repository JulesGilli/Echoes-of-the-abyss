package io.github.maingame.itemManager;

public class Weapon extends Gear{
    protected int attackIncrease;
    public Weapon(String name, int gold, int attackIncrease) {
        super(name,gold);
        this.attackIncrease = attackIncrease;
    }

    public int getAttackIncrease() {
        return attackIncrease;
    }
}
