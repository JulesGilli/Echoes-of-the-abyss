package io.github.maingame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.maingame.core.GameStat;
import io.github.maingame.core.Main;
import io.github.maingame.entities.Player;
import io.github.maingame.utils.ComboSystem;
import io.github.maingame.utils.FontManager;
import io.github.maingame.utils.UIHelper;

import java.util.HashMap;
import java.util.Map;

import static io.github.maingame.core.Main.VIRTUAL_HEIGHT;
import static io.github.maingame.core.Main.VIRTUAL_WIDTH;

public class HUD {
    private final Main game;
    private final BitmapFont goldFont;
    private final BitmapFont menuFont;
    private final BitmapFont headerFont;
    private final Texture healthFrame;
    private final Texture healthBar;
    private final Texture staminaFrame;
    private final Texture staminaBar;

    private final Texture buttonMenu;
    private final Texture backgroundGUI;
    private final Texture goldIcon;
    private final Texture headerGUI;
    private final Texture headerTuto;
    private final Texture backgroundTexture;

    private final Map<String, Texture> potionTextures = new HashMap<>();
    private final GlyphLayout layout;
    private final GameStat stat;
    private final Player player;

    private final Rectangle mainMenuButtonBounds;
    private final Rectangle resumeButtonBounds;
    private final Rectangle quitButtonBounds;

    public HUD(Main game, GameStat stat, Player player) {
        this.game = game;
        this.stat = stat;
        this.player = player;

        goldFont = FontManager.getFont(64);
        goldFont.setColor(new Color(255 / 255f, 204 / 255f, 101 / 255f, 1));

        menuFont = FontManager.getFont(64);
        menuFont.setColor(Color.BROWN);

        headerFont = FontManager.getFont(64);
        headerFont.setColor(new Color(0 / 255f, 153 / 255f, 76 / 255f, 1));

        potionTextures.put("StrengthPotion", new Texture(Gdx.files.internal("icons/items/potion/icon_potionStrength.png")));
        potionTextures.put("SpeedPotion", new Texture(Gdx.files.internal("icons/items/potion/icon_potionSpeed.png")));
        potionTextures.put("HealPotion", new Texture(Gdx.files.internal("icons/items/potion/icon_potionHealth.png")));
        potionTextures.put("ArmorPotion", new Texture(Gdx.files.internal("icons/items/potion/icon_potionArmor.png")));

        staminaFrame = new Texture(Gdx.files.internal("GUI/sprite_stamina.png"));
        staminaBar = new Texture(Gdx.files.internal("GUI/sprite_staminaBar.png"));
        healthFrame = new Texture(Gdx.files.internal("GUI/sprite_health.png"));
        healthBar = new Texture(Gdx.files.internal("GUI/sprite_healthBar.png"));

        headerTuto = new Texture(Gdx.files.internal("GUI/sprite_header_tuto.png"));
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/background_menuscreen.png"));

        buttonMenu = new Texture(Gdx.files.internal("GUI/button_basic.png"));
        backgroundGUI = new Texture(Gdx.files.internal("backgrounds/background_gamescreen_GUI.png"));
        goldIcon = new Texture(Gdx.files.internal("icons/icon_gold.png"));
        headerGUI = new Texture(Gdx.files.internal("GUI/header_floors.png"));

        layout = new GlyphLayout();

        float buttonWidth = 450;
        float buttonHeight = 200;

        resumeButtonBounds = new Rectangle(VIRTUAL_WIDTH / 2 - buttonWidth / 2, VIRTUAL_HEIGHT / 2 + 120, buttonWidth, buttonHeight);
        mainMenuButtonBounds = new Rectangle(VIRTUAL_WIDTH / 2 - buttonWidth / 2, VIRTUAL_HEIGHT / 2, buttonWidth, buttonHeight);
        quitButtonBounds = new Rectangle(VIRTUAL_WIDTH / 2 - buttonWidth / 2, VIRTUAL_HEIGHT / 2 - 240, buttonWidth, buttonHeight);
    }

