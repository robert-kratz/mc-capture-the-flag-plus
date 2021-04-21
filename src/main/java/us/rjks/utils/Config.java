package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.game.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 18.04.2021 / 19:52
 *
 **************************************************************************/

public class Config {

    private static File logs = new File("plugins/" + Main.getPlugin().getName() + "/", "config.yml");
    private static YamlConfiguration locscfg = YamlConfiguration.loadConfiguration(logs);

    private static HashMap<String, Object> cache = new HashMap<>();

    public static Object getProperty(String key) {
        if (cache.containsKey(key)) return cache.get(key);
        if (locscfg.get(key) == null) return null;
        Object element = locscfg.get(key);
        cache.put(key, element);
        return element;
    }

    public static String getMessageAsString(String key) {
        if (cache.containsKey("msg." + key)) return transformMessage(cache.get("msg." + key).toString());
        if (locscfg.get("msg." + key) == null) {
            Bukkit.getConsoleSender().sendMessage("§cATTENTION: MAJOR ERROR WHILE COMPILING CONFIG.YML! COULD NOT FIND: " + "msg." + key);
            return null;
        }
        Object element = locscfg.get("msg." + key);
        cache.put("msg." + key, element);
        return transformMessage(element.toString());
    }

    public static Object getConfig(String key) {
        if (cache.containsKey("config." + key)) return cache.get("config." + key);
        if (locscfg.get("config." + key) == null) {
            Bukkit.getConsoleSender().sendMessage("§cATTENTION: MAJOR ERROR WHILE COMPILING CONFIG.YML! COULD NOT FIND: " + "config." + key);
            return null;
        }
        Object element = locscfg.get("config." + key);
        cache.put("config." + key, element);
        return element;
    }

    public static ItemStack getItemStack(String key) {
        if (cache.containsKey("item." + key)) {
            return (ItemStack) cache.get("item." + key);
        }
        if (locscfg.get("item." + key) == null) {
            Bukkit.getConsoleSender().sendMessage("§cATTENTION: MAJOR ERROR WHILE COMPILING CONFIG.YML! COULD NOT FIND: " + "item." + key);
            return null;
        }
        ItemStack element = (ItemStack) locscfg.get("item." + key);
        ItemMeta meta = element.getItemMeta();
        String display = transformMessage(meta.getDisplayName());
        meta.setDisplayName(display);
        if (meta.getLore() != null) {
            ArrayList<String> lore = new ArrayList<>();
            for (String lor : meta.getLore()) {
                lore.add(transformMessage(lor));
            }
            meta.setLore(lore);
        }
        element.setItemMeta(meta);
        cache.put("item." + key, element);
        return element;
    }

    public static Object getItem(String key) {
        if (cache.containsKey("item." + key)) {
            return cache.get("item." + key);
        }
        if (locscfg.get("item." + key) == null) {
            Bukkit.getConsoleSender().sendMessage("§cATTENTION: MAJOR ERROR WHILE COMPILING CONFIG.YML! COULD NOT FIND: " + "item." + key);
            return null;
        }
        Object element = locscfg.get("item." + key);
        cache.put("item." + key, element);
        return element;
    }

    public static String getInventory(String key) {
        if (cache.containsKey("inv." + key)) {
            return (String) cache.get("inv." + key);
        }
        if (locscfg.get("inv." + key) == null) {
            Bukkit.getConsoleSender().sendMessage("§cATTENTION: MAJOR ERROR WHILE COMPILING CONFIG.YML! COULD NOT FIND: " + "inv." + key);
            return null;
        }
        Object element = transformMessage((String) locscfg.get("inv." + key));
        cache.put("inv." + key, element);
        return element.toString();
    }

    public static String getConfigeAsString(String key) {
        if (cache.containsKey("config." + key)){
            return cache.get("config." + key).toString();
        }
        if (locscfg.get("config." + key) == null) {
            Bukkit.getConsoleSender().sendMessage("§cATTENTION: MAJOR ERROR WHILE COMPILING CONFIG.YML! COULD NOT FIND: " + "config." + key);
            return null;
        }
        String element = transformMessage((String) locscfg.get("config." + key));
        cache.put("config." + key, element);
        return element;
    }

