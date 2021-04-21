package us.rjks.game;

import org.bukkit.entity.Player;
import us.rjks.utils.*;

import java.util.ArrayList;
import java.util.HashMap;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.04.2021 / 16:29
 *
 **************************************************************************/

public class GameManager {

    private static Game game;
    private static boolean forcemaped = false;
    private static Integer teamAmount, playersPerTeam, countDown, endCountDown, minPlayerStart, timeOutTime;
    private static Countdown countdown;
    private static TimeOut timeOut;
    private static Endgame endgame;
    private static MapManager.Map currentMap;
    private static ArrayList<Player> ingamePlayer = new ArrayList<Player>(), spectatorPlayers = new ArrayList<Player>();
    private static ArrayList<MapManager.VotePlayer> votePlayers = new ArrayList<>();
    private static HashMap<Player, String> playerKits = new HashMap<>();
    private static HashMap<String, Object> property = new HashMap<>();

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GameManager.game = game;
    }

    public static void setPlayersPerTeam(Integer playersPerTeam) {
        GameManager.playersPerTeam = playersPerTeam;
    }

    public static void setTeamAmount(Integer teamAmount) {
        GameManager.teamAmount = teamAmount;
    }

    public static void setCountDown(Integer countDown) {
        GameManager.countDown = countDown;
    }

    public static void setCountdown(Countdown countdown) {
        GameManager.countdown = countdown;
    }

    public static void setMinPlayerStart(Integer minPlayerStart) {
        GameManager.minPlayerStart = minPlayerStart;
    }

    public static void setTimeOut(TimeOut timeOut) {
        GameManager.timeOut = timeOut;
    }

    public static void setCurrentMap(MapManager.Map currentMap) {
        GameManager.currentMap = currentMap;
    }

    public static void setForcemaped(boolean forcemaped) {
        GameManager.forcemaped = forcemaped;
    }

    public static boolean isForcemaped() {
        return forcemaped;
    }

    public static MapManager.Map getCurrentMap() {
        return currentMap;
    }

    public static void addProperty(String key, Object value) {
        property.put(key, value);
    }

    public static Object getProperty(String key) {
        return property.get(key);
    }

    public static boolean propertyExists(String key) {
        return property.get(key) != null;
    }

    public static boolean removeProperty(String key) {
        property.remove(key);
        return true;
    }

    public static ArrayList<MapManager.VotePlayer> getVotePlayers() {
        return votePlayers;
    }

    public static TimeOut getTimeOut() {
        return timeOut;
    }

    public static Integer getMinPlayerStart() {
        return minPlayerStart;
    }

    public static Countdown getCountdown() {
        return countdown;
    }

    public static Integer getCountDown() {
        return countDown;
    }

    public static Integer getEndCountDown() {
        return endCountDown;
    }

    public static HashMap<Player, String> getPlayerKits() {
        return playerKits;
    }

    public static ArrayList<Player> getIngamePlayer() {
        return ingamePlayer;
    }

    public static ArrayList<Player> getSpectatorPlayers() {
        return spectatorPlayers;
    }

    public static void setTimeOutTime(Integer timeOutTime) {
        GameManager.timeOutTime = timeOutTime * 60;
    }

    public static Integer getTimeOutTime() {
        return timeOutTime;
    }

    public static void setPlayerKit(Player player, String name) {
        playerKits.remove(player);
        playerKits.put(player, name);
    }

    public static void setEndCountDown(Integer endCountDown) {
        GameManager.endCountDown = endCountDown;
    }

    public static void setEndgame(Endgame endgame) {
        GameManager.endgame = endgame;
    }

    public static String getPlayerKit(Player player) {
        if(!playerKits.containsKey(player)) return "default";
        return playerKits.get(player);
    }

    public static Endgame getEndgame() {
        return endgame;
    }

    public static void addIngamePlayer(Player player) {
        ingamePlayer.add(player);
    }

    public static void removeIngamePlayer(Player player) {
        TeamManager.removePlayerFromAllTeams(player);
        ingamePlayer.remove(player);
    }

    public static void addSpectatorPlayer(Player player) {
        spectatorPlayers.add(player);
    }

    public static void removeSpectatorPlayer(Player player) {
        spectatorPlayers.remove(player);
    }

    public static Integer getMaxPlayers() {
        return teamAmount * playersPerTeam;
    }

    public static Integer getPlayersPerTeam() {
        return playersPerTeam;
    }

    public static Integer getTeamAmount() {
        return teamAmount;
    }
}
