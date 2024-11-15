package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Entity;

public abstract class Gear extends Item implements GearManager{
    public Gear(int gold, String textureName, String textureBuyName) {
        super( gold, textureName, textureBuyName);
    }
}
