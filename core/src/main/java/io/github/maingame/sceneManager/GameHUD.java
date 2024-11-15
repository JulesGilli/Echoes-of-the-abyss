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
import io.github.maingame.utilsManager.GameStat;

public class GameHUD {
    private final Main game;
    private final BitmapFont goldFont;
    private final BitmapFont menuFont;
    private final BitmapFont headerFont;
    private final Texture healthFrame;
    private final Texture healthBar;
    private final Texture buttonMenu;
    private final Texture backgroundGUI;
    private final Texture goldIcon;
    private final Texture headerGUI;
    private final GlyphLayout layout;
    private GameStat stat;

    private final Rectangle mainMenuButtonBounds;
    private final Rectangle resumeButtonBounds;
    private final Rectangle quitButtonBounds;
    private final Rectangle shopButtonBounds;

    public GameHUD(Main game, GameStat stat) {
        this.game = game;
        this.stat = stat;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Jacquard12-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;

        goldFont = generator.generateFont(parameter);
        Color MY_GOLD = new Color(255 / 255f, 204 / 255f, 101 / 255f, 1);
        goldFont.setColor(MY_GOLD);

        menuFont = generator.generateFont(parameter);
        menuFont.setColor(Color.BROWN);

        headerFont = generator.generateFont(parameter);
        Color LIGHT_GREEN = new Color(0 / 255f, 153 / 255f, 76 / 255f, 1);
        headerFont.setColor(LIGHT_GREEN);

        generator.dispose();

        healthFrame = new Texture(Gdx.files.internal("Health_01.png"));
        healthBar = new Texture(Gdx.files.internal("Health_01_Bar01.png"));
        buttonMenu = new Texture(Gdx.files.internal("buttonMenu.png"));
        backgroundGUI = new Texture(Gdx.files.internal("BackGroundGUI.png"));
        goldIcon = new Texture(Gdx.files.internal("gold.png"));
        headerGUI = new Texture(Gdx.files.internal("Header.png"));


        layout = new GlyphLayout();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float buttonWidth = 450;
        float buttonHeight = 200;

        resumeButtonBounds = new Rectangle(screenWidth / 2 - buttonWidth / 2, screenHeight / 2 + 120, buttonWidth, buttonHeight);
        mainMenuButtonBounds = new Rectangle(screenWidth / 2 - buttonWidth / 2, screenHeight / 2, buttonWidth, buttonHeight);
        quitButtonBounds = new Rectangle(screenWidth / 2 - buttonWidth / 2, screenHeight / 2 - 240, buttonWidth, buttonHeight);
        shopButtonBounds = new Rectangle(screenWidth / 2 - buttonWidth / 2, screenHeight / 2 - 120, buttonWidth, buttonHeight);
    }

    public void renderPauseMenu(SpriteBatch batch) {
        batch.begin();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        batch.draw(backgroundGUI, screenWidth / 2 - backgroundGUI.getWidth() / 1.4f, screenHeight / 2 - backgroundGUI.getHeight() / 1.4f, 600, 800);

        menuFont.draw(batch, "Options", screenWidth / 2 - 100, screenHeight - 200);

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
        drawFloor(batch, screenWidth, screenHeight);


        if (isGameOver) {
            displayGameOverMenu(batch, screenWidth, screenHeight);
            handleGameOverInput();
        }
    }

    private void drawFloor(SpriteBatch batch, float screenWidth, float screenHeight) {
        if (stat != null) {
            String floorText = "Floor: " + stat.getFloors();
            layout.setText(headerFont, floorText);
            headerFont.draw(batch, floorText, screenWidth / 2 - layout.width / 2, screenHeight - 20);
            float size = 3;
            batch.draw(headerGUI,
                screenWidth / 2 - headerGUI.getWidth() * size / 2f,
                screenHeight - 80 - headerGUI.getHeight() * size / 2f,
                headerGUI.getWidth() * size,
                headerGUI.getHeight() * size);
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
        String goldText = "" + stat.getGolds();
        layout.setText(goldFont, goldText);
        goldFont.draw(batch, goldText, screenWidth - 200, screenHeight - 40);
        batch.draw(goldIcon, screenWidth - 270, screenHeight - 90,goldIcon.getWidth() * 3,goldIcon.getHeight() * 3);

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

        menuFont.draw(batch, "Main Menu", mainMenuButtonBounds.x + 100, mainMenuButtonBounds.y + 125);
        menuFont.draw(batch, "Quit", quitButtonBounds.x + 160, quitButtonBounds.y + 125);
        menuFont.draw(batch, "Shop", shopButtonBounds.x + 160, shopButtonBounds.y + 125);

    }

    private void handleGameOverInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

            if (mainMenuButtonBounds.contains(clickPosition)) {
                game.setScreen(new MainMenuScreen(game));
            } else if (quitButtonBounds.contains(clickPosition)) {
                Gdx.app.exit();
            } else if (shopButtonBounds.contains(clickPosition)) {
                game.setScreen(new ShopScreen(game, stat, ((GameScreen) game.getScreen()).player)); // Passez le joueur
            }
        }
    }

    public void dispose() {


        if (goldFont != null) goldFont.dispose();
        if (menuFont != null) menuFont.dispose();
        if (healthFrame != null) healthFrame.dispose();
        if (healthBar != null) healthBar.dispose();
        if (buttonMenu != null) buttonMenu.dispose();
        if (backgroundGUI != null) backgroundGUI.dispose();
        if (goldIcon != null) goldIcon.dispose();
        if (headerGUI != null) headerGUI.dispose();
    }


}
