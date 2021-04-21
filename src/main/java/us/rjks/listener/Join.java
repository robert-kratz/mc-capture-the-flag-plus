package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.game.Main;
import us.rjks.utils.*;
import us.rjks.utils.Countdown;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.04.2021 / 16:10
 *
 **************************************************************************/

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        TabListManager.setPrefix(event.getPlayer());
        ScoreBoardManager.setScoreboard(event.getPlayer());
        //TODO: System.out.println(config.getString("premium_player_kicked_user_kick_message"));
        if(GameManager.getGame().equals(Game.LOBBY)) {
            Inventory.loadLobbyItems(event.getPlayer());
            GameManager.addIngamePlayer(event.getPlayer());
            if(!GameManager.getCountdown().isRunning() && Bukkit.getOnlinePlayers().size() >= GameManager.getMinPlayerStart()) {
                GameManager.getCountdown().start();
            }
            if(GameManager.getMaxPlayers() == Bukkit.getOnlinePlayers().size() && GameManager.getCountdown().getCountdown() < 10) {
                GameManager.getCountdown().setCountdown(10);
            }
            Bukkit.broadcastMessage(Config.getMessageAsString("lobby_join_message")
                    .replaceAll("%player_name%", event.getPlayer().getName())
                    .replaceAll("%max_player%", Integer.toString(GameManager.getMaxPlayers()))
                    .replaceAll("%online_player%", Integer.toString(GameManager.getIngamePlayer().size())));
        }
        if(GameManager.getGame().equals(Game.INGAME)) {
            Spectator.setSpectator(event.getPlayer());
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if(GameManager.getGame().equals(Game.LOBBY)) {
            if(GameManager.getMaxPlayers() <= Bukkit.getOnlinePlayers().size() + 1) { //USERS + LOGGED IN USER
                if(event.getPlayer().hasPermission(Config.getConfigeAsString("permission_premium_kick"))) {
                    boolean kicked = false;
                    for (Player all : GameManager.getIngamePlayer()) {
                        if(!all.hasPermission(Config.getConfigeAsString("permission_premium_kick")) &&
                                !all.hasPermission(Config.getConfigeAsString("permission_premium_kick_bypass"))) {
                            all.kickPlayer(Config.getMessageAsString("premium_player_kicked_user_kick_message"));
                            GameManager.removeIngamePlayer(all);
                            kicked = true;
                            break;
                        }
                    }
                    if(kicked) event.disallow(PlayerLoginEvent.Result.KICK_FULL, Config.getMessageAsString("nobody_found_to_premium_kick"));
                    else event.allow();
                }
            } else {
                event.allow();
            }
        }
        if(GameManager.getGame().equals(Game.INGAME)) {
            event.allow();
        }
        if(GameManager.getGame().equals(Game.END)) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Config.getMessageAsString("game_already_ended"));
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        String map;
        if (GameManager.getCurrentMap() == null) {
            map = "Voting";
        } else {
            map = GameManager.getCurrentMap().getName();
        }


        event.setMotd(GameManager.getGame().name() + ";" + map);
        event.setMaxPlayers(GameManager.getMaxPlayers());
    }
}