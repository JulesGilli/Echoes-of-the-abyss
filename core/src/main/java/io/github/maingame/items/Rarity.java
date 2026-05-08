package io.github.maingame.items;

import com.badlogic.gdx.graphics.Color;

public enum Rarity {
    COMMON("Common", new Color(0.8f, 0.8f, 0.8f, 1f), 1.0f),
    RARE("Rare", new Color(0.3f, 0.5f, 1f, 1f), 1.5f),
    LEGENDARY("Legendary", new Color(1f, 0.8f, 0.1f, 1f), 2.0f);

    private final String displayName;
    private final Color color;
    private final float statMultiplier;

    Rarity(String displayName, Color color, float statMultiplier) {
        this.displayName = displayName;
        this.color = color;
        this.statMultiplier = statMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Color getColor() {
        return new Color(color);
    }

    public float getStatMultiplier() {
        return statMultiplier;
    }
}
