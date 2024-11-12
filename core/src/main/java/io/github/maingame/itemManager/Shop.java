package io.github.maingame.itemManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Shop {
    private List<Item> itemsAvailable;
    private Map<Item, Integer> priceList;
    private GameStat gameStat;

    public Shop(List<Item> itemsAvailable, GameStat gameStat) {
        this.itemsAvailable = itemsAvailable;
        this.priceList = new HashMap<>();
        this.gameStat = gameStat;
        for (Item item : itemsAvailable) {
            priceList.put(item, item.gold);
        }
    }

    public boolean buyItem(Player player, Item item) {
        int price = priceList.get(item);
        if (player.getGold() >= price) {
            player.setGold(player.getGold() - price);
            return true;
        }
        return false;
    }

    public void render(SpriteBatch batch) {

    }
}
