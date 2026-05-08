package io.github.maingame.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class DamageText {
    private final String text;
    private final Vector2 position;
    private final float duration = 1.0f;
    private final BitmapFont font;
    private float elapsedTime = 0f;

    public DamageText(String text, Vector2 position) {
        this.text = text;
        this.position = new Vector2(position.x, position.y + 50);

        this.font = FontManager.getFont(64);
        this.font.setColor(Color.RED);
    }

    public void update(float delta) {
        elapsedTime += delta;
        position.y += delta * 40;
    }

    public void render(SpriteBatch batch) {
        font.draw(batch, text, position.x + 180, position.y + 140);
    }

    public boolean isExpired() {
        return elapsedTime >= duration;
    }

    public void dispose() {
        font.dispose();
    }
}
