package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import us.rjks.event.GameEndEvent;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.game.GameProperty;
import us.rjks.utils.Config;
import us.rjks.utils.Spectator;
import us.rjks.utils.TeamManager;
import us.rjks.utils.TitleManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.04.2021 / 16:10
 *
 **************************************************************************/

public class Quit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        if(GameManager.getGame().equals(Game.LOBBY)) {
            GameManager.removeIngamePlayer(event.getPlayer());

            //Properties remove
            GameProperty.manager.getProperty("voting").removePlayer(event.getPlayer());

            Bukkit.broadcastMessage(Config.getMessageAsString("lobby_join_message")
                    .replaceAll("%player_name%", event.getPlayer().getName())
                    .replaceAll("%max_player%", Integer.toString(GameManager.getMaxPlayers()))
                    .replaceAll("%online_player%", Integer.toString(GameManager.getIngamePlayer().size())));

            if(GameManager.getCountdown().isRunning() && (Bukkit.getOnlinePlayers().size() - 1) < GameManager.getMinPlayerStart()) { //USERAMOUNT - CURRENT QUIT PLAYER
                GameManager.getCountdown().stop();
            }
        } else if(GameManager.getGame().equals(Game.INGAME)) {

            GameManager.removeIngamePlayer(event.getPlayer());

            if (GameManager.getIngamePlayer().contains(event.getPlayer())) {
                Spectator.removeSpectator(event.getPlayer());
                Bukkit.broadcastMessage(Config.getMessageAsString("ingame_player_leaves_message")
                        .replaceAll("%player_name%", event.getPlayer().getName()));
            } else if (GameManager.getSpectatorPlayers().contains(event.getPlayer())) {
                Spectator.removeSpectator(event.getPlayer());
            }

            if (TeamManager.getTeamsAlive() == 1) {

                TeamManager.Team team = null;
                for (Player player : GameManager.getIngamePlayer()) {
                    team = TeamManager.getTeamFromPlayer(player);
                    break;
                }

                Bukkit.getPluginManager().callEvent(new GameEndEvent(team.getPlayers(), team));
            }
        }
    }

}
