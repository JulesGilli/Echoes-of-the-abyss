package io.github.maingame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.maingame.sceneManager.MainMenuScreen;
import io.github.maingame.sceneManager.ShopScreen;
import io.github.maingame.utilsManager.GameStat;

public class Main extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        this.setScreen(new ShopScreen(this,new GameStat()));

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
