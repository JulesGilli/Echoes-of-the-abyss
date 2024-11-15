package io.github.maingame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.maingame.design2dManager.TextureManager;
import io.github.maingame.utilsManager.lifeCycle;

import java.util.ArrayList;
import java.util.List;

import io.github.maingame.design2dManager.TextureManager;

public class Platform implements lifeCycle {
    private final Rectangle bounds;
    private final Texture texture;

    public static final List<Platform> platforms = new ArrayList<>();


    public Platform(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = TextureManager.getTexture("platform.png");
    }

    public static void createPlatforms() {
        platforms.clear();
        platforms.add(new Platform(0, 0, 1500, 150));
        platforms.add(new Platform(1500, 0, 1500, 150));
    }

    public static List<Platform> getPlatforms() {
        return platforms;
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
