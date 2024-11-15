package io.github.maingame.itemManager;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item implements ItemSolution{
    protected String name;
    protected int gold;
    protected Texture textureAvailable;
    protected Texture textureDisabled;

    public Item(int value, String textureName, String textureDisabledName) {
        this.gold = value;
        this.textureAvailable = new Texture(textureName);
        this.textureDisabled = new Texture(textureDisabledName);
    }

    public String getStrGold() {
        return Integer.toString(this.gold);
    }

    public int getGold(){
        return this.gold;
    }

    public Texture getTextureAvailable() {
        return textureAvailable;
    }

    public Texture getTextureDisabled() {
        return textureDisabled;
    }
}
