package io.github.maingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.maingame.GameScreen;

public class Main extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (getScreen() != null) {
            getScreen().dispose();
        }
    }
}
