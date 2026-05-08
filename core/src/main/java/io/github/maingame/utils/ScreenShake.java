package io.github.maingame.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ScreenShake {
    private float duration;
    private float intensity;
    private float timer;
    private final Vector2 offset = new Vector2();

    public void trigger(float intensity, float duration) {
        this.intensity = Math.max(this.intensity, intensity);
        this.duration = Math.max(this.duration, duration);
        this.timer = 0f;
    }

    public void update(float delta) {
        if (duration <= 0) {
            offset.set(0, 0);
            return;
        }

        timer += delta;
        if (timer >= duration) {
            duration = 0;
            intensity = 0;
            offset.set(0, 0);
            return;
        }

        float progress = 1f - timer / duration;
        float currentIntensity = intensity * progress;
        offset.set(
            MathUtils.random(-currentIntensity, currentIntensity),
            MathUtils.random(-currentIntensity, currentIntensity)
        );
    }

    public Vector2 getOffset() {
        return offset;
    }

    public boolean isShaking() {
        return duration > 0;
    }
}
