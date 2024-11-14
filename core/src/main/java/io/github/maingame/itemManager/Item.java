package io.github.maingame.itemManager;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item {
    protected String name;
    protected int gold;
    protected Texture textureAvailable;
    protected Texture textureDisabled;

    public Item(String name, int value, String textureName, String textureDisabledName) {
        this.name = name;
        this.gold = value;
        this.textureAvailable = new Texture(textureName);
        this.textureDisabled = new Texture(textureDisabledName);
    }
}
