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
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.maingame.core.GameStat;
import io.github.maingame.core.Main;
import io.github.maingame.entities.Player;
import io.github.maingame.input.PlayerInputHandler;
import io.github.maingame.utils.Platform;
import io.github.maingame.utils.SoundManager;

import static io.github.maingame.core.Main.VIRTUAL_HEIGHT;
import static io.github.maingame.core.Main.VIRTUAL_WIDTH;

public class MainMenuScreen extends ScreenAdapter {
    private final Main game;
    private final GameStat stat;
    private final Texture backgroundTexture;
    private final Texture buttonTexture;
    private final Rectangle playButtonBounds;
    private final Rectangle quitButtonBounds;
    private final Rectangle optionButtonBounds;
    private final Rectangle shopButtonBounds;
    private Player player = null;
    private BitmapFont font;
    private BitmapFont titleFont;
    private final OrthographicCamera camera;
    private final FitViewport viewport;

    private SoundManager soundManager;

    public MainMenuScreen(Main game) {
        this.game = game;
        stat = new GameStat();

        this.soundManager = game.getSoundManager();

        player = new Player(new Vector2(100, 100), Platform.getPlatforms(), soundManager);

        PlayerInputHandler inputHandler = new PlayerInputHandler(player);
        player.setInputHandler(inputHandler);

        stat.loadGame();

        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/background_menuscreen.png"));
        buttonTexture = new Texture(Gdx.files.internal("GUI/button_basic.png"));

        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply(true);

        initFonts();

        float buttonWidth = 600;
        float buttonHeight = 200;

        playButtonBounds = new Rectangle((VIRTUAL_WIDTH - buttonWidth) / 2, VIRTUAL_HEIGHT / 2 + 150, buttonWidth, buttonHeight);
        shopButtonBounds = new Rectangle((VIRTUAL_WIDTH - buttonWidth) / 2, VIRTUAL_HEIGHT / 2, buttonWidth, buttonHeight);
        optionButtonBounds = new Rectangle((VIRTUAL_WIDTH - buttonWidth) / 2, VIRTUAL_HEIGHT / 2 - 150, buttonWidth, buttonHeight);
        quitButtonBounds = new Rectangle((VIRTUAL_WIDTH - buttonWidth) / 2, VIRTUAL_HEIGHT / 2 - 300, buttonWidth, buttonHeight);

        game.getSoundManager().playMusic("menu", true, 0.3f);
    }

    private void initFonts() {
        font = FontManager.getFont(64);
        font.setColor(Color.BROWN);

        titleFont = FontManager.getFont(96);
        titleFont.setColor(Color.LIGHT_GRAY);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        game.batch.draw(buttonTexture, playButtonBounds.x, playButtonBounds.y, playButtonBounds.width, playButtonBounds.height);
        game.batch.draw(buttonTexture, quitButtonBounds.x, quitButtonBounds.y, quitButtonBounds.width, quitButtonBounds.height);
        game.batch.draw(buttonTexture, optionButtonBounds.x, optionButtonBounds.y, optionButtonBounds.width, optionButtonBounds.height);
        game.batch.draw(buttonTexture, shopButtonBounds.x, shopButtonBounds.y, shopButtonBounds.width, shopButtonBounds.height);
        font.draw(game.batch, "Play", playButtonBounds.x + 250, playButtonBounds.y + 125);
        font.draw(game.batch, "Shop", shopButtonBounds.x + 235, shopButtonBounds.y + 125);
        font.draw(game.batch, "Option", optionButtonBounds.x + 220, optionButtonBounds.y + 125);
        font.draw(game.batch, "Quit", quitButtonBounds.x + 240, quitButtonBounds.y + 125);
        titleFont.draw(game.batch, "Echoes of the Abyss", 600, 950);

        game.batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            if (playButtonBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                game.getSoundManager().stopMusic("menu");
                game.setScreen(new GameScreen(game, stat, player));
            }

            if (optionButtonBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                game.setScreen(new OptionsScreen(game));
            }

            if (shopButtonBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                game.setScreen(new ShopScreen(game, stat, player));
            }

            if (quitButtonBounds.contains(clickPosition)) {
                game.getSoundManager().playSound("select");
                game.getSoundManager().stopMusic("menu");
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        if (font != null) font.dispose();
        if (titleFont != null) titleFont.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (buttonTexture != null) buttonTexture.dispose();
    }
}
