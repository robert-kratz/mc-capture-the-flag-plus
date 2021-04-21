package us.rjks.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.rjks.game.GameManager;
import us.rjks.utils.*;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 21.04.2021 / 12:26
 *
 **************************************************************************/

public class Forcemap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission(Config.getConfigeAsString("permission_forcemap"))) {
                if (args.length == 1) {
                    if (!GameManager.isForcemaped()) {
                        if (MapManager.getMapFromName(args[0]) != null) {
                            GameManager.setCurrentMap(MapManager.getMapFromName(args[0]));
                            GameManager.setForcemaped(true);
                            sender.sendMessage(Config.getMessageAsString("forcemap_selected_successfully")
                                    .replaceAll("%mapname%", MapManager.getMapFromName(args[0]).getName()));
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                ScoreBoardManager.setScoreboard(all);
                            }
                            return true;
                        } else {
                            sender.sendMessage(Config.getMessageAsString("map_does_not_exists")
                                    .replaceAll("%mapname%", args[0]));
                        }
                    } else {
                        sender.sendMessage(Config.getMessageAsString("map_is_already_selected")
                                .replaceAll("%mapname%", args[0]));
                    }
                }
            } else {
                sender.sendMessage(Config.getMessageAsString("no_permission"));
            }
        }
        return false;
    }
}
