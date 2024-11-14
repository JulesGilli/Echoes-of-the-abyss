package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Shop {
    private final List<Item> items = new ArrayList<>();
    private GameStat gameStat;

    public Shop(List<Item> itemsAvailable, GameStat gameStat) {
        for (int i = 1; i < 6; i++){
            items.add(new Weapon(i));
        }
        for (int i = 1; i < 4; i++){
            items.add(new Armor(i));
        }
        items.add(new SpeedPotion(1));
        for (int i = 1; i < 4; i++){
            items.add(new Weapon(i));
        }
    }

    public boolean isAvailable(Item item){
        return true;
    }

    public boolean buyItem(Player player, Item item) {
        if (isAvailable(item) && player.getGold() >= item.gold) {
            player.setGold(player.getGold() - item.gold);
                return true;
        }
        return false;
    }

    public List<Item> getItems() {
        return items;
    }

}
