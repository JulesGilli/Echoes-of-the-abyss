package io.github.maingame.sceneManager;

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
import io.github.maingame.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ShopScreen extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private final Texture shopTexture;
    private final float shopSize = 1.2F;
    private BitmapFont font;
    private final Shop shop;
    private final GameStat stat;
    private final int buttonWidth = 600;
    private final int buttonHeight = 200;
    private final float shopWidth;
    private final float shopHeight;
    private final int screenWidth = Gdx.graphics.getWidth();
    private final int screenHeight = Gdx.graphics.getHeight();
    private final Texture buttonTexture;
    private final com.badlogic.gdx.math.Rectangle playButtonBounds;
    private final com.badlogic.gdx.math.Rectangle quitButtonBounds;

    public ShopScreen(Main game, GameStat stat) {
        this.game = game;
        this.stat = stat;
        this.batch = new SpriteBatch();
        this.shop = new Shop(new ArrayList<>(), stat);
        shopWidth = 876 * shopSize;
        shopHeight = 641 * shopSize;
        backgroundTexture = new Texture(Gdx.files.internal("assets/backGroundMainMenu.png"));
        shopTexture = new Texture(Gdx.files.internal("assets/shop.png"));
        initFonts();
        buttonTexture = new Texture(Gdx.files.internal("assets/buttonMenu.png"));
        playButtonBounds = new com.badlogic.gdx.math.Rectangle((screenWidth - buttonWidth ) / 2 - buttonWidth/2, screenHeight - shopHeight  - 250, buttonWidth, buttonHeight);
        quitButtonBounds = new com.badlogic.gdx.math.Rectangle((screenWidth - buttonWidth) / 2 + buttonWidth/2, screenHeight - shopHeight - 250, buttonWidth, buttonHeight);

    }


    public List<Rectangle> createItemContainer(){
        List<Rectangle> listItemContainer = new ArrayList<>();
        int yPosition = screenHeight / 2;
        int margin = 20;

        for (int i = 0; i < shop.getItemsAvailable().size(); i++) {
            Rectangle button = createButton(i, yPosition - i * (buttonHeight + margin));
            listItemContainer.add(button);
        }
        return listItemContainer;
    }

    public Rectangle createButton(int index, int yPosition) {
        int xPosition = (screenWidth - buttonWidth) / 2;
        return new Rectangle(xPosition, yPosition, buttonWidth, buttonHeight);
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
        float centerShopWidth = screenWidth / 2f - shopWidth / 2f;
        float centerShopHeight = screenHeight / 2f - shopHeight / 2f;

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, screenWidth, screenHeight);
        batch.draw(shopTexture, centerShopWidth + 40, centerShopHeight +150 , shopWidth, shopHeight);
        batch.draw(buttonTexture, playButtonBounds.x, playButtonBounds.y, playButtonBounds.width, playButtonBounds.height);
        batch.draw(buttonTexture, quitButtonBounds.x, quitButtonBounds.y, quitButtonBounds.width, quitButtonBounds.height);
        batch.end();
    }

    public void dispose(){
        batch.dispose();
        shopTexture.dispose();
        backgroundTexture.dispose();
        font.dispose();
    }
}
