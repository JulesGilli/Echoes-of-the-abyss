package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Shop {
    private List<Item> items = new ArrayList<>(new Weapon( ));
    private Map<Item, Integer> priceListAvailableItem;
    private GameStat gameStat;

    public Shop(List<Item> itemsAvailable, GameStat gameStat) {
        this.priceList = new HashMap<>();
        this.gameStat = gameStat;
        for (Item item : items) {
            if (isAvailable(item)) {
                priceList.put(item, item.gold);
            }
        }

    }

    public boolean isAvailable(Item item){
        return true;
    }

    public boolean buyItem(Player player, Item item) {
        int price = priceListAvailableItem.get(item);
        if (player.getGold() >= price) {
            player.setGold(player.getGold() - price);
            return true;
        }
        return false;
    }

    public Map<Item, Integer> getPriceList() {
        return priceList;
    }

    public List<Item> getItemsAvailable() {
        return itemsAvailable;
    }
}
