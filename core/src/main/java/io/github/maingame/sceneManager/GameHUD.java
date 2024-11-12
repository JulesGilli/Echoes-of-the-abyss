package io.github.maingame.sceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Main;
import io.github.maingame.characterManager.Player;

public class GameHUD {
    private final Main game;
    private final BitmapFont goldFont;
    private final BitmapFont menuFont;
    private final Texture healthFrame;
    private final Texture healthBar;
    private final Texture buttonMenu;
    private final Texture backgroundGUI;
    private final GlyphLayout layout;

    private final Rectangle mainMenuButtonBounds;
    private final Rectangle resumeButtonBounds;
    private final Rectangle quitButtonBounds;
    private final Rectangle shopButtonBounds;

    public GameHUD(Main game) {
        this.game = game;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Jacquard12-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;

        goldFont = generator.generateFont(parameter);
        goldFont.setColor(Color.YELLOW);

        menuFont = generator.generateFont(parameter);
        menuFont.setColor(Color.BROWN);
        generator.dispose();

        healthFrame = new Texture(Gdx.files.internal("Health_01.png"));
        healthBar = new Texture(Gdx.files.internal("Health_01_Bar01.png"));
        buttonMenu = new Texture(Gdx.files.internal("buttonMenu.png"));
        backgroundGUI = new Texture(Gdx.files.internal("BackGroundGUI.png"));


        layout = new GlyphLayout();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float buttonWidth = 450;
        float buttonHeight = 200;

        resumeButtonBounds = new Rectangle(screenWidth / 2 - buttonWidth / 2, screenHeight / 2 + 100, buttonWidth, buttonHeight);
        mainMenuButtonBounds = new Rectangle(screenWidth / 2 - buttonWidth / 2, screenHeight / 2, buttonWidth, buttonHeight);
        quitButtonBounds = new Rectangle(screenWidth / 2 - buttonWidth / 2, screenHeight / 2 - 240, buttonWidth, buttonHeight);
        shopButtonBounds = new Rectangle(screenWidth / 2 - buttonWidth / 2, screenHeight / 2 - 120, buttonWidth, buttonHeight);
    }

    public void renderPauseMenu(SpriteBatch batch) {
        batch.begin();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        batch.draw(backgroundGUI, screenWidth / 2 - backgroundGUI.getWidth() / 1.4f, screenHeight / 2 - backgroundGUI.getHeight() / 1.4f, 600, 800);


        batch.draw(buttonMenu, resumeButtonBounds.x, resumeButtonBounds.y, resumeButtonBounds.width, resumeButtonBounds.height);
        batch.draw(buttonMenu, mainMenuButtonBounds.x, mainMenuButtonBounds.y, mainMenuButtonBounds.width, mainMenuButtonBounds.height);
        batch.draw(buttonMenu, quitButtonBounds.x, quitButtonBounds.y, quitButtonBounds.width, quitButtonBounds.height);

        menuFont.draw(batch, "Resume", resumeButtonBounds.x + 120, resumeButtonBounds.y + 120);
        menuFont.draw(batch, "Main Menu", mainMenuButtonBounds.x + 100, mainMenuButtonBounds.y + 120);
        menuFont.draw(batch, "Quit", quitButtonBounds.x + 160, quitButtonBounds.y + 120);

        batch.end();

        handlePauseMenuInput();
    }

    private void handlePauseMenuInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

            if (resumeButtonBounds.contains(clickPosition)) {
                ((GameScreen) game.getScreen()).resumeGame();
            } else if (mainMenuButtonBounds.contains(clickPosition)) {
                game.setScreen(new MainMenuScreen(game));
            } else if (quitButtonBounds.contains(clickPosition)) {
                Gdx.app.exit();
            }
        }
    }

    public void render(SpriteBatch batch, Player player, float screenWidth, float screenHeight, boolean isGameOver) {
        drawHealthBar(batch, player, screenHeight);
        drawGold(batch, player, screenWidth, screenHeight);

        if (isGameOver) {
            displayGameOverMenu(batch, screenWidth, screenHeight);
            handleGameOverInput();
        }
    }

    private void drawHealthBar(SpriteBatch batch, Player player, float screenHeight) {
        float offset = 100;
        float sizeHealthBar = 4;
        batch.draw(healthFrame, offset, screenHeight - offset, healthFrame.getWidth() * sizeHealthBar, healthFrame.getHeight() * sizeHealthBar);

        float healthPercentage = player.getHealth() / (float) player.getMaxHealth();
        float healthBarWidth = healthBar.getWidth() * healthPercentage;
        batch.draw(healthBar, offset + 64, screenHeight - offset + 36, healthBarWidth * sizeHealthBar * 1.025f, healthBar.getHeight() * sizeHealthBar);
    }

    private void drawGold(SpriteBatch batch, Player player, float screenWidth, float screenHeight) {
        String goldText = "Gold: " + player.getGold();
        layout.setText(goldFont, goldText);
        goldFont.draw(batch, goldText, screenWidth - 270, screenHeight - 40);
    }

    private void displayGameOverMenu(SpriteBatch batch, float screenWidth, float screenHeight) {
        Gdx.app.log("GameHUD", "Entering displayGameOverMenu");

        batch.draw(backgroundGUI, screenWidth / 2 - backgroundGUI.getWidth() / 1.4f, screenHeight / 2 - backgroundGUI.getHeight() / 1.4f, 600, 800);


        String gameOverText = "Game Over";
        layout.setText(menuFont, gameOverText);
        menuFont.draw(batch, gameOverText, screenWidth / 2f - layout.width / 2, screenHeight / 2f + 250);

        batch.draw(buttonMenu, mainMenuButtonBounds.x, mainMenuButtonBounds.y + 5, mainMenuButtonBounds.width, mainMenuButtonBounds.height);
        batch.draw(buttonMenu, quitButtonBounds.x, quitButtonBounds.y + 5, quitButtonBounds.width, quitButtonBounds.height);
        batch.draw(buttonMenu, shopButtonBounds.x, shopButtonBounds.y + 5, shopButtonBounds.width, shopButtonBounds.height);

        menuFont.draw(batch, "Main Menu", mainMenuButtonBounds.x + 100, mainMenuButtonBounds.y + 100);
        menuFont.draw(batch, "Quit", quitButtonBounds.x + 160, quitButtonBounds.y + 100);
        menuFont.draw(batch, "Shop", shopButtonBounds.x + 160, shopButtonBounds.y + 100);

    }

    private void handleGameOverInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

            if (mainMenuButtonBounds.contains(clickPosition)) {
                game.setScreen(new MainMenuScreen(game));
            } else if (quitButtonBounds.contains(clickPosition)) {
                Gdx.app.exit();
            } else if (shopButtonBounds.contains(clickPosition)) {
                game.setScreen(new ShopScreen(game));
            }
        }
    }

    public void dispose() {
        goldFont.dispose();
        menuFont.dispose();
        healthFrame.dispose();
        healthBar.dispose();
        buttonMenu.dispose();
        backgroundGUI.dispose();
    }
}
