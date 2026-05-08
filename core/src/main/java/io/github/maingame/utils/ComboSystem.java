package io.github.maingame.utils;

public class ComboSystem {
    private int comboCount = 0;
    private float comboTimer = 0f;
    private float comboWindow = 2.0f;
    private float displayTimer = 0f;

    public void registerHit() {
        if (comboTimer > 0 && comboTimer < comboWindow) {
            comboCount++;
        } else {
            comboCount = 1;
        }
        comboTimer = 0f;
        displayTimer = 2f;
    }

    public void update(float delta) {
        comboTimer += delta;
        if (comboTimer >= comboWindow) {
            comboCount = 0;
        }
        if (displayTimer > 0) {
            displayTimer -= delta;
        }
    }

    public float getDamageMultiplier() {
        if (comboCount <= 1) return 1f;
        return 1f + (comboCount - 1) * 0.1f;
    }

    public int getComboCount() {
        return comboCount;
    }

    public boolean shouldDisplay() {
        return comboCount >= 2 && displayTimer > 0;
    }

    public float getDisplayTimer() {
        return displayTimer;
    }
}
