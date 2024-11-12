package io.github.maingame.sceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private final Rectangle backButtonBounds;

    public OptionsScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();

        backgroundTexture = new Texture(Gdx.files.internal("assets/backGroundMainMenu.png"));
        buttonTexture = new Texture(Gdx.files.internal("assets/buttonMenu.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Jacquard12-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 56;
        font = generator.generateFont(parameter);
        font.setColor(Color.BROWN);

        parameter.size = 96;
        titleFont = generator.generateFont(parameter);
        titleFont.setColor(Color.LIGHT_GRAY);

        generator.dispose();


        float buttonWidth = 450;
        float buttonHeight = 150;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        backButtonBounds = new Rectangle((screenWidth - buttonWidth) / 2, 100, buttonWidth, buttonHeight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(buttonTexture, backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);

        titleFont.draw(batch, "Options", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() - 50);
        font.draw(batch, "Back", backButtonBounds.x + 170, backButtonBounds.y + 90);


        batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

            if (backButtonBounds.contains(clickPosition)) {
                game.setScreen(new MainMenuScreen(game));
            }

        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundTexture.dispose();
        buttonTexture.dispose();
    }
}
