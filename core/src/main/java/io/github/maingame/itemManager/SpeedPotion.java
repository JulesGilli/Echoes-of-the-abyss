package io.github.maingame.itemManager;

public class SpeedPotion extends Consumable {
    private float SpeedIncrease;

    public SpeedPotion(int speedIncrease){
        super("SpeedPotion",50, speedIncrease );
    }

    public float getSpeedIncrease() {
        return SpeedIncrease;
    }
}
