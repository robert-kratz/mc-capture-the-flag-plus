package us.rjks.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import us.rjks.utils.TeamManager;

import java.util.ArrayList;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 21.04.2021 / 19:47
 *
 **************************************************************************/

public class GameEndEvent extends Event {

    private ArrayList<TeamManager.TeamPlayer> players;
    private TeamManager.Team winner;
    private static final HandlerList HANDLERS = new HandlerList();

    public GameEndEvent() {

    }

    public GameEndEvent(ArrayList<TeamManager.TeamPlayer> players, TeamManager.Team winner) {
        this.players = players;
        this.winner = winner;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public TeamManager.Team getWinner() {
        return winner;
    }

    public ArrayList<TeamManager.TeamPlayer> getPlayers() {
        return players;
    }
}
