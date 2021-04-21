package us.rjks.game;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import us.rjks.cmd.*;
import us.rjks.db.MySQL;
import us.rjks.listener.*;
import us.rjks.utils.*;
import us.rjks.utils.Countdown;

import java.io.File;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.04.2021 / 15:58
 *
 **************************************************************************/

public class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;

        //Init Config
        //config.configFile();

        loadListeners();

        try {
            if(!new File("plugins/" + getName()).exists()) new File("plugins/" + getName()).mkdirs();
            if(!new File("plugins/" + getName() + "/maps").exists()) new File("plugins/" + getName() + "/maps").mkdirs();

            Config.create();

            KitManager.create();
            MapManager.create();
            SpawnManager.create();
            TeamManager.create();

            MapManager.loadMaps();
            KitManager.loadKits();
            TeamManager.loadTeams();

            TabListManager.loadTabllist();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //DB CONNECTION
        MySQL.connect();
        MySQL.createTable();

        if (!MySQL.isConnected()) {
            Bukkit.getConsoleSender().sendMessage("§c§l=======================================================");
            Bukkit.getConsoleSender().sendMessage("§c§l              PLUGIN HAS BEEN DISABLED!");
            Bukkit.getConsoleSender().sendMessage("§c§l       THE MYSQL CONNECTION IS NOT VALID, PLEASE:");
            Bukkit.getConsoleSender().sendMessage("§c§l             CHECK YOUR DETAILS IN mysql.yml");
            Bukkit.getConsoleSender().sendMessage("§c§l=======================================================");
        }

        //THIS MESSAGE IS CHECKING, IF THE PLUGIN IS SETUP RIGHT
        if (TeamManager.getTeams().size() == 0) {
            Bukkit.getConsoleSender().sendMessage("§c§l=======================================================");
            Bukkit.getConsoleSender().sendMessage("§c§l              PLUGIN HAS BEEN DISABLED!");
            Bukkit.getConsoleSender().sendMessage("§c§l       NO TEAM WAS SETUP, PLEASE ADD A TEAM IN:");
            Bukkit.getConsoleSender().sendMessage("§c§l    plugins/" + getPlugin().getName() + "/teams.yml");
            Bukkit.getConsoleSender().sendMessage("§c§l=======================================================");

            if (MapManager.getMaps().size() == 0) {
                Bukkit.getConsoleSender().sendMessage("§c§l=======================================================");
                Bukkit.getConsoleSender().sendMessage("§c§l              PLUGIN HAS BEEN DISABLED!");
                Bukkit.getConsoleSender().sendMessage("§c§l       NO MAP WAS FOUND, PLEASE ADD A MAP TO:");
                Bukkit.getConsoleSender().sendMessage("§c§l    plugins/" + getPlugin().getName() + "/maps.yml");
                Bukkit.getConsoleSender().sendMessage("§c§l             AND ADD THEM TO THE CONFIG!");
                Bukkit.getConsoleSender().sendMessage("§c§l=======================================================");
            }
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Properties
        GameProperty.manager.addProperty("voting");

        //Configuration
        GameManager.setTeamAmount(Integer.parseInt(Config.getConfig("team_amount").toString()));
        GameManager.setTimeOutTime(Integer.parseInt(Config.getConfig("ingame_count_down_game_ends_in_minutes").toString())); //in minuutes
        GameManager.setPlayersPerTeam(Integer.parseInt(Config.getConfig("players_per_team").toString()));
        GameManager.setCountDown(Integer.parseInt(Config.getConfig("start_count_down_time").toString()));
        GameManager.setMinPlayerStart(Integer.parseInt(Config.getConfig("min_players_start").toString()));
        GameManager.setEndCountDown(Integer.parseInt(Config.getConfig("end_count_down_time").toString()));

        GameManager.setGame(Game.LOBBY);
        GameManager.setTimeOut(new TimeOut());
        GameManager.setCountdown(new Countdown());
        GameManager.setEndgame(new Endgame());

        System.out.println("[GAME] Plugin successfully started");
        System.out.println("[GAME] Author: Salty#0299");
        System.out.println("[GAME] Version: 1.0.0");
        System.out.println("[GAME] Git: link.rjks.us/github");
        System.out.println("[GAME] Support: link.rjks.us/support");

    }

    @Override
    public void onDisable() {
        super.onDisable();

        //Unload all Maps
        for (MapManager.Map maps : MapManager.getMaps()) {
            if (maps.isLoaded()) {
                maps.unloadMap();
                maps.deleteMap();
            }
        }

        System.out.println("[GAME] Plugin successfully stopped");
    }

    @Override
    public void onLoad() {
        super.onLoad();

        //TODO: LICENSE CHECK to ls.rjks.us

        System.out.println("[GAME] Plugin successfully loaded");
    }

    public void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new Join(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new Quit(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new LobbyItems(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new Build(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new Respawn(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new Chat(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new us.rjks.listener.Countdown(), getPlugin());

        getServer().getPluginCommand("kit").setExecutor(new Kit());
        getServer().getPluginCommand("map").setExecutor(new Map());
        getServer().getPluginCommand("team").setExecutor(new Team());
        getServer().getPluginCommand("start").setExecutor(new Start());
        getServer().getPluginCommand("forcemap").setExecutor(new Forcemap());
        getServer().getPluginCommand("forcestart").setExecutor(new Start());
    }

    public static Main getPlugin() {
        return plugin;
    }
}
