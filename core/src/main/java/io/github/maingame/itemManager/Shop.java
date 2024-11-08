package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Shop {
    private List<Item> itemsAvailable;
    private Map<Item, Integer> priceList;

    public Shop(List<Item> itemsAvailable) {
        this.itemsAvailable = itemsAvailable;
        this.priceList = new HashMap<>();
        for (Item item : itemsAvailable) {
            priceList.put(item, item.gold);
        }
    }

    public boolean buyItem(Player player, Item item) {
        int price = priceList.get(item);
        if (player.getGold() >= price) {
            player.getGold() -= price;

            return true;
        }
        return false;
    }
}
