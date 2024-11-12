package io.github.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import io.github.maingame.itemManager.Item;
import io.github.maingame.itemManager.Shop;
import io.github.maingame.itemManager.SpeedPotion;
import io.github.maingame.utilsManager.GameStat;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShopScreen extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private final Texture shopTexture;
    private final Texture buttonTexture;
    private final int shopSize = 25;
    private BitmapFont font;
    private Shop shop;
    private GameStat stat;
    private final int buttonWidth = 600;
    private final int buttonHeight = 200;
    private final int screenWidth = Gdx.graphics.getWidth();
    private final int screenHeight = Gdx.graphics.getHeight();

    public ShopScreen(Main game, GameStat stat) {
        this.game = game;
        this.batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("assets/backGroundMainMenu.png"));
        shopTexture = new Texture(Gdx.files.internal("assets/bookShop.png"));
        initFonts();
        buttonTexture = new Texture(Gdx.files.internal("assets/buttonMenu.png"));
        float buttonWidth = 600;
        float buttonHeight = 200;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

    }


    public List<Rectangle> createItemContainer(){
        List<Rectangle> listItemContainer = new ArrayList<>();
        int i = 0;
        for (Item item: shop.getItemsAvailable()) {
            createButton(0);
            i++;
        }
        return listItemContainer;
    }

    public Rectangle createButton(int number){
        int width = (screenWidth - buttonWidth) / 2;
        int height = (buttonHeight - buttonHeight) / 2;
        int margin = buttonWidth + 20;
        return new Rectangle(width - margin * 20, height, buttonWidth, buttonHeight);
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

    @Override
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
