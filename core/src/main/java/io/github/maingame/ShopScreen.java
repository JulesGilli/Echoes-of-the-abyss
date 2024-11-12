package io.github.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShopScreen extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private final Texture shopTexture;
    private final int shopSize = 25;
    //private BitmapFont font;


    public ShopScreen(Main game) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.game = game;
        this.batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("assets/backGroundMainMenu.png"));
        shopTexture = new Texture(Gdx.files.internal("assets/bookShop.png"));
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float shopWidth = 42 * shopSize;
        float shopHeight = 58 * shopSize;
        float centerShopWidth = Gdx.graphics.getWidth()/2 - shopWidth/2;
        float centerShopHeight = Gdx.graphics.getHeight()/2 - shopHeight/2;
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(shopTexture, centerShopWidth,centerShopHeight, 42 * shopSize, 58 * shopSize);
        batch.end();
    }

    public void dispose(){
        batch.dispose();
        shopTexture.dispose();
    }
}
