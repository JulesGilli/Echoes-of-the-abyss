package io.github.maingame.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import io.github.maingame.scenes.MainMenuScreen;
import io.github.maingame.utils.SoundManager;

public class Main extends Game {
    /** Virtual resolution — all game logic uses these coordinates */
    public static final float VIRTUAL_WIDTH = 1920;
    public static final float VIRTUAL_HEIGHT = 1080;

    public SpriteBatch batch;
    public OrthographicCamera camera;
    public StretchViewport viewport;
    private SoundManager soundManager;

    @SuppressWarnings("deprecation")
    @Override
    public void create() {
        // Force plain VBO mode — required for WebGL 1.0 (GWT/HTML5)
        SpriteBatch.defaultVertexDataType = Mesh.VertexDataType.VertexBufferObject;
        batch = new SpriteBatch();
        soundManager = new SoundManager();
        soundManager.initialize();

        camera = new OrthographicCamera();
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply(true);

        this.setScreen(new MainMenuScreen(this));
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
            batch = null;
        }
        if (getScreen() != null) {
            getScreen().dispose();
            setScreen(null);
        }
        if (soundManager != null) {
            soundManager.dispose();
        }
    }
}
