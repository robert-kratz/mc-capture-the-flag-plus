package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import us.rjks.game.GameProperty;

import java.util.ArrayList;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 19.04.2021 / 18:52
 *
 **************************************************************************/

public class Inventory {

    public static void loadLobbyItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setHeldItemSlot(0);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.getInventory().setItem((int)Config.getItem("lobby_items_team_select_pos"), Config.getItemStack("lobby_items_team_select"));
        player.getInventory().setItem((int)Config.getItem("lobby_items_map_voting_pos"), Config.getItemStack("lobby_items_map_voting"));
        player.getInventory().setItem((int)Config.getItem("lobby_items_back_to_lobby_pos"), Config.getItemStack("lobby_items_back_to_lobby"));
    }

    public static void loadSpectatoorInv(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setHeldItemSlot(0);

        player.getInventory().setItem((int)Config.getItem("spectator_items_player_overview_pos"), Config.getItemStack("spectator_items_player_overview"));
        player.getInventory().setItem((int)Config.getItem("spectator_items_back_to_lobby_pos"), Config.getItemStack("spectator_items_back_to_lobby"));
    }

    public static void openTeamSelect(Player player) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, getInvSizeFromElementAmount(TeamManager.getTeams().size()), Config.getInventory("team_select_inv_name").toString());

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, Config.getItemStack("inv_place_holder"));
        }
        int i = 9;
        for (TeamManager.Team team : TeamManager.getTeams()) {
            ItemBuilder builder = new ItemBuilder(Material.LEATHER_CHESTPLATE, Config.getInventory("team_select_item_name").toString()
                    .replaceAll("%team_color%", team.getColor())
                    .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                    .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                    .replaceAll("%team_prefix%", team.getPrefix())
                    .replaceAll("%team_suffix%", team.getSuffix())
                    .replaceAll("%team_name%", team.getName()));
            ArrayList lore = new ArrayList();
            for (TeamManager.TeamPlayer teamPlayer : team.getPlayers()) {
                lore.add(Config.getInventory("team_select_item_lore_format")
                        .replaceAll("%player_name%", teamPlayer.getPlayer().getName()));
            }
            builder.setLore(lore);
            builder.addItemFlag(ItemFlag.HIDE_ENCHANTS);

            ItemStack stack = builder.checkout();

            if (TeamManager.getTeamFromPlayer(player) != null && TeamManager.getTeamFromPlayer(player).getName().equalsIgnoreCase(team.getName())) {
                stack.addUnsafeEnchantment(Enchantment.LUCK, 1);
            }

            inv.setItem(i, stack);
            i++;
        }

        inv.setItem(4, Config.getItemStack("lobby_items_team_select"));

        player.openInventory(inv);
    }

    public static void openMapVoting(Player player) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, getInvSizeFromElementAmount(MapManager.getMaps().size()), Config.getInventory("map_select_inv_name").toString());

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, Config.getItemStack("inv_place_holder"));
        }

        inv.setItem(4, Config.getItemStack("lobby_items_map_voting"));

        int i = 9;
        for (MapManager.Map map : MapManager.getMaps()) {
            ItemBuilder builder = new ItemBuilder(map.getIcon().getType(), Config.getInventory("map_select_item_name")
                    .replaceAll("%mapname%", map.getName()));
            ArrayList lore = new ArrayList();

            lore.add(Config.getInventory("map_select_item_lore_format")
                    .replaceAll("%votes%", String.valueOf(GameProperty.manager.getProperty("voting").getAmoutOfKey(map.getName()))));
            builder.setLore(lore);
            builder.addItemFlag(ItemFlag.HIDE_ENCHANTS);

            ItemStack stack = builder.checkout();

            if (GameProperty.manager.getProperty("voting").playerExists(player) && GameProperty.manager.getProperty("voting").getPlayerValue(player).toString().equalsIgnoreCase(map.getName())) {
                stack.addUnsafeEnchantment(Enchantment.LUCK, 1);
            }

            inv.setItem(i, stack);
            i++;
        }

        player.openInventory(inv);
    }

    public static int getInvSizeFromElementAmount(int i) {
        if (i < 9) {
            return 9*2;
        } else if (i < 18) {
            return 9*3;
        } else if (i < 27) {
            return 9*4;
        } else if (i < 36) {
            return 9*5;
        } else if (i < 45) {
            return 9*6;
        } else if (i < 54) {
            return 9*7;
        }
        return 9*2;
    }
}
