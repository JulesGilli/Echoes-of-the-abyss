package io.github.maingame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;
import java.util.Map;

public class FontManager {
    private static final Map<Integer, BitmapFont> fonts = new HashMap<>();

    public static BitmapFont getFont(int size) {
        if (!fonts.containsKey(size)) {
            String path = "fonts/generated/jacquard_" + size + ".fnt";
            BitmapFont font = new BitmapFont(Gdx.files.internal(path));
            font.getData().markupEnabled = false;
            fonts.put(size, font);
        }
        return new BitmapFont(Gdx.files.internal("fonts/generated/jacquard_" + size + ".fnt"));
    }

    public static void dispose() {
        for (BitmapFont font : fonts.values()) {
            font.dispose();
        }
        fonts.clear();
    }
}
