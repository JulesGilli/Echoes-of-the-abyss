package io.github.maingame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.maingame.utilsManager.lifeCycle;

public class Platform implements lifeCycle {
    private final Rectangle bounds;
    private final Texture texture;

    public Platform(float x, float y, float width, float height, Texture texture) {
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = texture;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public void update(float delta){}

}
