package us.rjks.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.utils.Config;
import us.rjks.utils.TeamManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 19.04.2021 / 13:19
 *
 **************************************************************************/

public class Start  implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(Config.getConfigeAsString("permission_start"))) {
                if (args.length == 0) {
                    if(GameManager.getGame().equals(Game.LOBBY)) {
                        if (GameManager.getCountdown().isRunning()) {
                            if(GameManager.getCountdown().getCountdown() > 6) {
                                p.sendMessage(Config.getMessageAsString("game_start_successfully"));
                                GameManager.getCountdown().setCountdown(5);
                            } else {
                                p.sendMessage(Config.getMessageAsString("game_already_started"));
                            }
                        } else {
                            p.sendMessage(Config.getMessageAsString("countdown_cant_be_startet_before_running"));
                        }
                    } else {
                        p.sendMessage(Config.getMessageAsString("game_already_started"));
                    }
                } else {
                    p.sendMessage(Config.getMessageAsString("help_command_start"));
                }
            } else {
                p.sendMessage(Config.getMessageAsString("no_permission"));
            }
        } else {
            sender.sendMessage(Config.getMessageAsString("has_to_be_a_player"));
        }

        return false;
    }
}
