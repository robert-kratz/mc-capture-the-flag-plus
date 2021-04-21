package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.utils.Config;
import us.rjks.utils.TeamManager;

import java.util.List;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 21.04.2021 / 11:53
 *
 **************************************************************************/

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        String msg = event.getMessage().replace("%", "%%");
        msg.replaceAll("%", "%%");

        if (GameManager.getGame().equals(Game.LOBBY)) {

            if (TeamManager.getTeamFromPlayer(event.getPlayer()) != null) {
                TeamManager.Team team = TeamManager.getTeamFromPlayer(event.getPlayer());
                event.setFormat(Config.getMessageAsString("chat_format_team")
                        .replaceAll("%team_color%", team.getColor())
                        .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                        .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                        .replaceAll("%team_prefix%", team.getPrefix())
                        .replaceAll("%player_name%", event.getPlayer().getDisplayName())
                        .replaceAll("%player_message%", msg)
                        .replaceAll("%team_suffix%", team.getSuffix())
                        .replaceAll("%team_name%", team.getName()));
            } else {
                event.setFormat(Config.getMessageAsString("chat_format_no_team_selected")
                        .replaceAll("%player_name%", event.getPlayer().getDisplayName())
                        .replaceAll("%player_message%", msg));
            }

        } else if (GameManager.getGame().equals(Game.INGAME)) {
            if (TeamManager.getTeamFromPlayer(event.getPlayer()) != null) {
                TeamManager.Team team = TeamManager.getTeamFromPlayer(event.getPlayer());
                event.setFormat(Config.getMessageAsString("chat_format_team")
                        .replaceAll("%team_color%", team.getColor())
                        .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                        .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                        .replaceAll("%team_prefix%", team.getPrefix())
                        .replaceAll("%player_name%", event.getPlayer().getDisplayName())
                        .replaceAll("%player_message%", msg)
                        .replaceAll("%team_suffix%", team.getSuffix())
                        .replaceAll("%team_name%", team.getName()));
            } else {
                event.setCancelled(true);
                for (Player all : GameManager.getSpectatorPlayers()) {
                    all.sendMessage(Config.getMessageAsString("chat_format_spectator")
                            .replaceAll("%player_name%", event.getPlayer().getDisplayName())
                            .replaceAll("%player_message%", msg));
                }
            }
        } else if (GameManager.getGame().equals(Game.END)) {
            if (TeamManager.getTeamFromPlayer(event.getPlayer()) != null) {
                TeamManager.Team team = TeamManager.getTeamFromPlayer(event.getPlayer());
                event.setFormat(Config.getMessageAsString("chat_format_team")
                        .replaceAll("%team_color%", team.getColor())
                        .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                        .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                        .replaceAll("%team_prefix%", team.getPrefix())
                        .replaceAll("%player_name%", event.getPlayer().getDisplayName())
                        .replaceAll("%player_message%", msg)
                        .replaceAll("%team_suffix%", team.getSuffix())
                        .replaceAll("%team_name%", team.getName()));
            } else {
                event.setFormat(Config.getMessageAsString("chat_format_no_team_selected")
                        .replaceAll("%player_name%", event.getPlayer().getDisplayName())
                        .replaceAll("%player_message%", msg));
            }
        }
    }

}