    public static String getConfigAsString(String key) {
        if (cache.containsKey("config." + key)){
            return cache.get("config." + key).toString();
        }
        if (locscfg.get("config." + key) == null) {
            Bukkit.getConsoleSender().sendMessage("§cATTENTION: MAJOR ERROR WHILE COMPILING CONFIG.YML! COULD NOT FIND: " + "config." + key);
            return null;
        }
        String element = transformMessage((String) locscfg.get("config." + key));
        cache.put("config." + key, element);
        return element;
    }

    public static String getScoreBoardAsString(String key) {
        if (cache.containsKey("scoreboard." + key)){
            return cache.get("scoreboard." + key).toString();
        }
        if (locscfg.get("scoreboard." + key) == null) {
            Bukkit.getConsoleSender().sendMessage("§cATTENTION: MAJOR ERROR WHILE COMPILING CONFIG.YML! COULD NOT FIND: " + "scoreboard." + key);
            return null;
        }
        String element = transformMessage((String) locscfg.get("scoreboard." + key));
        cache.put("scoreboard." + key, element);
        return element;
    }

    public static void setProperty(String key, Object value) {
        if (cache.containsKey(key)) {
            cache.remove(key);
        }
        if (value instanceof String) {
            cache.put(key, transformMessage(value.toString()));
        } else if (value instanceof ItemStack){
            ItemStack element = (ItemStack) value;
            ItemMeta meta = element.getItemMeta();
            String display = transformMessage(meta.getDisplayName());
            meta.setDisplayName(display);
            if (meta.getLore() != null) {
                ArrayList<String> lore = new ArrayList<>();
                for (String lor : meta.getLore()) {
                    lore.add(transformMessage(lor));
                }
                meta.setLore(lore);
            }
            element.setItemMeta(meta);
            cache.put(key, element);
        }
        locscfg.set(key, value);
        save();
    }