    private void drawPotion(SpriteBatch batch, Player player) {
        String potionType = player.getInventory().getPotionTexture();
        if (potionType != null && potionTextures.containsKey(potionType)) {
            Texture potionTexture = potionTextures.get(potionType);
            float potionX = 100;
            float potionY = VIRTUAL_HEIGHT - 210;
            float potionSize = 80;
            batch.draw(potionTexture, potionX, potionY, potionSize, potionSize);
        }
    }

    public void showStats(SpriteBatch batch, Player player) {
        menuFont.getData().setScale(0.6f, 0.6f);
        menuFont.draw(batch, "attack: " + player.getAttack(), mainMenuButtonBounds.x + 40, mainMenuButtonBounds.y + 30);
        menuFont.draw(batch, "speed: " + player.getSpeed(), mainMenuButtonBounds.x + 40, mainMenuButtonBounds.y - 10);
        menuFont.draw(batch, "health: " + player.getHealth(), mainMenuButtonBounds.x + 40, mainMenuButtonBounds.y - 50);
        menuFont.getData().setScale(1, 1);
    }

    public void renderPauseMenu(SpriteBatch batch, FitViewport viewport) {
        Vector2 mousePos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        batch.draw(backgroundGUI, VIRTUAL_WIDTH / 2 - backgroundGUI.getWidth() / 1.4f, VIRTUAL_HEIGHT / 2 - backgroundGUI.getHeight() / 1.4f, 600, 800);

        menuFont.draw(batch, "Options", VIRTUAL_WIDTH / 2 - 100, VIRTUAL_HEIGHT - 200);

        UIHelper.drawButton(batch, buttonMenu, resumeButtonBounds, UIHelper.isHovered(resumeButtonBounds, mousePos));
        UIHelper.drawButton(batch, buttonMenu, mainMenuButtonBounds, UIHelper.isHovered(mainMenuButtonBounds, mousePos));
        UIHelper.drawButton(batch, buttonMenu, quitButtonBounds, UIHelper.isHovered(quitButtonBounds, mousePos));

        menuFont.draw(batch, "Resume", resumeButtonBounds.x + 120, resumeButtonBounds.y + 120);
        menuFont.draw(batch, "Main Menu", mainMenuButtonBounds.x + 100, mainMenuButtonBounds.y + 120);
        menuFont.draw(batch, "Quit", quitButtonBounds.x + 160, quitButtonBounds.y + 120);

        showStats(batch, player);

        handlePauseMenuInput(viewport);
    }

