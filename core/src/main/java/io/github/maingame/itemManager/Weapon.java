package io.github.maingame.itemManager;

public class Weapon extends Gear{
    protected int attackIncrease;
    public Weapon(String name, int gold, int attackIncrease, String textureName, String textureBuyName) {
        super(name,gold,textureName,textureBuyName);
        this.attackIncrease = attackIncrease;
    }

    public int getAttackIncrease() {
        return attackIncrease;
    }
}
