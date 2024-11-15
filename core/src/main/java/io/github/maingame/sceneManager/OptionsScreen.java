package io.github.maingame.sceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import io.github.maingame.Main;

public class OptionsScreen extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final BitmapFont titleFont;
    private final Texture backgroundTexture;
    private final Texture buttonTexture;
    private final Texture backgroundGUI;
    private final Rectangle backButtonBounds;


    private final Rectangle leftKeyBounds;
    private final Rectangle rightKeyBounds;
    private final Rectangle jumpKeyBounds;
    private final Rectangle attackKeyBounds;
    private final Rectangle rollKeyBounds;

    private boolean isDispose = false;
    private int leftKey = Input.Keys.A;
    private int rightKey = Input.Keys.D;
    private int jumpKey = Input.Keys.SPACE;
    private int attackKey = Input.Keys.F;
    private int rollKey = Input.Keys.SHIFT_LEFT;
    private boolean waitingForNewKey = false;
    private String keyToRemap = "";

    private final Preferences preferences;

    public OptionsScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();

        backgroundTexture = new Texture(Gdx.files.internal("backGroundMainMenu.png"));
        buttonTexture = new Texture(Gdx.files.internal("buttonMenu.png"));
        backgroundGUI = new Texture(Gdx.files.internal("BackGroundGUI.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Jacquard12-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 56;
        font = generator.generateFont(parameter);
        font.setColor(Color.BROWN);

        parameter.size = 96;
        titleFont = generator.generateFont(parameter);
        titleFont.setColor(Color.BROWN);

        generator.dispose();

        float buttonWidth = 450;
        float buttonHeight = 150;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        backButtonBounds = new Rectangle((screenWidth - buttonWidth) / 2, 100, buttonWidth, buttonHeight);

        leftKeyBounds = new Rectangle(screenWidth / 2 - 150, screenHeight - 400, 200, 100);
        rightKeyBounds = new Rectangle(screenWidth / 2 - 150, screenHeight - 480, 200, 100);
        jumpKeyBounds = new Rectangle(screenWidth / 2 - 150, screenHeight - 560, 200, 100);
        attackKeyBounds = new Rectangle(screenWidth / 2 - 150, screenHeight - 640, 200, 100);
        rollKeyBounds = new Rectangle(screenWidth / 2 - 150, screenHeight - 720, 200, 100);

        preferences = Gdx.app.getPreferences("GamePreferences");
        loadKeys();
        System.out.println("Option Screen");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        batch.begin();

        batch.draw(backgroundTexture, 0, 0, screenWidth, screenHeight);
        batch.draw(backgroundGUI, screenWidth / 2 - backgroundGUI.getWidth() / 1.4f, screenHeight / 2 - backgroundGUI.getHeight() / 1.4f, 600, 800);
        batch.draw(buttonTexture, backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);

        titleFont.draw(batch, "Options", screenWidth / 2 - 150, screenHeight - 180);
        font.draw(batch, "Back", backButtonBounds.x + 170, backButtonBounds.y + 90);

        font.draw(batch, "Left: " + Input.Keys.toString(leftKey), leftKeyBounds.x, leftKeyBounds.y + 50);
        font.draw(batch, "Right: " + Input.Keys.toString(rightKey), rightKeyBounds.x, rightKeyBounds.y + 50);
        font.draw(batch, "Jump: " + Input.Keys.toString(jumpKey), jumpKeyBounds.x, jumpKeyBounds.y + 50);
        font.draw(batch, "Attack: " + Input.Keys.toString(attackKey), attackKeyBounds.x, attackKeyBounds.y + 50);
        font.draw(batch, "Roll: " + Input.Keys.toString(rollKey), rollKeyBounds.x, rollKeyBounds.y + 50);

        batch.end();

        handleInput();
    }

    private void loadKeys() {
        leftKey = preferences.getInteger("leftKey", Input.Keys.A);
        rightKey = preferences.getInteger("rightKey", Input.Keys.D);
        jumpKey = preferences.getInteger("jumpKey", Input.Keys.SPACE);
        attackKey = preferences.getInteger("attackKey", Input.Keys.F);
        rollKey = preferences.getInteger("rollKey", Input.Keys.SHIFT_LEFT);
    }

    private void saveKeys() {
        preferences.putInteger("leftKey", leftKey);
        preferences.putInteger("rightKey", rightKey);
        preferences.putInteger("jumpKey", jumpKey);
        preferences.putInteger("attackKey", attackKey);
        preferences.putInteger("rollKey", rollKey);
        preferences.flush();
    }

    private void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

            if (backButtonBounds.contains(clickPosition)) {
                game.setScreen(new MainMenuScreen(game));
            } else if (leftKeyBounds.contains(clickPosition)) {
                waitingForNewKey = true;
                keyToRemap = "left";
            } else if (rightKeyBounds.contains(clickPosition)) {
                waitingForNewKey = true;
                keyToRemap = "right";
            } else if (jumpKeyBounds.contains(clickPosition)) {
                waitingForNewKey = true;
                keyToRemap = "jump";
            } else if (attackKeyBounds.contains(clickPosition)) {
                waitingForNewKey = true;
                keyToRemap = "attack";
            } else if (rollKeyBounds.contains(clickPosition)) {
                waitingForNewKey = true;
                keyToRemap = "roll";
            }

        }
        if (waitingForNewKey) {
            for (int key = 0; key < 256; key++) {
                if (Gdx.input.isKeyJustPressed(key)) {
                    assignNewKey(key);
                    waitingForNewKey = false;
                    saveKeys();
                }
            }
        }
    }

    private void assignNewKey(int key) {
        switch (keyToRemap) {
            case "left":
                leftKey = key;
                break;
            case "right":
                rightKey = key;
                break;
            case "jump":
                jumpKey = key;
                break;
            case "attack":
                attackKey = key;
                break;
            case "roll":
                rollKey = key;
                break;
        }
        keyToRemap = "";
    }


    @Override
    public void dispose() {
        if (!isDispose) {
            batch.dispose();
            font.dispose();
            titleFont.dispose();
            backgroundTexture.dispose();
            buttonTexture.dispose();
            backgroundGUI.dispose();
        }
    }

    public int getLeftKey() {
        return leftKey;
    }

    public int getRightKey() {
        return rightKey;
    }

    public int getJumpKey() {
        return jumpKey;
    }

    public int getAttackKey() {
        return attackKey;
    }
    public int getRollKey() {
        return rollKey;
    }
}
