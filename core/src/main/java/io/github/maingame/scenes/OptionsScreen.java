package io.github.maingame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.maingame.utils.FontManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import io.github.maingame.core.Main;
import io.github.maingame.input.InputManager;
import io.github.maingame.utils.UIHelper;

import static io.github.maingame.core.Main.VIRTUAL_HEIGHT;
import static io.github.maingame.core.Main.VIRTUAL_WIDTH;

public class OptionsScreen extends ScreenAdapter {
    private final Main game;
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
    private final Rectangle potionKeyBounds;

    private final OrthographicCamera camera;
    private final StretchViewport viewport;

    private boolean waitingForNewKey = false;
    private String keyToRemap = "";
    private float fadeAlpha = 1f;

    public OptionsScreen(Main game) {
        this.game = game;

        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/background_menuscreen.png"));
        buttonTexture = new Texture(Gdx.files.internal("GUI/button_basic.png"));
        backgroundGUI = new Texture(Gdx.files.internal("backgrounds/background_gamescreen_GUI.png"));

        font = FontManager.getFont(56);
        font.setColor(Color.BROWN);

        titleFont = FontManager.getFont(96);
        titleFont.setColor(Color.BROWN);

        camera = new OrthographicCamera();
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply(true);

        float buttonWidth = 450;
        float buttonHeight = 150;

        backButtonBounds = new Rectangle((VIRTUAL_WIDTH - buttonWidth) / 2, 100, buttonWidth, buttonHeight);

        leftKeyBounds = new Rectangle(VIRTUAL_WIDTH / 2 - 150, VIRTUAL_HEIGHT - 320, 200, 100);
        rightKeyBounds = new Rectangle(VIRTUAL_WIDTH / 2 - 150, VIRTUAL_HEIGHT - 400, 200, 100);
        jumpKeyBounds = new Rectangle(VIRTUAL_WIDTH / 2 - 150, VIRTUAL_HEIGHT - 480, 200, 100);
        attackKeyBounds = new Rectangle(VIRTUAL_WIDTH / 2 - 150, VIRTUAL_HEIGHT - 560, 200, 100);
        rollKeyBounds = new Rectangle(VIRTUAL_WIDTH / 2 - 150, VIRTUAL_HEIGHT - 640, 200, 100);
        potionKeyBounds = new Rectangle(VIRTUAL_WIDTH / 2 - 150, VIRTUAL_HEIGHT - 720, 200, 100);
    }

    @Override
    public void render(float delta) {
        if (fadeAlpha > 0) fadeAlpha = Math.max(0, fadeAlpha - delta * 2f);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        game.batch.setProjectionMatrix(camera.combined);
        Vector2 mousePos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        game.batch.begin();

        game.batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        game.batch.draw(backgroundGUI, VIRTUAL_WIDTH / 2 - backgroundGUI.getWidth() / 1.4f, VIRTUAL_HEIGHT / 2 - backgroundGUI.getHeight() / 1.4f, 600, 800);
        UIHelper.drawButton(game.batch, buttonTexture, backButtonBounds, UIHelper.isHovered(backButtonBounds, mousePos));

        titleFont.draw(game.batch, "Options", VIRTUAL_WIDTH / 2 - 150, VIRTUAL_HEIGHT - 180);
        font.draw(game.batch, "Back", backButtonBounds.x + 170, backButtonBounds.y + 90);

        font.draw(game.batch, "Left: " + Input.Keys.toString(InputManager.getKey("leftKey")), leftKeyBounds.x, leftKeyBounds.y + 50);
        font.draw(game.batch, "Right: " + Input.Keys.toString(InputManager.getKey("rightKey")), rightKeyBounds.x, rightKeyBounds.y + 50);
        font.draw(game.batch, "Jump: " + Input.Keys.toString(InputManager.getKey("jumpKey")), jumpKeyBounds.x, jumpKeyBounds.y + 50);
        font.draw(game.batch, "Attack: " + Input.Keys.toString(InputManager.getKey("attackKey")), attackKeyBounds.x, attackKeyBounds.y + 50);
        font.draw(game.batch, "Roll: " + Input.Keys.toString(InputManager.getKey("rollKey")), rollKeyBounds.x, rollKeyBounds.y + 50);
        font.draw(game.batch, "Potion: " + Input.Keys.toString(InputManager.getKey("potionKey")), potionKeyBounds.x, potionKeyBounds.y + 50);

        UIHelper.drawFadeOverlay(game.batch, fadeAlpha, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        game.batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            if (backButtonBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                game.setScreen(new MainMenuScreen(game));
            } else if (leftKeyBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                waitingForNewKey = true;
                keyToRemap = "leftKey";
            } else if (rightKeyBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                waitingForNewKey = true;
                keyToRemap = "rightKey";
            } else if (jumpKeyBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                waitingForNewKey = true;
                keyToRemap = "jumpKey";
            } else if (attackKeyBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                waitingForNewKey = true;
                keyToRemap = "attackKey";
            } else if (rollKeyBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                waitingForNewKey = true;
                keyToRemap = "rollKey";
            } else if (potionKeyBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                waitingForNewKey = true;
                keyToRemap = "potionKey";
            }
        }
        if (waitingForNewKey) {
            for (int key = 0; key < 256; key++) {
                if (Gdx.input.isKeyJustPressed(key)) {
                    InputManager.setKey(keyToRemap, key);
                    waitingForNewKey = false;
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        font.dispose();
        titleFont.dispose();
        backgroundTexture.dispose();
        buttonTexture.dispose();
        backgroundGUI.dispose();
    }
}
