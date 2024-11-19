package io.github.maingame.itemManager;

import io.github.maingame.characterManager.Player;
import io.github.maingame.utilsManager.GameStat;

import java.util.ArrayList;
import java.util.List;


public class Shop {
    private final List<Item> items = new ArrayList<>();
    private final Player player;
    private GameStat gameStat;

    public Shop(GameStat gameStat, Player player) {
        this.player = player;
        this.gameStat = gameStat;

        for (int i = 1; i < 6; i++) {
            items.add(new Weapon(i));
        }
        for (int i = 1; i < 4; i++) {
            items.add(new Armor(i));
        }
        items.add(new SpeedPotion(gameStat));
        items.add(new HealPotion(gameStat));
        items.add(new StrengthPotion(gameStat));
        items.add(new ArmorPotion(gameStat));
        this.gameStat = gameStat;
    }

    public boolean isAvailable(Item item) {
        if (gameStat.getGolds() < item.getGold())
            return false;
        if (item instanceof Weapon) {
            return !player.getInventory().containWeapon();
        }
        if (item instanceof Armor) {
            return !player.getInventory().containArmor();
        }
        if (item instanceof Consumable) {
            return !player.getInventory().containConsumable();
        }
        return true;
    }

    public boolean buyItem(GameStat stat, Item item) {
        if (player == null) {
            throw new IllegalStateException("Le joueur n'a pas été initialisé correctement.");
        }

        if (item.isUnlocked(stat) && isAvailable(item)) {
            stat.setGolds(stat.getGolds() - item.gold);
            player.getInventory().addItem(item);
            System.out.println("item acheté");
            return true;
        } else {
            System.out.println("achat impossible");
            return false;
        }
    }

    public List<Item> getItems() {
        return items;
    }
}
