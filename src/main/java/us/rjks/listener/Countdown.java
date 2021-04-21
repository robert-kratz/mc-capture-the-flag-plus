package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import us.rjks.event.EndCountDownStopEvent;
import us.rjks.event.GameEndEvent;
import us.rjks.event.StartCountDownEndEvent;
import us.rjks.event.TimeOutCountDownEndsEvent;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.utils.*;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 18.04.2021 / 18:42
 *
 **************************************************************************/

public class Countdown implements Listener {

    @EventHandler
    public void onCountDownEnds(StartCountDownEndEvent event) {
        GameManager.setGame(Game.INGAME);
        GameManager.getTimeOut().start();
        for (Player a : GameManager.getIngamePlayer()) {
            GameManager.getCurrentMap().teleportPlayerToWorld(a);
        }
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(TeamManager.getTeamFromPlayer(all) == null) {
                TeamManager.Team team = TeamManager.fillTeams(all);
                TeamManager.addPlayerToTeam(all, team.getName());

                all.sendMessage(Config.getMessageAsString("countdown_end_team_info")
                        .replaceAll("%team_color%", team.getColor())
                        .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                        .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                        .replaceAll("%team_prefix%", team.getPrefix())
                        .replaceAll("%team_suffix%", team.getSuffix())
                        .replaceAll("%team_name%", team.getName()));
            } else {
                TeamManager.Team team = TeamManager.getTeamFromPlayer(all);
                all.sendMessage(Config.getMessageAsString("countdown_end_team_info")
                        .replaceAll("%team_color%", team.getColor())
                        .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                        .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                        .replaceAll("%team_prefix%", team.getPrefix())
                        .replaceAll("%team_suffix%", team.getSuffix())
                        .replaceAll("%team_name%", team.getName()));
            }
            all.setFireTicks(0);
            all.setFallDistance(0);

            //TODO: SET HP FOOD ETC TO GAME STANDART

            ScoreBoardManager.setScoreboard(all);

            //TODO: EXAMPLE TP PLAYERS, ADD KIT ETC
            all.sendMessage(Config.getMessageAsString("countdown_end_kit_info")
                    .replaceAll("%kit_name%", GameManager.getPlayerKit(all)));
            KitManager.getKitFromName(GameManager.getPlayerKit(all)).setKit(all);
        }
    }

    @EventHandler
    public void onTie(TimeOutCountDownEndsEvent event) {
        GameManager.setGame(Game.END);

        //TODO: TELEPORT PLAYERS, TIE

        GameManager.getEndgame().start();

        for (Player all : Bukkit.getOnlinePlayers()) {
            ScoreBoardManager.setScoreboard(all);
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        Bukkit.broadcastMessage(Config.getMessageAsString("game_ended"));

        String winner = "";
        TeamManager.Team team = event.getWinner();
        for (TeamManager.TeamPlayer player : event.getPlayers()) {
            if (winner.isEmpty()) {
                winner = player.getPlayer().getName();
            } else {
                winner = winner + ", " + player.getPlayer().getName();
            }
            break;
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            TitleManager.sendTitle(all, Config.getMessageAsString("game_ended_winner_title")
                            //TITLE
                            .replaceAll("%team_color%", team.getColor())
                            .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                            .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                            .replaceAll("%team_prefix%", team.getPrefix())
                            .replaceAll("%team_suffix%", team.getSuffix())
                            .replaceAll("%team_name%", team.getName()),
                    //SUBTITLE
                    Config.getMessageAsString("game_ended_winner_subtitle")
                            .replaceAll("%team_color%", team.getColor())
                            .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                            .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                            .replaceAll("%team_prefix%", team.getPrefix())
                            .replaceAll("%team_suffix%", team.getSuffix())
                            .replaceAll("%team_name%", team.getName()));
        }

        Bukkit.broadcastMessage(Config.getMessageAsString("game_ended_winner_chat_message")
                .replaceAll("%team_color%", team.getColor())
                .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                .replaceAll("%team_prefix%", team.getPrefix())
                .replaceAll("%team_suffix%", team.getSuffix())
                .replaceAll("%team_name%", team.getName()));

        Bukkit.broadcastMessage(Config.getMessageAsString("game_ended_winner_chat_message_line_two")
                .replaceAll("%winner_list%", winner));

        GameManager.getEndgame().start();
    }

    @EventHandler
    public void onCountDownEnds(EndCountDownStopEvent event) {
        for(Player all : GameManager.getIngamePlayer()) {
            all.sendMessage(Config.getMessageAsString("end_countdown_ends_message"));
            all.playSound(all.getLocation(), Sound.NOTE_BASS, 10, 10);
        }
        GameManager.getEndgame().stopGame();
        GameManager.getEndgame().deleteCounter();

        for (Player all : Bukkit.getOnlinePlayers()) {
            ScoreBoardManager.setScoreboard(all);
        }

        //TODO: STOP SERVER
        Bukkit.reload();
    }



}
