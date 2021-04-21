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
import us.rjks.utils.MapManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.04.2021 / 16:02
 *
 **************************************************************************/

public class Map implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.isOp()) {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("list")) {
                        p.sendMessage("Maps in cache:");
                        for (MapManager.Map maps : MapManager.getMaps()) {
                            p.sendMessage("- " + maps.getName() + " | Is loaded: " + maps.isLoaded());
                        }
                        p.sendMessage(" ");
                        p.sendMessage("Loaded Maps");
                        for (World world : Bukkit.getWorlds()) {
                            p.sendMessage("- " + world.getName());
                        }
                    }
                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("load")) {
                        if(MapManager.getMapFromName(args[1]) != null) {
                            MapManager.Map map = MapManager.getMapFromName(args[1]);
                            if(!map.isLoaded()) {
                                map.loadMap();
                                p.sendMessage("Loaded Map " + map.getName());
                            } else {
                                p.sendMessage("This Map is already loaded.");
                            }
                        } else {
                            p.sendMessage("Unable to loaded map " + args[1] + " because map is not registered");
                        }
                    } else if(args[0].equalsIgnoreCase("unload")) {
                        if(MapManager.getMapFromName(args[1]) != null) {
                            MapManager.Map map = MapManager.getMapFromName(args[1]);
                            if(map.isLoaded()) {
                                map.unloadMap();
                                map.deleteMap();
                                p.sendMessage("Unloaded Map " + map.getName());
                            } else {
                                p.sendMessage("This Map is not loaded.");
                            }
                        } else {
                            p.sendMessage("Unable to loaded map " + args[1] + " because map is not registered");
                        }
                    } else if(args[0].equalsIgnoreCase("delete")) {
                        if(MapManager.getMapFromName(args[1]) != null) {
                            MapManager.Map map = MapManager.getMapFromName(args[1]);

                            map.unloadMap();
                            map.deleteMapFromConfig();
                            p.sendMessage("Deleted Map " + map.getName() + " from all configs, it will still exists in the Maps Folder");
                        } else {
                            p.sendMessage("Unable to loaded map " + args[1] + " because map is not registered");
                        }
                    } else if(args[0].equalsIgnoreCase("tp")) {
                        for (World world : Bukkit.getWorlds()) {
                            if (!world.getName().equalsIgnoreCase(args[1])) continue;
                            p.teleport(world.getSpawnLocation());
                            p.sendMessage("You are now on " + world.getName());
                            break;
                        }
                    } else if(args[0].equalsIgnoreCase("reset")) {
                        if(MapManager.getMapFromName(args[1]) != null) {
                            MapManager.Map map = MapManager.getMapFromName(args[1]);
                            if(map.isLoaded()) {
                                if (p.getLocation().getWorld().getName().equals(map.getName())) {
                                    map.resetMap();
                                    map.teleportPlayerToWorld(p);
                                    p.sendMessage("Map has been regenerated");
                                } else {
                                    map.resetMap();
                                    p.sendMessage("Map has been regenerated");
                                }
                            } else {
                                p.sendMessage("This Map is not loaded.");
                            }
                        } else {
                            p.sendMessage("Unable to loaded map " + args[1] + " because map is not registered");
                        }
                    } else if(args[0].equalsIgnoreCase("save")) {
                        if(MapManager.getMapFromName(args[1]) != null) {
                            MapManager.Map map = MapManager.getMapFromName(args[1]);
                            if(map.isLoaded()) {
                                if (map.saveToCache()) {
                                    p.sendMessage("The cache has been overridden");
                                } else {
                                    p.sendMessage("Could not save the Map to cache.");
                                }
                            } else {
                                p.sendMessage("This Map is not loaded.");
                            }
                        } else {
                            p.sendMessage("Unable to loaded map " + args[1] + " because map is not registered");
                        }
                    }
                } else if(args.length == 3) {
                    if(args[0].equalsIgnoreCase("create")) {
                        boolean ex = MapManager.createMap(args[1], args[2], new ItemBuilder(Material.SUGAR, args[2]).checkout(), "Salty", "This Map was loaded from " + p.getName());
                        if(!ex) {
                            p.sendMessage("This Map was not found");
                        } else {
                            p.sendMessage("The Map " + args[1] + " was successfully created.");
                        }
                    } else if(args[0].equalsIgnoreCase("backup")) {
                        if(MapManager.getMapFromName(args[1]) != null) {
                            MapManager.Map map = MapManager.getMapFromName(args[1]);
                            if(map.isLoaded()) {
                                boolean created = map.backUpMap(args[2]);
                                if(created) {
                                    p.sendMessage("Map was successfully cloned to " + args[2]);
                                } else {
                                    p.sendMessage("Map could not be cloned because a World with this Name already exists");
                                }
                            } else {
                                p.sendMessage("This Map is not loaded.");
                            }
                        } else {
                            p.sendMessage("Unable to loaded map " + args[1] + " because map is not registered");
                        }
                    } else if(args[0].equalsIgnoreCase("setspawn")) {
                        if(MapManager.getMapFromName(args[1]) != null) {
                            MapManager.Map map = MapManager.getMapFromName(args[1]);
                            if(map.isLoaded()) {
                                if (map.getName().equalsIgnoreCase(p.getWorld().getName())) {
                                    map.setLocation(p.getLocation(), args[2]);
                                    p.sendMessage("You set the " + args[2] + " spawn to your location");
                                } else {
                                    p.sendMessage("You have to be in the specific world to set a spawn");
                                }
                            } else {
                                p.sendMessage("This Map is not loaded.");
                            }
                        } else {
                            p.sendMessage("Unable to loaded map " + args[1] + " because map is not registered");
                        }
                    } else if(args[0].equalsIgnoreCase("teleport")) {
                        if(MapManager.getMapFromName(args[1]) != null) {
                            MapManager.Map map = MapManager.getMapFromName(args[1]);
                            if(map.isLoaded()) {
                                if (map.getLocation(args[2]) != null) {
                                    p.teleport(map.getLocation(args[2]));
                                    p.sendMessage("You are now at location " + args[2] + " on world " + map.getName());
                                } else {
                                    p.sendMessage("This spawn does not exist");
                                }
                            } else {
                                p.sendMessage("This Map is not loaded.");
                            }
                        } else {
                            p.sendMessage("Unable to loaded map " + args[1] + " because map is not registered");
                        }
                    }
                }
            }
        } else {
            sender.sendMessage("§cYou have to be a player to execute this command.");
        }

        return false;
    }
}
