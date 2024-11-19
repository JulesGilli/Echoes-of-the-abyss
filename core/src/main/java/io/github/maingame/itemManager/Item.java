package io.github.maingame.itemManager;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item implements ItemSolution {
    protected float increaseValue;
    protected String name;
    protected int gold;
    protected Texture textureAvailable;
    protected Texture textureDisabled;
    protected Texture textureLock;

    public Item(int value, String textureName, String textureDisabledName, String textureLockName) {
        this.gold = value;
        this.textureAvailable = new Texture(textureName);
        this.textureDisabled = new Texture(textureDisabledName);
        this.textureLock = new Texture(textureLockName);
    }

    public String getStrGold() {
        return Integer.toString(this.gold);
    }

    public int getGold() {
        return this.gold;
    }

    public Texture getTextureAvailable() {
        return textureAvailable;
    }

    public Texture getTextureDisabled() {
        return textureDisabled;
    }

    public Texture getTextureLock() {
        return textureLock;
    }

    public float getIncreaseValue() {
        return increaseValue;
    }

    public void setIncreaseValue(float increaseValue) {
        this.increaseValue = increaseValue;
    }
}
