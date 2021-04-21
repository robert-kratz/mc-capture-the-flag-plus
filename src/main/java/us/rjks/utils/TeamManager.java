package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import us.rjks.game.GameManager;
import us.rjks.game.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.04.2021 / 16:00
 *
 **************************************************************************/

public class TeamManager {

    private static File logs = new File("plugins/" + Main.getPlugin().getName() + "/", "teams.yml");
    private static YamlConfiguration locscfg = YamlConfiguration.loadConfiguration(logs);

    public static ArrayList<Team> teams = new ArrayList<>();

    public static void createTeam(String name, String prefix, String suffix, String color, Integer maxPlayers) {
        locscfg.set(name + ".name", name);
        locscfg.set(name + ".prefix", prefix);
        locscfg.set(name + ".suffix", suffix);
        locscfg.set(name + ".color", color);
        locscfg.set(name + ".maxPlayers", maxPlayers);
        save();
        teams.add(new Team(name.replaceAll("&", "§"), prefix.replaceAll("&", "§"), suffix.replaceAll("&", "§"), color.replaceAll("&", "§"), maxPlayers));
    }

    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public static Team getTeamsFromName(String name) {
        for(Team kit : getTeams()) {
            if(kit.getName().equalsIgnoreCase(name)) return kit;
        }
        return null;
    }

    public static void loadTeams() {
        for(String tops : locscfg.getConfigurationSection("").getKeys(false)) {
            System.out.println("[TEAM] Loaded Team " + tops);
            teams.add(new Team(tops));
        }
    }

    public static Team getTeamFromPlayer(Player player) {
        for (Team team : teams) {
            for (TeamPlayer p : team.getPlayers()) {
                if (p.getPlayer().equals(player)) {
                    return team;
                }
            }
        }
        return null;
    }

    public static Team getTeamFromName(String name) {
        for (Team team : teams) {
            if(team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    public static boolean removeTeamFromPlayer(Player player, String mame) {
        for (Team team : teams) {
            if(team.getName().equalsIgnoreCase(mame)) {
                team.removePlayer(player);
                return true;
            }
        }
        return false;
    }

    public static void fillTeams(ArrayList<Player> players) {

        for (Player all : players) {
            Team lowest = teams.get(0);
            for (Team team : teams) {
                if(lowest.getPlayers().size() < team.getPlayers().size()) {
                    lowest = team;
                }
            }
            getTeamsFromName(lowest.getName()).addPlayer(all);
        }
    }

    public static Team fillTeams(Player target) {
        Team lowest = teams.get(0);
        for (Team team : teams) {
            if(lowest.getPlayers().size() >= team.getPlayers().size() && !team.isFull()) {
                lowest = team;
            }
        }
        for (Team teams : teams) {
            if(teams.getName().equalsIgnoreCase(lowest.getName())) {
                return teams;
            }
        }
        return null;
    }

    public static boolean removePlayerFromAllTeams(Player target) {
        for (Team team : teams) {
            team.removePlayer(target);
        }
        return false;
    }

    public static boolean addPlayerToTeam(Player target, String name) {
        for (Team team : teams) {
            if(team.getName().equalsIgnoreCase(name) && !team.isFull()) {
                team.addPlayer(target);
                return true;
            }
        }
        return false;
    }

    public static boolean addPlayerToTeam(Player target, Team name) {
        for (Team team : teams) {
            if(team.getName().equalsIgnoreCase(name.getName()) && !team.isFull()) {
                team.addPlayer(target);
                return true;
            }
        }
        return false;
    }

    public static int getFilledTeams() {
        int i = 0;
        for (Team team : teams) {
            if(team.getPlayers().size() > 0) {
                i++;
            }
        }
        return i;
    }

    public static int getPlayersNotInTeam() {
        int i = 0;
        for (Player all : GameManager.getIngamePlayer()) {
            if(getTeamFromPlayer(all) == null) {
                i++;
            }
        }
        return i;
    }

    public static int getTeamsAlive() {
        int i = 0;
        for (Team team : teams) {
            if (team.isAlive()) {
                i++;
            }
        }
        return i;
    }

    public static class TeamPlayer {

        private Player player;
        private HashMap<String, Object> property = new HashMap<>();

        public TeamPlayer(Player player) {
            this.player = player;
        }

        public void setProperty(String key, Object value) {
            property.put(key, value);
            save();
        }

        public Object getProperty(String key) {
            return property.get(key);
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }
    }


    public static class Team {

        private String name, prefix, color, suffix;
        private Integer maxPlayers;
        private boolean alive = false;

        private ArrayList<TeamPlayer> players = new ArrayList<>();

        public Team(String name) {
            this.name = name;
            this.prefix = locscfg.getString(name + ".prefix").replaceAll("&", "§");
            this.suffix = locscfg.getString(name + ".suffix").replaceAll("&", "§");
            this.color = locscfg.getString(name + ".color").replaceAll("&", "§");
            this.maxPlayers = locscfg.getInt(name + ".maxPlayers");
        }

        public Team(String name, String prefix, String suffix, String color, Integer maxPlayers) {
            this.name = name;
            this.prefix = prefix;
            this.suffix = suffix;
            this.color = color;
            this.maxPlayers = maxPlayers;
        }

        public Object getProperty(String name) {
            return locscfg.get(name + ".property." + name);
        }

        public void setProperty(String name, Object object) {
            locscfg.set(getName() + ".property." + name, object);
            save();
        }

        public boolean addPlayer(Player target) {
            boolean al = false;
            for (TeamPlayer p : getPlayers()) {
                if(p.getPlayer().equals(target)) {
                    al = true;
                }
            }
            if(maxPlayers > players.size() && !al) {
                players.add(new TeamPlayer(target));
                alive = true;
                TabListManager.setPrefix(target);
                return true;
            } else {
                return false;
            }
        }

        public boolean removePlayer(Player target) {
            for (TeamPlayer p : getPlayers()) {
                if(p.getPlayer().equals(target)) {
                    players.remove(p);
                    TabListManager.setPrefix(target);
                    if(getPlayers().size() > 0) {
                        this.alive = false;
                    }
                    return true;
                }
            }
            if(getPlayers().size() > 0) {
                this.alive = false;
            }
            return false;
        }

        public boolean isAlive() {
            return alive;
        }

        public void setAlive(boolean alive) {
            this.alive = alive;
        }

        public ArrayList<TeamPlayer> getPlayers() {
            return players;
        }

        public String getName() {
            return name;
        }

        public Integer getMaxPlayers() {
            return maxPlayers;
        }

        public String getSuffix() {
            return suffix;
        }

        public String getColor() {
            return color;
        }

        public String getPrefix() {
            return prefix;
        }

        public boolean isFull() {
            if(maxPlayers > players.size()) return false;
            return true;
        }

        public void setMaxPlayers(Integer maxPlayers) {
            this.maxPlayers = maxPlayers;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }

    public static boolean create() {
        if(!logs.exists()) {
            try {
                logs.createNewFile();

                //CREATE SAMPLE TEAMS
                createTeam("BLUE", "&9Red &8| &9", "", "&9", 1);
                createTeam("RED", "&cRed &8| &c", "", "&c", 1);
                createTeam("YELLOW", "&eYellow &8| &e", "", "&e", 1);
                createTeam("GREEN", "&aGreen &8| &a", "", "&a", 1);
                return true;
            }
            catch (Exception localException) {}
        }
        return false;
    }

    private static void save() {
        try { locscfg.save(logs); } catch (Exception e) {}
    }
}
