package us.rjks.utils;

import org.bukkit.entity.Player;
import us.rjks.game.Game;
import us.rjks.game.GameManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 21.04.2021 / 10:04
 *
 **************************************************************************/

public class Spectator {

    public static void setSpectator(Player player) {
        GameManager.addSpectatorPlayer(player);
        if (GameManager.getCurrentMap().getLocation("spectator") != null) {
            player.teleport(GameManager.getCurrentMap().getLocation("spectator"));
        }
        player.sendMessage(Config.getMessageAsString("spectator_joined_chat"));
        player.setAllowFlight(true);
        player.setFlying(true);

        player.setFoodLevel(20);
        player.setHealth(20);
        player.spigot().setCollidesWithEntities(false);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        Inventory.loadSpectatoorInv(player);

        for (Player all : GameManager.getSpectatorPlayers()) {
            all.showPlayer(player);
            player.showPlayer(all);
        }

        for (Player all : GameManager.getIngamePlayer()) {
            all.hidePlayer(player);
            player.showPlayer(all);
        }
    }

    public static void removeSpectator(Player player) {
        GameManager.removeSpectatorPlayer(player);
        player.setAllowFlight(false);
        player.setFlying(false);

        player.setFoodLevel(20);
        player.setHealth(20);
        player.spigot().setCollidesWithEntities(false);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }

}