    private void handlePauseMenuInput(FitViewport viewport) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            if (resumeButtonBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                ((GameScreen) game.getScreen()).resumeGame();
            } else if (mainMenuButtonBounds.contains(clickPosition)) {
                game.getSoundManager().stopMusic("fight");
                game.getSoundManager().playSound("select");
                game.setScreen(new MainMenuScreen(game));
            } else if (quitButtonBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                Gdx.app.exit();
            }
        }
    }

    public void renderFirstGameInstructions(SpriteBatch batch, int leftKey, int rightKey, int jumpKey, int attackKey, int rollKey, int potionKey) {
        float instructionsX = VIRTUAL_WIDTH / 2 - 300;
        float instructionsY = VIRTUAL_HEIGHT - 200;

        String instructionsMovement = "To move :\nLeft: " + Input.Keys.toString(leftKey)
            + "\nRight: " + Input.Keys.toString(rightKey)
            + "\nJump: " + Input.Keys.toString(jumpKey);

        String instructionsAction = "For capacity :\nAttack: " + Input.Keys.toString(attackKey)
            + "\nRoll: " + Input.Keys.toString(rollKey)
            + "\nPotion: " + Input.Keys.toString(potionKey);

        headerFont.setColor(Color.BROWN);

        float headerSize = 1.8f;
        batch.draw(backgroundGUI,
            VIRTUAL_WIDTH / 2 - backgroundGUI.getWidth() * headerSize / 2f,
            VIRTUAL_HEIGHT / 2 - backgroundGUI.getHeight() * headerSize / 2f,
            backgroundGUI.getWidth() * headerSize,
            backgroundGUI.getHeight() * headerSize);

        headerSize = 2;
        batch.draw(headerTuto,
            VIRTUAL_WIDTH / 2 - headerTuto.getWidth() * headerSize / 2f,
            VIRTUAL_HEIGHT / 2 + 200,
            headerTuto.getWidth() * headerSize,
            headerTuto.getHeight() * headerSize);

        headerFont.getData().setScale(1.5f);
        headerFont.draw(batch, "Tutorial", VIRTUAL_WIDTH / 2 - 150, VIRTUAL_HEIGHT - 70);

        headerFont.getData().setScale(1f);
        headerFont.draw(batch, instructionsMovement, instructionsX + 130, instructionsY - 80);
        headerFont.draw(batch, instructionsAction, instructionsX + 130, instructionsY - 400);

        headerFont.getData().setScale(1.0f);
        headerFont.setColor(0 / 255f, 153 / 255f, 76 / 255f, 1);
    }

    public void renderWaveTransition(SpriteBatch batch, int waveNumber, float alpha) {
        String waveText = "Floor " + waveNumber;

        headerFont.setColor(0 / 255f, 153 / 255f, 76 / 255f, alpha);
        headerFont.getData().setScale(2.0f);

        layout.setText(headerFont, waveText);
        headerFont.draw(batch, waveText, VIRTUAL_WIDTH / 2f - layout.width / 2, VIRTUAL_HEIGHT / 2f + layout.height / 2);

        headerFont.getData().setScale(1.0f);
        headerFont.setColor(0 / 255f, 153 / 255f, 76 / 255f, 1);
    }

    public void render(SpriteBatch batch, Player player, float screenWidth, float screenHeight, boolean isGameOver, FitViewport viewport, ComboSystem comboSystem) {
        drawHealthBar(batch, player, screenHeight);
        drawStaminaBar(batch, player, screenHeight);
        drawGold(batch, player, screenWidth, screenHeight);
        drawFloor(batch, screenWidth, screenHeight);
        drawPotion(batch, player);
        drawCombo(batch, comboSystem, screenWidth, screenHeight);

        if (isGameOver) {
            displayGameOverMenu(batch, screenWidth, screenHeight, viewport);
            handleGameOverInput(viewport);
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
        float offset = 70;
        float sizeHealthBar = 2;
        batch.draw(healthFrame, offset, screenHeight - offset, healthFrame.getWidth() * sizeHealthBar, healthFrame.getHeight() * sizeHealthBar);

        float healthPercentage = player.getHealth() / player.getMaxHealth();
        float healthBarWidth = healthBar.getWidth() * healthPercentage;
        batch.draw(healthBar, offset + 12, screenHeight - offset + 12, healthBarWidth * sizeHealthBar * 1f, healthBar.getHeight() * sizeHealthBar);
    }

    private void drawStaminaBar(SpriteBatch batch, Player player, float screenHeight) {
        float offset = 120;
        float sizeStaminaBar = 2;
        batch.draw(staminaFrame, 70, screenHeight - offset, staminaFrame.getWidth() * sizeStaminaBar, staminaFrame.getHeight() * sizeStaminaBar);

        float staminaPercentage = player.getStamina() / player.getMaxStamina();
        float staminaBarWidth = staminaBar.getWidth() * staminaPercentage;
        batch.draw(staminaBar, 82, screenHeight - offset + 12, staminaBarWidth * sizeStaminaBar, staminaBar.getHeight() * sizeStaminaBar);
    }

    private void drawGold(SpriteBatch batch, Player player, float screenWidth, float screenHeight) {
        String goldText = "" + stat.getGolds();
        layout.setText(goldFont, goldText);
        goldFont.draw(batch, goldText, screenWidth - 200, screenHeight - 40);
        batch.draw(goldIcon, screenWidth - 270, screenHeight - 90, goldIcon.getWidth() * 3, goldIcon.getHeight() * 3);
    }

    private void drawCombo(SpriteBatch batch, ComboSystem comboSystem, float screenWidth, float screenHeight) {
        if (comboSystem == null || !comboSystem.shouldDisplay()) return;

        int combo = comboSystem.getComboCount();
        String comboText = combo + "x COMBO!";
        float scale = 1.0f + Math.min(combo * 0.1f, 1.0f);

        goldFont.getData().setScale(scale);
        Color prevColor = goldFont.getColor().cpy();
        float r = Math.min(1f, 0.5f + combo * 0.1f);
        goldFont.setColor(r, 0.9f - combo * 0.05f, 0.1f, Math.min(1f, comboSystem.getDisplayTimer()));
        layout.setText(goldFont, comboText);
        goldFont.draw(batch, comboText, screenWidth / 2f - layout.width / 2, screenHeight - 120);
        goldFont.setColor(prevColor);
        goldFont.getData().setScale(1f);
    }

    private void displayGameOverMenu(SpriteBatch batch, float screenWidth, float screenHeight, FitViewport viewport) {
        Vector2 mousePos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        batch.draw(backgroundGUI, screenWidth / 2 - backgroundGUI.getWidth() / 1.4f, screenHeight / 2 - backgroundGUI.getHeight() / 1.4f, 600, 800);

        String gameOverText = "Game Over";
        layout.setText(menuFont, gameOverText);
        menuFont.draw(batch, gameOverText, screenWidth / 2f - layout.width / 2, screenHeight / 2f + 250);

        Rectangle mmBounds = new Rectangle(mainMenuButtonBounds.x, mainMenuButtonBounds.y + 5, mainMenuButtonBounds.width, mainMenuButtonBounds.height);
        Rectangle qBounds = new Rectangle(quitButtonBounds.x, quitButtonBounds.y + 5, quitButtonBounds.width, quitButtonBounds.height);

        UIHelper.drawButton(batch, buttonMenu, mmBounds, UIHelper.isHovered(mmBounds, mousePos));
        UIHelper.drawButton(batch, buttonMenu, qBounds, UIHelper.isHovered(qBounds, mousePos));

        menuFont.draw(batch, "Main Menu", mainMenuButtonBounds.x + 100, mainMenuButtonBounds.y + 125);
        menuFont.draw(batch, "Quit", quitButtonBounds.x + 160, quitButtonBounds.y + 125);
    }

    private void handleGameOverInput(FitViewport viewport) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            if (mainMenuButtonBounds.contains(clickPosition)) {
                game.getSoundManager().stopMusic("fight");
                game.setScreen(new MainMenuScreen(game));
            } else if (quitButtonBounds.contains(clickPosition)) {
                Gdx.app.exit();
            }
        }
    }

    public void dispose() {
        for (Texture texture : potionTextures.values()) {
            if (texture != null) texture.dispose();
        }

        if (goldFont != null) goldFont.dispose();
        if (menuFont != null) menuFont.dispose();
        if (headerFont != null) headerFont.dispose();
        if (healthFrame != null) healthFrame.dispose();
        if (healthBar != null) healthBar.dispose();
        if (staminaFrame != null) staminaFrame.dispose();
        if (staminaBar != null) staminaBar.dispose();
        if (buttonMenu != null) buttonMenu.dispose();
        if (backgroundGUI != null) backgroundGUI.dispose();
        if (goldIcon != null) goldIcon.dispose();
        if (headerGUI != null) headerGUI.dispose();
        if (headerTuto != null) headerTuto.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
    }
}
