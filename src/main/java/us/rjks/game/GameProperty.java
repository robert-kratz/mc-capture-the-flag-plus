package us.rjks.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 20.04.2021 / 22:04
 *
 **************************************************************************/

public class GameProperty {

    public static Manager manager = new Manager();

    public static class Manager {

        private ArrayList<Property> properties = new ArrayList<>();

        public Manager() {}

        public void addProperty(String name) {
            properties.add(new Property(name));
        }

        public void removeProperty(String name) {
            for (Property property : properties) {
                if (property.getName().equalsIgnoreCase(name)) {
                    properties.remove(property);
                }
            }
        }

        public Property getProperty(String name) {
            for (Property property : properties) {
                if (property.getName().equalsIgnoreCase(name)) {
                    return property;
                }
            }
            return null;
        }
    }

    public static class Property {

        private HashMap<Player, Object> players = new HashMap<>();
        private String name;

        public Property(String name) {
            this.name = name;
        }

        public boolean playerExists(Player player) {
            return players.containsKey(player);
        }

        public Object getPlayerValue(Player player) {
            return players.get(player);
        }

        public boolean setPlayer(Player player, Object object) {
            players.put(player, object);
            return true;
        }

        public boolean removePlayer(Player player) {
            players.remove(player);
            return true;
        }

        public int getAmoutOfKey(Object value) {
            int i = 0;
            for (Player k : players.keySet()) {
                if (players.get(k).equals(value)) {
                    i++;
                }
            }
            return i;
        }

        public Object getHighestValue() {

            Object res = null; int numb = 0;
            for (Player o : getPlayers().keySet()) {
                int i = 0;
                for (Player ob : getPlayers().keySet()) {
                    if (players.get(o).equals(players.get(ob))) {
                        i++;
                    }
                }
                if (i > numb) {
                    res = players.get(o);
                    numb = i;
                }
            }
            return res;
        }

        public Object getPlayer() {
            return true;
        }

        public HashMap<Player, Object> getPlayers() {
            return players;
        }

        public String getName() {
            return name;
        }
    }

}
