package us.rjks.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.rjks.utils.ItemBuilder;
import us.rjks.utils.KitManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 18.04.2021 / 12:16
 *
 **************************************************************************/

public class Kit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.isOp()) {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("list")) {
                        p.sendMessage("Kits in cache:");
                        for (KitManager.Kit kits : KitManager.getKits()) {
                            p.sendMessage(" - " + kits.getName());
                        }
                    }
                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("load")) {
                        if(KitManager.getKitFromName(args[1]) != null) {
                            KitManager.getKitFromName(args[1]).setKit(p);
                            p.sendMessage("You loaded the Kit " + args[1]);
                        } else {
                            p.sendMessage("This Kit does not exist");
                        }
                    } else if(args[0].equalsIgnoreCase("save")) {
                        KitManager.saveKit(args[1], p, new ItemBuilder(Material.SUGAR, args[1]).checkout(), "This is the " + args[1] + " Kit");
                        p.sendMessage("You saved the Kit " + args[1]);
                    } else if(args[0].equalsIgnoreCase("sethp")) {
                        p.setMaxHealth(Double.valueOf(args[1]));
                        p.sendMessage("You have now " + args[1] + "hp");
                    }
                }
            }
        } else {
            sender.sendMessage("§cYou have to be a player to execute this command.");
        }

        return false;
    }
}
