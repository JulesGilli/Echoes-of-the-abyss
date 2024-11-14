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
import io.github.maingame.characterManager.Player;
import io.github.maingame.itemManager.Inventory;
import io.github.maingame.itemManager.Item;
import io.github.maingame.itemManager.Shop;
import io.github.maingame.utilsManager.GameStat;
import io.github.maingame.Main;

import java.util.ArrayList;
import java.util.List;


public class ShopScreen extends ScreenAdapter {
    private final Main game;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;
    private final Texture shopTexture;
    private final float shopSize = 1.2F;
    private BitmapFont font;
    private final Shop shop;
    private final List<Item> items;
    private final GameStat stat;
    private final int buttonWidth = 600;
    private final int buttonHeight = 200;
    private final int itemCardWidth = 200;
    private final int itemCardHeight = 200;
    private final float shopWidth;
    private final float shopHeight;
    private final int screenWidth = Gdx.graphics.getWidth();
    private final int screenHeight = Gdx.graphics.getHeight();
    private final float centerShopWidth;
    private final float centerShopHeight;
    private final Texture buttonTexture;
    private final com.badlogic.gdx.math.Rectangle playButtonBounds;
    private final com.badlogic.gdx.math.Rectangle quitButtonBounds;

    public ShopScreen(Main game, GameStat stat) {
        this.game = game;
        this.stat = stat;
        this.batch = new SpriteBatch();
        this.shop = new Shop(new Inventory(), stat);
        this.items = shop.getItems();
        stat.setGolds(400);
        shopWidth = 876 * shopSize;
        shopHeight = 641 * shopSize;
        centerShopWidth = screenWidth / 2f - shopWidth / 2f;
        centerShopHeight = screenHeight / 2f - shopHeight / 2f;
        backgroundTexture = new Texture(Gdx.files.internal("assets/backGroundMainMenu.png"));
        shopTexture = new Texture(Gdx.files.internal("assets/shop.png"));
        initFonts();
        buttonTexture = new Texture(Gdx.files.internal("assets/buttonMenu.png"));
        playButtonBounds = new com.badlogic.gdx.math.Rectangle((screenWidth - buttonWidth ) / 2 - buttonWidth/2, screenHeight - shopHeight  - 250, buttonWidth, buttonHeight);
        quitButtonBounds = new com.badlogic.gdx.math.Rectangle((screenWidth - buttonWidth) / 2 + buttonWidth/2, screenHeight - shopHeight - 250, buttonWidth, buttonHeight);
    }

    public Vector2 getItemAssetPosition(int number){
        return new Vector2(centerShopWidth + 180 + number % 4 * 200, centerShopHeight + 728 - number / 4 * 200);
    }

    public Vector2 getItemGoldPosition(int number){
        return new Vector2(centerShopWidth + 200 + number % 4 * 200, centerShopHeight + 628 - number / 4 * 200);
    }

    public Rectangle drawItem(int number){
        if (shop.isAvailable(items.get(number)))
        {
            batch.draw(items.get(number).getTextureAvailable(), getItemAssetPosition(number).x + 10, getItemAssetPosition(number).y - 67, 75, 75);

        } else {
            batch.draw(items.get(number).getTextureDisabled(), getItemAssetPosition(number).x + 10, getItemAssetPosition(number).y - 67, 75, 75);
        }
        if (shop.getInventory().inInventory(items.get(number))){
            font.draw(batch,"bought", getItemGoldPosition(number).x - 50, getItemGoldPosition(number).y);

        } else{
            font.draw(batch, items.get(number).getStrGold(), getItemGoldPosition(number).x, getItemGoldPosition(number).y);
        }
        Rectangle rectangle = new Rectangle(getItemGoldPosition(0).x - 50 + number % 4 * 200, getItemGoldPosition(0).y - 50 - number / 4 * 200 , 200 , 200);
        return rectangle;
    }

    public List<Rectangle> createButtons(){
        List<Rectangle> buttons = new ArrayList<>();
        for (int i = 0; i < 12; i++){
            buttons.add(drawItem(i));
        }
        return buttons;
    }

    public void input( List<Rectangle> listButtons){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (playButtonBounds.contains(clickPosition)) {
                game.setScreen(new GameScreen(game));
            }
            if (quitButtonBounds.contains(clickPosition)) {
                Gdx.app.exit();
            }
            for (int i = 0; i < 12; i++){
                if (listButtons.get(i).contains(clickPosition)) {
                    if (shop.buyItem(stat , items.get(i)))
                    {
                        System.out.println("buy Item :"+i);
                    }
                }
            }
        }
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Jacquard12-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);
        font.setColor(Color.BROWN);
        parameter.size = 96;
        generator.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, screenWidth, screenHeight);
        batch.draw(shopTexture, centerShopWidth + 40, centerShopHeight +150 , shopWidth, shopHeight);
        batch.draw(buttonTexture, playButtonBounds.x, playButtonBounds.y, playButtonBounds.width, playButtonBounds.height);
        batch.draw(buttonTexture, quitButtonBounds.x, quitButtonBounds.y, quitButtonBounds.width, quitButtonBounds.height);
        font.draw(batch, "Play", playButtonBounds.x + 250, playButtonBounds.y + 125);
        font.draw(batch, "Quit", quitButtonBounds.x + 250 , quitButtonBounds.y + 125);
        font.draw(batch, "Shop", screenWidth/ 2 - 48, screenHeight - 48);
        font.draw(batch, Integer.toString(stat.getGolds()),centerShopWidth + 960, centerShopHeight + 790);
        List<Rectangle> listButtons= createButtons();
        input(listButtons);
        batch.end();
    }

    public void dispose(){
        batch.dispose();
        shopTexture.dispose();
        backgroundTexture.dispose();
        font.dispose();
    }
}
