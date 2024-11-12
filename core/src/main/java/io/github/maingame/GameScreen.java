package io.github.maingame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    private final BitmapFont goldFont;
    private final BitmapFont menuFont;
    private final GlyphLayout layout = new GlyphLayout();
    private float timeSinceLastSpawn = 0f;

    private final Texture healthFrame;
    private final Texture healthBar;

    private boolean isGameOver = false;

    public GameScreen(Main game) {
        this.game = game;
        this.batch = game.batch;

        this.player = new Player(new Vector2(100, 100), Platform.getPlatforms());

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Jacquard12-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        goldFont = generator.generateFont(parameter);
        goldFont.setColor(Color.YELLOW);
        menuFont = generator.generateFont(parameter);
        menuFont.setColor(Color.WHITE);



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
            drawHealthBar(screenHeight);
            drawGold(screenWidth, screenHeight);
        } else {
            displayGameOverMenu(screenWidth, screenHeight);
        }

        batch.end();
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

    private void spawnEnemies(float delta) {
        timeSinceLastSpawn += delta;
        if (timeSinceLastSpawn >= 3.0f) {
            spawnEnemy();
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

    private void spawnEnemy() {
        float spawnX = MathUtils.randomBoolean() ? -200 : Gdx.graphics.getWidth();

        Enemy newEnemy = new Enemy(new Vector2(spawnX, 100), Platform.getPlatforms(), player);
        enemies.add(newEnemy);
    }

    private void drawHealthBar(float screenHeight) {
        float offset = 100;
        float sizeHealthBar = 4;
        batch.draw(healthFrame, offset, screenHeight - offset, healthFrame.getWidth() * sizeHealthBar, healthFrame.getHeight() * sizeHealthBar);

        float healthPercentage = player.getHealth() / (float) player.getMaxHealth();
        float healthBarWidth = healthBar.getWidth() * healthPercentage;
        batch.draw(healthBar, offset + 64, screenHeight - offset + 36, healthBarWidth * sizeHealthBar * 1.025f, healthBar.getHeight() * sizeHealthBar);
    }

    private void drawGold(float screenWidth, float screenHeight) {
        String goldText = "Gold: " + player.getGold();
        layout.setText(goldFont, goldText);
        goldFont.draw(batch, goldText, screenWidth - 270, screenHeight - 40);
    }

    private void displayGameOverMenu(float screenWidth, float screenHeight) {
        String gameOverText = "Game Over";
        String replayText = "Press R to Replay";
        layout.setText(menuFont, gameOverText);
        menuFont.draw(batch, gameOverText, screenWidth / 2f - layout.width / 2, screenHeight / 2f + 50);

        layout.setText(menuFont, replayText);
        menuFont.draw(batch, replayText, screenWidth / 2f - layout.width / 2, screenHeight / 2f - 50);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            resetGame();
        }
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
        goldFont.dispose();
        menuFont.dispose();
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
