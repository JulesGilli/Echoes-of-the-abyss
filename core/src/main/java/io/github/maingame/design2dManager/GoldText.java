package io.github.maingame.design2dManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

public class GoldText {
    private final Vector2 position;
    private final float duration = 1.0f;
    private float elapsedTime = 0f;
    private final BitmapFont font;

    public GoldText(String text, Vector2 position) {
        this.position = new Vector2(position.x, position.y + 50);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Jacquard12-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;

        this.font = generator.generateFont(parameter);
        Color MY_GOLD = new Color(255 / 255f, 204 / 255f, 101 / 255f, 1);

        this.font.setColor(MY_GOLD);
    }

    public void update(float delta) {
        elapsedTime += delta;
        position.y += delta * 40;
    }

    public void render(SpriteBatch batch) {
        font.draw(batch, "+10", position.x + 180, position.y + 180);
    }

    public boolean isExpired() {
        return elapsedTime >= duration;
    }

    public void dispose() {
        font.dispose();
    }
}
