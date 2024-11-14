package io.github.maingame.sceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Main;
import io.github.maingame.Platform;
import io.github.maingame.characterManager.Enemy;
import io.github.maingame.characterManager.Player;
import io.github.maingame.design2dManager.TextureManager;
import io.github.maingame.itemManager.Shop;
import io.github.maingame.utilsManager.GameStat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture background1, background2, background3, background4a, background4b;
    private final Player player;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Enemy> spawnList = new ArrayList<>();
    private int baseEnemyCount = 3;

    private float timeSinceLastSpawn = 0f;
    private float spawnDelay = 3.0f;
    private final Shop shop;
    private final GameStat stat;
    private final GameHUD hud;
    private OptionsScreen optionsScreen;

    private boolean isGameOver = false;
    private boolean isPaused = false;

    public GameScreen(Main game) {
        this.game = game;
        this.optionsScreen = new OptionsScreen(game);
        this.batch = game.batch;
        this.stat = new GameStat();
        this.shop = new Shop(new ArrayList<>(), stat);
        this.player = new Player(new Vector2(100, 100), Platform.getPlatforms(),
            optionsScreen.getLeftKey(), optionsScreen.getRightKey(),
            optionsScreen.getJumpKey(), optionsScreen.getAttackKey(), optionsScreen.getRollKey());

        this.hud = new GameHUD(game, stat);

        background1 = new Texture(Gdx.files.internal("background1.png"));
        background2 = new Texture(Gdx.files.internal("background2.png"));
        background3 = new Texture(Gdx.files.internal("background3.png"));
        background4a = new Texture(Gdx.files.internal("background4a.png"));
        background4b = new Texture(Gdx.files.internal("background4b.png"));

        updatePlayerKeys();
        Platform.createPlatforms();

        setupFloorEnemies();
    }

    private void setupFloorEnemies() {
        spawnList.clear();
        int enemyCount = baseEnemyCount + stat.getFloors();
        for (int i = 0; i < enemyCount; i++) {
            spawnList.add(new Enemy(new Vector2(-200, 100), Platform.getPlatforms(), player, stat));
        }

        spawnDelay = Math.max(1.0f, spawnDelay * 0.95f);
    }

    @Override
    public void render(float delta) {

        if (isPaused) {
            hud.renderPauseMenu(batch);
            return;
        }

        if (player.getHealth() <= 0) {
            isGameOver = true;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        drawBackground(screenWidth, screenHeight);
        drawPlatforms();
        player.render(batch);

        if (!isGameOver) {
            player.update(delta, enemies);
            spawnEnemies(delta);
        }
        hud.render(batch, player, screenWidth, screenHeight, isGameOver);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = true;
        }

        if (spawnList.isEmpty() && enemies.isEmpty()) {
            onPlayerReachNewFloor();
            setupFloorEnemies();
        }
    }

    public void updatePlayerKeys() {
        player.setLeftKey(optionsScreen.getLeftKey());
        player.setRightKey(optionsScreen.getRightKey());
        player.setJumpKey(optionsScreen.getJumpKey());
        player.setAttackKey(optionsScreen.getAttackKey());
    }


    private void drawBackground(float screenWidth, float screenHeight) {
        batch.draw(background1, 0, 0, screenWidth, screenHeight);
        batch.draw(background2, 0, 0, screenWidth, screenHeight);
        batch.draw(background3, 0, 0, screenWidth, screenHeight);
        batch.draw(background4a, 0, 0, screenWidth, screenHeight);
        batch.draw(background4b, 0, 0, screenWidth, screenHeight);
    }

    private void drawPlatforms() {
        for (Platform platform : Platform.getPlatforms()) {
            platform.render(batch);
        }
    }

    public void resumeGame() {
        isPaused = false;
        updatePlayerKeys();
    }

    private void spawnEnemies(float delta) {
        timeSinceLastSpawn += delta;

        if (!spawnList.isEmpty() && timeSinceLastSpawn >= spawnDelay) {
            Enemy enemyToSpawn = spawnList.remove(0);
            enemyToSpawn.getPosition().x = MathUtils.randomBoolean() ? -200 : Gdx.graphics.getWidth();
            enemies.add(enemyToSpawn);
            timeSinceLastSpawn = 0f;
        }

        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            enemy.render(batch);
            enemy.update(delta);

            if (enemy.isDeathAnimationFinished()) {
                player.setGold(player.getGold() + enemy.getGold());
                iterator.remove();
            }
        }
    }

    private void onPlayerReachNewFloor() {
        stat.setFloors(stat.getFloors() + 1);
        baseEnemyCount++;
    }

    private void resetGame() {
        player.reset();
        enemies.clear();
        timeSinceLastSpawn = 0f;
        isGameOver = false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        hud.dispose();
        background1.dispose();
        background2.dispose();
        background3.dispose();
        background4a.dispose();
        background4b.dispose();
        TextureManager.dispose();
    }
}
