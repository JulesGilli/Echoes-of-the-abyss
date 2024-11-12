package io.github.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ShopScene extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture background;
    private final Texture shop;
    private BitmapFont font;


    public ShopScene(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("assets/backGroundMainMenu.png"));
        shop = new Texture(Gdx.files.internal("assets/bookShop"));
        initFonts();
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Jacquard12-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        font = generator.generateFont(parameter);
        font.setColor(Color.BROWN);
        parameter.size = 96;
        generator.dispose();
    }

    public void render(SpriteBatch batch) {
        float size = 10;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(shop, 400, 600, 42*size, 58*size);
        batch.end();
    }

    public void dispose(){
        batch.dispose();
        background.dispose();
        font.dispose();
        shop.dispose();
    }
}
