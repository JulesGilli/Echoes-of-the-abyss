package io.github.maingame.itemManager;

public abstract class Gear extends Item {
    public Gear(int gold, String textureName, String textureBuyName , String textureLockName) {
        super( gold, textureName, textureBuyName, textureLockName);
    }
}
