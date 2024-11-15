package io.github.maingame.sceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.maingame.Main;
import io.github.maingame.Platform;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

public class MainMenuScreen extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont titleFont;
    private final Texture backgroundTexture;
    private final Texture buttonTexture;
    private final Rectangle playButtonBounds;
    private final Rectangle quitButtonBounds;
    private final Rectangle optionButtonBounds;

    public MainMenuScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();

        backgroundTexture = new Texture(Gdx.files.internal("assets/backGroundMainMenu.png"));
        buttonTexture = new Texture(Gdx.files.internal("assets/buttonMenu.png"));

        initFonts();

        float buttonWidth = 600;
        float buttonHeight = 200;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        playButtonBounds = new Rectangle((screenWidth - buttonWidth) / 2, screenHeight / 2, buttonWidth, buttonHeight);
        optionButtonBounds = new Rectangle((screenWidth - buttonWidth) / 2, screenHeight / 2 - 150, buttonWidth, buttonHeight);
        quitButtonBounds = new Rectangle((screenWidth - buttonWidth) / 2, screenHeight / 2 - 300, buttonWidth, buttonHeight);
        System.out.println("Main Menu");
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Jacquard12-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 64;
        font = generator.generateFont(parameter);
        font.setColor(Color.BROWN);

        parameter.size = 96;
        titleFont = generator.generateFont(parameter);
        titleFont.setColor(Color.LIGHT_GRAY);

        generator.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(buttonTexture, playButtonBounds.x, playButtonBounds.y, playButtonBounds.width, playButtonBounds.height);
        batch.draw(buttonTexture, quitButtonBounds.x, quitButtonBounds.y, quitButtonBounds.width, quitButtonBounds.height);
        batch.draw(buttonTexture, optionButtonBounds.x, optionButtonBounds.y, optionButtonBounds.width, optionButtonBounds.height);

        font.draw(batch, "Play", playButtonBounds.x + 250, playButtonBounds.y + 125);
        font.draw(batch, "Quit", quitButtonBounds.x + 250 , quitButtonBounds.y + 125);
        font.draw(batch, "Option", optionButtonBounds.x + 220 , optionButtonBounds.y + 125);
        titleFont.draw(batch, "Echoes of the Abyss", 600, 800);

        batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

            if (playButtonBounds.contains(clickPosition)) {
                GameStat stat = new GameStat();
                Player player = new Player(new Vector2(100, 100), Platform.getPlatforms(),
                    Input.Keys.A, Input.Keys.D, Input.Keys.SPACE,
                    Input.Keys.F, Input.Keys.SHIFT_LEFT,Input.Keys.E);
                stat.loadGame();
                game.setScreen(new GameScreen(game, stat, player));
            }


            if (optionButtonBounds.contains(clickPosition)) {
                game.setScreen(new OptionsScreen(game));
            }

            if (quitButtonBounds.contains(clickPosition)) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        titleFont.dispose();
        backgroundTexture.dispose();
        buttonTexture.dispose();
    }
}
