package io.github.maingame.sceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Main;
import io.github.maingame.Platform;
import io.github.maingame.characterManager.*;
import io.github.maingame.design2dManager.TextureManager;
import io.github.maingame.utilsManager.GameStat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture background1, background2, background3, background4a, background4b;

    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    final Player player;

    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Enemy> spawnList = new ArrayList<>();
    private int baseEnemyCount = 3;

    private float timeSinceLastSpawn = 0f;
    private float spawnDelay = 3.0f;
    private final GameStat stat;
    private final GameHUD hud;
    private OptionsScreen optionsScreen;

    private boolean isGameOver = false;
    private boolean isPaused = false;

    private final float minX = 0;
    private final float maxX = 3000;
    private final float minY = 0;
    private final float maxY = 1000;

    private final float leftBoundary = -200;
    private final float rightBoundary = 3200;


    public GameScreen(Main game, GameStat stat, Player player) {
        this.game = game;
        this.stat = stat;
        this.player = player;

        this.optionsScreen = new OptionsScreen(game);
        this.batch = game.batch;

        this.hud = new GameHUD(game, stat, player);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCamera.position.set(hudCamera.viewportWidth / 2, hudCamera.viewportHeight / 2, 0);
        hudCamera.update();

        background1 = new Texture(Gdx.files.internal("backgrounds/background_gamescreen1.png"));
        background2 = new Texture(Gdx.files.internal("backgrounds/background_gamescreen2.png"));
        background3 = new Texture(Gdx.files.internal("backgrounds/background_gamescreen3.png"));
        background4a = new Texture(Gdx.files.internal("backgrounds/background_gamescreen4a.png"));
        background4b = new Texture(Gdx.files.internal("backgrounds/background_gamescreen4b.png"));

        stat.loadGame();

        updatePlayerKeys();
        Platform.createPlatforms();

        setupFloorEnemies();
    }

    private void setupFloorEnemies() {
        spawnList.clear();

        int enemyCount = baseEnemyCount + stat.getFloors();

        for (int i = 0; i < enemyCount; i++) {
            Vector2 spawnPosition = new Vector2(-200, 100);
            Enemy enemy = EnemyFactory.createRandomEnemy(spawnPosition, Platform.getPlatforms(), player, stat);
            enemy.updateStats(stat);
            enemy.setScaleFactor(3f);
            spawnList.add(enemy);
        }

        spawnDelay = Math.max(1.0f, spawnDelay * 0.95f);
    }





    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
        }

        if (isPaused) {
            hud.renderPauseMenu(batch);
            return;
        }

        if (player.getHealth() <= 0 && player.isDeathAnimationFinished() && !isGameOver) {
            stat.setDeaths(stat.getDeaths() + 1);
            isGameOver = true;
            stat.saveGame();
        }

        float targetCameraX = player.getPosition().x + 300;
        camera.position.x += (targetCameraX - camera.position.x) * 0.05f;

        camera.position.x = MathUtils.clamp(camera.position.x, minX + camera.viewportWidth / 2, maxX - camera.viewportWidth / 2);
        camera.position.y = (float) Gdx.graphics.getHeight() / 2;

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        drawBackground(screenWidth, screenHeight);
        drawPlatforms();
        player.render(batch);

        if (!isGameOver) {
            player.update(delta, enemies, leftBoundary, rightBoundary);
            spawnEnemies(delta);
        }

        batch.end();

        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();
        hud.render(batch, player, screenWidth, screenHeight, isGameOver);
        batch.end();

        if (spawnList.isEmpty() && enemies.isEmpty()) {
            onPlayerReachNewFloor();
            setupFloorEnemies();
        }
    }

    @Override
    public void show() {
        if (ShopScreen.comingFromShop) {
            player.prepareForNewGame();
            stat.setFloors(0);
            ShopScreen.comingFromShop = false;
            resetGame();
        }
    }


    public void prepareForNewGame() {
        player.prepareForNewGame();
        stat.setFloors(0);
        resetGame();
    }


    public void updatePlayerKeys() {
        player.setLeftKey(optionsScreen.getLeftKey());
        player.setRightKey(optionsScreen.getRightKey());
        player.setJumpKey(optionsScreen.getJumpKey());
        player.setAttackKey(optionsScreen.getAttackKey());
        player.setRollKey(optionsScreen.getRollKey());
    }


    private void drawBackground(float screenWidth, float screenHeight) {

        float parallaxFactor1 = 0.95f;
        float parallaxFactor2 = 0.9f;
        float parallaxFactor3 = 0.85f;
        float parallaxFactor4a = 0.8f;
        float parallaxFactor4b = 0.75f;

        float backgroundX = camera.position.x - camera.viewportWidth / 2;
        float backgroundY = camera.position.y - camera.viewportHeight / 2;

        float backgroundWidth1 = screenWidth / parallaxFactor1;
        float backgroundWidth2 = screenWidth / parallaxFactor2;
        float backgroundWidth3 = screenWidth / parallaxFactor3;
        float backgroundWidth4a = screenWidth / parallaxFactor4a;
        float backgroundWidth4b = screenWidth / parallaxFactor4b;

        batch.draw(background1, backgroundX * parallaxFactor1, backgroundY, backgroundWidth1, screenHeight);
        batch.draw(background2, backgroundX * parallaxFactor2, backgroundY, backgroundWidth2, screenHeight);
        batch.draw(background3, backgroundX * parallaxFactor3, backgroundY, backgroundWidth3, screenHeight);
        batch.draw(background4a, backgroundX * parallaxFactor4a, backgroundY, backgroundWidth4a, screenHeight);
        batch.draw(background4b, backgroundX * parallaxFactor4b, backgroundY, backgroundWidth4b, screenHeight);
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

            float spawnX;
            if (MathUtils.randomBoolean()) {
                spawnX = camera.position.x - camera.viewportWidth / 2 - 100;
            } else {
                spawnX = camera.position.x + camera.viewportWidth / 2 + 100;
            }

            float spawnY = 100;

            enemyToSpawn.getPosition().set(spawnX, spawnY);
            enemies.add(enemyToSpawn);
            timeSinceLastSpawn = 0f;
        }

        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            enemy.render(batch);
            enemy.update(delta);

            if (enemy.isDeathAnimationFinished()) {
                stat.addGolds(enemy.getGold());
                stat.setKills(stat.getKills() + 1);
                iterator.remove();
            }

        }
    }

    private void onPlayerReachNewFloor() {
        stat.setFloors(stat.getFloors() + 1);
        stat.setMaxFloors(Math.max(stat.getFloors(), stat.getMaxFloors()));
        baseEnemyCount++;
    }

    private void resetGame() {
        player.reset();
        enemies.clear();
        spawnList.clear();
        timeSinceLastSpawn = 0f;
        baseEnemyCount = 3;
        setupFloorEnemies();
        isGameOver = false;
    }

    @Override
    public void dispose() {
        hud.dispose();
        if (background1 != null) background1.dispose();
        if (background2 != null) background2.dispose();
        if (background3 != null) background3.dispose();
        if (background4a != null) background4a.dispose();
        if (background4b != null) background4b.dispose();
        TextureManager.dispose();
    }





}