    public static void loadDefaultConfig() {
        String config = "config.", message = "msg.", item = "item.", inventory = "inv.", scoreboard = "scoreboard.";

        setProperty(config + "show_prefix_in_tab", false);
        setProperty(config + "permission_forcemap", "system.forcemap");
        setProperty(config + "permission_start", "system.start");
        setProperty(config + "permission_premium_kick", "system.game.priority.join");
        setProperty(config + "permission_premium_kick_bypass", "system.game.priority.nokick");

        setProperty(config + "team_amount", 4);
        setProperty(config + "players_per_team", 2);
        setProperty(config + "min_players_start", 2);
        setProperty(config + "start_count_down_time", 30);
        setProperty(config + "end_count_down_time", 10);
        setProperty(config + "ingame_count_down_game_ends_in_minutes", 30);


        setProperty(message + "has_to_be_a_player", "&cYou have to be a player to execute this command.");
        setProperty(message + "no_permission", "&cYou dont have permission to execute this command.");
        setProperty(message + "minute_message", "minutes");

        setProperty(message + "map_does_not_exists", "&8» &cThis Map does not exists. (%mapname%)");
        setProperty(message + "map_is_already_selected", "&8» &cA Map is already selected. (%mapname%)");
        setProperty(message + "forcemap_selected_successfully", "&8» &aYou selected %mapname% as Map");

        setProperty(message + "game_start_successfully", "&8» &aYou started the Game");
        setProperty(message + "game_already_started", "&8» &cThe Game already started.");
        setProperty(message + "countdown_cant_be_startet_before_running", "&8» &cYou cannot start the Countdown before it started");
        setProperty(message + "help_command_start", "&8» &cUsage: /start");

        setProperty(message + "team_selected_successfully", "&8» &7You are now in Team %team_color%%team_name% &8[&e%team_size%&8|&e%team_max_size%&8]&7.");
        setProperty(message + "team_is_full", "&8» &cThis Team is Full");
        setProperty(message + "team_not_exists", "&8» &cTeam does not exists");
        setProperty(message + "help_command_team", "&8» &cUsage: /team [TEAM]");

        setProperty(message + "chat_format_default_color", "&7");
        setProperty(message + "chat_format_spectator", "&7Spectator &8| &7%player_name%&8: &f%player_message%");
        setProperty(message + "chat_format_team", "%team_prefix%%player_name%&8: &f%player_message%");
        setProperty(message + "chat_format_no_team_selected", "&7%player_name%&8: &f%player_message%");

        setProperty(message + "countdown_end_team_info", "&8» &aYou are now in Team %team_color%%team_name%&a.");
        setProperty(message + "countdown_end_kit_info", "&8» &7You selected the Kit: &e%kit_name%");
        setProperty(message + "end_countdown_ends_message", "&8» &7The Server is stopping.");

        setProperty(message + "lobby_join_message", "&8» &a%player_name% joined &8[&e%online_player%&8|&e%max_player%&8]");
        setProperty(message + "premium_player_kicked_user_kick_message", "&cA Player with a higher permission level joined.");
        setProperty(message + "nobody_found_to_premium_kick", "&cNobody was found to kick");
        setProperty(message + "game_already_ended", "&cThe Game already ended, the server will restart soon.");

        setProperty(message + "lobby_quit_message", "&8» &a%player_name% joined &8[&e%online_player%&8|&e%max_player%&8]");
        setProperty(message + "ingame_player_leaves_message", "&8» &c%player_name% left the game");
        setProperty(message + "game_ended", "&8» &cThe Game Ended");
        setProperty(message + "game_ended_winner_title", "%team_color%&l%team_name%");
        setProperty(message + "game_ended_winner_subtitle", "&7won the game");
        setProperty(message + "game_ended_winner_chat_message", "&8» &aTeam %team_color%%team_name% &awon the Game!");
        setProperty(message + "game_ended_winner_chat_message_line_two", "&8» &aWinners&8: &e%winner_list%");

        setProperty(message + "min_two_teams_required", "&8» &cThe Game requires at least two used teams");
        setProperty(message + "lobby_action_bar", "&8» &6The Game starts in &e%countdown% seconds");
        setProperty(message + "vote_ended_result", "&8» &7The Voting ended, winning Map: &e%mapname%&7.");
        setProperty(message + "lobby_countdown_chat", "&8» &7The Game begins in &e%countdown% seconds&7.");
        setProperty(message + "lobby_countdown_starts_now_chat", "&8» &6The Game starts &enow");
        setProperty(message + "lobby_countdown_starts_now_actionbar", "&8» &7The Game begins in &enow&7.");
        setProperty(message + "lobby_waiting_for_player_singular", "&8» &cWe are waiting for &6one &cPlayer");
        setProperty(message + "lobby_waiting_for_player_plural", "&8» &cWe are waiting for &6%amount% Players");
        setProperty(message + "not_enough_player_online", "&8» &cThe countdown was stopped because of the low amount of online Players.");

        setProperty(message + "not_enough_player_online", "&8» &cThe countdown was stopped because of the low amount of online Players.");
        setProperty(message + "spawn_is_not_set", "&8» &cThe spawn is not set (%spawn_name%)");

        setProperty(message + "ingame_actionbar_player", "&8» %team_color%Team %team_name% &8| &7 %timeout%");
        setProperty(message + "ingame_actionbar_spectator", "&8» &7Spectator");

        setProperty(message + "spectator_joined_chat", "&8» &aYou are a spectating now.");
        setProperty(message + "back_to_lobby_kick_msg", "&cBack to Lobby");

        setProperty(message + "lobby_player_voted", "&8» &7You Voted for Map &e%mapname% &7.");

        setProperty(message + "end_countdown_for_restart", "&8» &7The Game ends in §e%timeout% &7.");
        setProperty(message + "timer_format", "%hours%h, %minutes%m, %seconds%s");

        setProperty(message + "ingame_scoreboard_", "&8» &7The Game ends in §e%timeout% &7.");
        setProperty(message + "timer_format", "%hours%h, %minutes%m, %seconds%s");

        setProperty(item + "inv_place_holder", new ItemBuilder(Material.STAINED_GLASS_PANE, " ").setDamage((short)15).checkout());

        setProperty(item + "lobby_items_team_select", new ItemBuilder(Material.BED, "&8» &eTeams").checkout());
        setProperty(item + "lobby_items_team_select_pos", 0);
        setProperty(item + "lobby_items_map_voting", new ItemBuilder(Material.EMPTY_MAP, "&8» &eMapvoting").checkout());
        setProperty(item + "lobby_items_map_voting_pos", 1);
        setProperty(item + "lobby_items_back_to_lobby", new ItemBuilder(Material.FIREBALL, "&8» &eLobby").checkout());
        setProperty(item + "lobby_items_back_to_lobby_pos", 8);

        setProperty(item + "spectator_items_player_overview", new ItemBuilder(Material.COMPASS, "&8» &ePlayers").checkout());
        setProperty(item + "spectator_items_player_overview_pos", 0);
        setProperty(item + "spectator_items_back_to_lobby", new ItemBuilder(Material.FIREBALL, "&8» &eLobby").checkout());
        setProperty(item + "spectator_items_back_to_lobby_pos", 8);

        setProperty(inventory + "team_select_inv_name", "&8» &eTeams");
        setProperty(inventory + "team_select_item_name", "&8» %team_color%%team_name%");
        setProperty(inventory + "team_select_item_lore_format", "&8- &7%player_name%");

        setProperty(inventory + "map_select_inv_name", "&8» &eMapvoting");
        setProperty(inventory + "map_select_item_name", "&8» &e%mapname%");
        setProperty(inventory + "map_select_item_lore_format", "&7Votes: &e%votes%");

        setProperty(scoreboard + "lobby_title", "&8« &6YOUR-SERVER.NET &8»");
        setProperty(scoreboard + "lobby_title_line_01", "&7&2              ");
        setProperty(scoreboard + "lobby_title_line_02", "&e&lTeam");
        setProperty(scoreboard + "lobby_title_line_03", "&8➟ %team%");
        setProperty(scoreboard + "lobby_title_line_04", "&7&0 ");
        setProperty(scoreboard + "lobby_title_line_05", "&e&lMap");
        setProperty(scoreboard + "lobby_title_line_06", "&8➟ &7%mapname%");
        setProperty(scoreboard + "lobby_title_line_07", "&7&9 ");
        setProperty(scoreboard + "lobby_title_line_08", "&e&lType");
        setProperty(scoreboard + "lobby_title_line_09", "&8➟ &7%game%");

        setProperty(scoreboard + "ingame_title", "&8« &6YOUR-SERVER.NET &8»");
        setProperty(scoreboard + "ingame_title_line_01", "&7&2              ");
        setProperty(scoreboard + "ingame_title_line_02", "&e&lTeam");
        setProperty(scoreboard + "ingame_title_line_03", "&8➟ %team%");
        setProperty(scoreboard + "ingame_title_line_04", "&7&0 ");
        setProperty(scoreboard + "ingame_title_line_05", "&e&lMap");
        setProperty(scoreboard + "ingame_title_line_06", "&8➟ &7%mapname%");
        setProperty(scoreboard + "ingame_title_line_07", "&7&9 ");
        setProperty(scoreboard + "ingame_title_line_08", "&e&lKills");
        setProperty(scoreboard + "ingame_title_line_09", "&8➟ &70");

        setProperty(scoreboard + "end_title", "&8« &6YOUR-SERVER.NET &8»");
        setProperty(scoreboard + "end_title_line_01", "&7&2              ");
        setProperty(scoreboard + "end_title_line_02", "&e&lTeam");
        setProperty(scoreboard + "end_title_line_03", "&8➟ %team%");
        setProperty(scoreboard + "end_title_line_04", "&7&0 ");
        setProperty(scoreboard + "end_title_line_05", "&e&lMap");
        setProperty(scoreboard + "end_title_line_06", "&8➟ &7%mapname%");
        setProperty(scoreboard + "end_title_line_07", "&7&9 ");
        setProperty(scoreboard + "end_title_line_08", "&e&lRank");
        setProperty(scoreboard + "end_title_line_09", "&8➟ &e#1");
    }

    private static String transformMessage(String string) {
        String message = string;
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static boolean create() {
        if(!logs.exists()) {
            try {
                logs.createNewFile();
                loadDefaultConfig();
                return true;
            }
            catch (Exception localException) {}
        }
        return false;
    }

    private static void save() {
        try { locscfg.save(logs); } catch (Exception e) {}
    }

}
