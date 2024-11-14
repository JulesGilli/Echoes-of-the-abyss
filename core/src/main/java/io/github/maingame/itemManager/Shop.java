package io.github.maingame.itemManager;

import com.badlogic.gdx.Game;
import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;
import java.util.ArrayList;
import java.util.List;


public class Shop {
    private final List<Item> items = new ArrayList<>();
    private GameStat gameStat;
    private Inventory inventory;

    public Shop(Inventory inventory, GameStat gameStat) {
        for (int i = 1; i < 6; i++){
            items.add(new Weapon(i));
        }
        for (int i = 1; i < 4; i++){
            items.add(new Armor(i));
        }
        items.add(new SpeedPotion(1));
        items.add(new HealPotion(2));
        items.add(new StrenghtPotion(3));
        items.add(new ArmorPotion(1));
        this.gameStat = gameStat;
        this.inventory = inventory;
    }

    public boolean isAvailable(Item item){
        if (gameStat.getGolds() < item.getGold())
            return false;
        if (item instanceof Weapon){
            return !inventory.containWeapon();
        }
        if (item instanceof Armor){
            return !inventory.containArmor();
        }
        if (item instanceof Consumable){
            return !inventory.containConsumable();
        }
        return true;
    }

    public boolean buyItem(GameStat stat, Item item) {
        if (isAvailable(item) && stat.getGolds() >= item.gold) {
            stat.setGolds(stat.getGolds() - item.gold);
            inventory.addItem(item);
                return true;
        }
        return false;
    }

    public List<Item> getItems() {
        return items;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
