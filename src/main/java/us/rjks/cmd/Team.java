package us.rjks.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.utils.Config;
import us.rjks.utils.ScoreBoardManager;
import us.rjks.utils.TeamManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 18.04.2021 / 19:16
 *
 **************************************************************************/

public class Team implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (GameManager.getGame().equals(Game.LOBBY)) {
                if (args.length == 1) {
                    if(TeamManager.getTeamsFromName(args[0]) != null) {
                        if(!TeamManager.getTeamsFromName(args[0]).isFull()) {
                            TeamManager.removePlayerFromAllTeams(p);
                            TeamManager.addPlayerToTeam(p, args[0]);

                            ScoreBoardManager.setScoreboard(p);

                            TeamManager.Team team = TeamManager.getTeamsFromName(args[0]);

                            p.sendMessage(Config.getMessageAsString("team_selected_successfully")
                                    .replaceAll("%team_color%", team.getColor())
                                    .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                                    .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                                    .replaceAll("%team_prefix%", team.getPrefix())
                                    .replaceAll("%team_suffix%", team.getSuffix())
                                    .replaceAll("%team_name%", team.getName()));
                        } else {
                            p.sendMessage(Config.getMessageAsString("team_is_full"));
                        }
                    } else {
                        p.sendMessage(Config.getMessageAsString("team_not_exists"));
                    }
                } else {
                    p.sendMessage(Config.getMessageAsString("help_command_team"));
                }
            } else {
                p.sendMessage(Config.getMessageAsString("game_already_started"));
            }
        } else {
            sender.sendMessage(Config.getMessageAsString("has_to_be_a_player"));
        }

        return false;
    }
}
