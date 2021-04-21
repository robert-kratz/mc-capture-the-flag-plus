package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 19.04.2021 / 17:51
 *
 **************************************************************************/

public class TabListManager {

    public static Scoreboard scoreboard;

    public static void loadTabllist() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        for (TeamManager.Team team : TeamManager.getTeams()) {
            if (Boolean.parseBoolean(Config.getConfig("show_prefix_in_tab").toString()) == true) {
                scoreboard.registerNewTeam(team.getName()).setPrefix(team.getPrefix());
            } else {
                scoreboard.registerNewTeam(team.getName()).setPrefix(team.getColor());
            }
        }

        //REGISTER SPEC TEAM
        scoreboard.registerNewTeam("spectator").setPrefix(Config.getMessageAsString("chat_format_default_color") + "");
    }

    public static synchronized void setPrefix(Player player) {

        String team = "";
        for (TeamManager.Team t : TeamManager.getTeams()) {
            for (TeamManager.TeamPlayer p : t.getPlayers()) {
                if(p.getPlayer().equals(player)) {
                    team = t.getName();
                }
            }
        }

        if(team.isEmpty()) team = "spectator";

        scoreboard.getTeam(team).addEntry(player.getName());
        player.setDisplayName(scoreboard.getTeam(team).getPrefix() + player.getName() + "§r");

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreboard);
        }
    }

}
