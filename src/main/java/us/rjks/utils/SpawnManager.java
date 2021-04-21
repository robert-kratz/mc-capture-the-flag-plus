package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import us.rjks.game.Main;

import java.io.File;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.04.2021 / 16:12
 *
 **************************************************************************/

public class SpawnManager {

    public static File logs = new File("plugins/" + Main.getPlugin().getName() + "/", "spawns.yml");
    public static YamlConfiguration locscfg = YamlConfiguration.loadConfiguration(logs);

    public static void setSpawn(Player p, String name) {
        Location loc = p.getLocation();
        locscfg.set("spawn." + name + ".x", Double.valueOf(loc.getX()));
        locscfg.set("spawn." + name + ".y", Double.valueOf(loc.getY()));
        locscfg.set("spawn." + name + ".z", Double.valueOf(loc.getZ()));
        locscfg.set("spawn." + name + ".yaw", Float.valueOf(loc.getYaw()));
        locscfg.set("spawn." + name + ".pitch", Float.valueOf(loc.getPitch()));
        locscfg.set("spawn." + name + ".world", loc.getWorld().getName());

        save();
    }

    public static void tp(Player p, String name) {
        try {
            if(locscfg.get("spawn." + name) == null) {
                p.sendMessage(Config.getMessageAsString("spawn_is_not_set")
                        .replaceAll("%spawn_name%", name));
            } else {
                Location loc = p.getLocation();
                loc.setX(locscfg.getDouble("spawn."  + name + ".x"));
                loc.setY(locscfg.getDouble("spawn."  + name + ".y"));
                loc.setZ(locscfg.getDouble("spawn."  + name + ".z"));
                loc.setYaw((float)locscfg.getDouble("spawn." + name + ".yaw"));
                loc.setPitch((float)locscfg.getDouble("spawn." + name + ".pitch"));
                loc.setWorld(Bukkit.getWorld(locscfg.getString("spawn." + name + ".world")));
                p.teleport(loc);
            }
        } catch (Exception e) {
            if(p.isOp()) p.sendMessage(Config.getMessageAsString("spawn_is_not_set")
                    .replaceAll("%spawn_name%", name));
        }
    }

    public static Location getLocation(Player player, String name) {
        try {
            Location loc = player.getLocation();
            loc.setX(locscfg.getDouble("spawn."  + name + ".x"));
            loc.setY(locscfg.getDouble("spawn."  + name + ".y"));
            loc.setZ(locscfg.getDouble("spawn."  + name + ".z"));
            loc.setYaw((float)locscfg.getDouble("spawn." + name + ".yaw"));
            loc.setPitch((float)locscfg.getDouble("spawn." + name + ".pitch"));
            loc.setWorld(Bukkit.getWorld(locscfg.getString("spawn." + name + ".world")));

            return loc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Location getLocation(String name) {
        try {
            Location loc = new Location(Bukkit.getWorld(locscfg.getString("spawn." + name + ".world")), 0, 0, 0);
            loc.setX(locscfg.getDouble("spawn."  + name + ".x"));
            loc.setY(locscfg.getDouble("spawn."  + name + ".y"));
            loc.setZ(locscfg.getDouble("spawn."  + name + ".z"));
            loc.setYaw((float)locscfg.getDouble("spawn." + name + ".yaw"));
            loc.setPitch((float)locscfg.getDouble("spawn." + name + ".pitch"));
            loc.setWorld(Bukkit.getWorld(locscfg.getString("spawn." + name + ".world")));

            return loc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean create() {
        if(!logs.exists()) {
            try {
                logs.createNewFile();
                return true;
            }
            catch (Exception localException) {}
        }
        return false;
    }

    public static void save() {
        try { locscfg.save(logs); } catch (Exception e) {}
    }

}
