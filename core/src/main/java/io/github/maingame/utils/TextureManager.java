package io.github.maingame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TextureManager {
    private static final HashMap<String, Texture> textures = new HashMap<>();
    private static Texture whitePixel;

    public static Texture getTexture(String filePath) {
        if (!textures.containsKey(filePath)) {
            textures.put(filePath, new Texture(Gdx.files.internal(filePath)));
        }
        return textures.get(filePath);
    }

    public static Texture getWhitePixel() {
        if (whitePixel == null) {
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            pixmap.fill();
            whitePixel = new Texture(pixmap);
            pixmap.dispose();
        }
        return whitePixel;
    }

    public static void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        textures.clear();
        if (whitePixel != null) {
            whitePixel.dispose();
            whitePixel = null;
        }
    }
}
