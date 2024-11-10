package io.github.maingame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.characterManager.Enemy;
import io.github.maingame.characterManager.Player;
import io.github.maingame.design2dManager.TextureManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture background1, background2, background3, background4a, background4b;
    private final Player player;
    private final List<Enemy> enemies = new ArrayList<>();
    private final float spawnDelay = 3.0f;
    private float timeSinceLastSpawn = 0f;

    private final Texture healthFrame;
    private final Texture healthBar;

    public GameScreen(Main game) {
        this.game = game;
        this.batch = game.batch;

        this.player = new Player(new Vector2(100, 100), Platform.getPlatforms());

        healthFrame = new Texture(Gdx.files.internal("Health_01.png"));
        healthBar = new Texture(Gdx.files.internal("Health_01_Bar01.png"));

        background1 = new Texture(Gdx.files.internal("background1.png"));
        background2 = new Texture(Gdx.files.internal("background2.png"));
        background3 = new Texture(Gdx.files.internal("background3.png"));
        background4a = new Texture(Gdx.files.internal("background4a.png"));
        background4b = new Texture(Gdx.files.internal("background4b.png"));

        Platform.createPlatforms();
    }

    @Override
    public void render(float delta) {
        timeSinceLastSpawn += delta;

        if (timeSinceLastSpawn >= spawnDelay) {
            spawnEnemy();
            timeSinceLastSpawn = 0f;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        batch.begin();

        batch.draw(background1, 0, 0, screenWidth, screenHeight);
        batch.draw(background2, 0, 0, screenWidth, screenHeight);
        batch.draw(background3, 0, 0, screenWidth, screenHeight);
        batch.draw(background4a, 0, 0, screenWidth, screenHeight);
        batch.draw(background4b, 0, 0, screenWidth, screenHeight);

        for (Platform platform : Platform.getPlatforms()) {
            platform.render(batch);
        }

        player.render(batch);
        player.update(delta, enemies);

        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            enemy.render(batch);
            enemy.update(delta);

            if (!enemy.isAlive()) {
                iterator.remove();
            }
        }

        float offset = 100;
        float sizeHealthBar = 4;
        batch.draw(healthFrame, offset, screenHeight - offset,healthFrame.getWidth() * sizeHealthBar,healthFrame.getHeight() * sizeHealthBar);

        float healthPercentage = player.getHealth() / (float) player.getMaxHealth();
        float healthBarWidth = healthBar.getWidth() * healthPercentage;
        batch.draw(healthBar, offset + 64, screenHeight - offset + 36, healthBarWidth * sizeHealthBar * 1.025f, healthBar.getHeight() * sizeHealthBar);

        batch.end();
    }

    private void spawnEnemy() {
        float spawnX = MathUtils.randomBoolean() ? -200 : Gdx.graphics.getWidth();

        Enemy newEnemy = new Enemy(new Vector2(spawnX, 100), Platform.getPlatforms(), player);
        enemies.add(newEnemy);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        healthFrame.dispose();
        healthBar.dispose();
        background1.dispose();
        background2.dispose();
        background3.dispose();
        background4a.dispose();
        background4b.dispose();
        TextureManager.dispose();
    }
}
