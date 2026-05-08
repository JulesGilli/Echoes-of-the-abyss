package io.github.maingame.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class UIHelper {
    private static final float HOVER_SCALE = 1.05f;
    private static final Color HOVER_TINT = new Color(1.2f, 1.15f, 1.0f, 1f);
    private static final Color NORMAL_TINT = new Color(1f, 1f, 1f, 1f);

    public static boolean isHovered(Rectangle bounds, Vector2 mousePos) {
        return bounds.contains(mousePos);
    }

    public static void drawButton(SpriteBatch batch, Texture texture, Rectangle bounds, boolean hovered) {
        if (hovered) {
            float extraW = bounds.width * (HOVER_SCALE - 1f);
            float extraH = bounds.height * (HOVER_SCALE - 1f);
            batch.setColor(HOVER_TINT);
            batch.draw(texture,
                bounds.x - extraW / 2f,
                bounds.y - extraH / 2f,
                bounds.width + extraW,
                bounds.height + extraH);
            batch.setColor(NORMAL_TINT);
        } else {
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public static void drawFadeOverlay(SpriteBatch batch, float alpha, float width, float height) {
        if (alpha <= 0) return;
        Texture pixel = TextureManager.getWhitePixel();
        batch.setColor(0, 0, 0, alpha);
        batch.draw(pixel, 0, 0, width, height);
        batch.setColor(1, 1, 1, 1);
    }
}
