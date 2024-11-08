package io.github.maingame.design2dManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
    public static Texture platformTexture;

    public static Texture getPlatformTexture() {
        if (platformTexture == null) {
            platformTexture = new Texture(Gdx.files.internal("platform.png"));
        }
        return platformTexture;
    }

    public static void dispose() {
        if (platformTexture != null) {
            platformTexture.dispose();
        }
    }
}
