package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.game.GameProperty;
import us.rjks.utils.*;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 19.04.2021 / 18:57
 *
 **************************************************************************/

public class LobbyItems implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (GameManager.getGame().equals(Game.LOBBY)) {
            event.setCancelled(true);
            if (event.getPlayer().getItemInHand() == null || event.getPlayer().getItemInHand().getItemMeta() == null) return;

            if (event.getPlayer().getItemInHand().getType().equals(Config.getItemStack("lobby_items_team_select").getType())) {
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.NOTE_BASS, 20L, 20L);
                Inventory.openTeamSelect(event.getPlayer());
            }
            if (event.getPlayer().getItemInHand().getType().equals(Config.getItemStack("lobby_items_map_voting").getType())) {
                Inventory.openMapVoting(event.getPlayer());
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.NOTE_BASS, 20L, 20L);
            }
            if (event.getPlayer().getItemInHand().getType().equals(Config.getItemStack("lobby_items_back_to_lobby").getType())) {
                event.getPlayer().kickPlayer(Config.getMessageAsString("back_to_lobby_kick_msg"));
            }
        } else if (GameManager.getGame().equals(Game.END)) {
            if (event.getPlayer().getItemInHand().getType().equals(Material.FIREBALL)) {
                event.getPlayer().kickPlayer(Config.getMessageAsString("back_to_lobby_kick_msg"));
            }
        } else if (GameManager.getGame().equals(Game.INGAME)) {
            if (event.getPlayer().getItemInHand().getType().equals(Material.FIREBALL)) {
                event.getPlayer().kickPlayer(Config.getMessageAsString("back_to_lobby_kick_msg"));
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (GameManager.getGame().equals(Game.LOBBY)) {
            event.setCancelled(true);

            Player player = ((Player) event.getWhoClicked());

            if ( event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getType() == null) return;

            if (event.getInventory().getName().equalsIgnoreCase(Config.getInventory("team_select_inv_name"))) {
                event.setCancelled(true);
                if (event.getCurrentItem().getType().equals(Material.LEATHER_CHESTPLATE)) {
                    try {
                        String name = "";
                        for (TeamManager.Team team : TeamManager.getTeams()) {
                            if (event.getCurrentItem().getItemMeta().getDisplayName().contains(team.getName())) {
                                name = team.getName();
                            }
                        }
                        if (!name.isEmpty()) {
                            TeamManager.removePlayerFromAllTeams(player);
                            TeamManager.addPlayerToTeam(player, name);

                            TeamManager.Team team = TeamManager.getTeamFromPlayer(player);

                            player.sendMessage(Config.getMessageAsString("team_selected_successfully")
                                    .replaceAll("%team_color%", team.getColor())
                                    .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                                    .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                                    .replaceAll("%team_prefix%", team.getPrefix())
                                    .replaceAll("%team_suffix%", team.getSuffix())
                                    .replaceAll("%team_name%", team.getName()));

                            player.closeInventory();
                            player.playSound(event.getWhoClicked().getLocation(), Sound.NOTE_BASS, 20L, 20L);

                            for (Player all : Bukkit.getOnlinePlayers()) {
                                ScoreBoardManager.setScoreboard(all);
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            } else if (event.getInventory().getName().equalsIgnoreCase(Config.getInventory("map_select_inv_name"))) {
                event.setCancelled(true);
                try {
                    String name = "";
                    for (MapManager.Map map : MapManager.getMaps()) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().contains(map.getName())) {
                            name = map.getName();
                        }
                    }
                    if (!name.isEmpty()) {
                        if (!GameManager.isForcemaped()) {
                            if (GameManager.getCountdown().getCountdown() > 6) {
                                MapManager.Map map = MapManager.getMapFromName(name);

                                GameProperty.manager.getProperty("voting").removePlayer(player);
                                GameProperty.manager.getProperty("voting").setPlayer(player, map.getName());

                                player.sendMessage(Config.getMessageAsString("lobby_player_voted")
                                    .replaceAll("%mapname%", name));

                                player.closeInventory();
                                player.playSound(event.getWhoClicked().getLocation(), Sound.NOTE_BASS, 20L, 20L);
                            } else {
                                player.sendMessage(Config.getMessageAsString("map_is_already_selected")
                                    .replaceAll("%mapname%", name));
                            }
                        } else {
                            player.sendMessage(Config.getMessageAsString("map_is_already_selected")
                                    .replaceAll("%mapname%", name));
                        }
                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
