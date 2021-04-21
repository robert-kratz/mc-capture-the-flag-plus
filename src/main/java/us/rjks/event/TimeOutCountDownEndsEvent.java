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
 *  Erstellt: 18.04.2021 / 18:39
 *
 **************************************************************************/

public class TimeOutCountDownEndsEvent extends Event {

    private ArrayList<Player> alive;
    private ArrayList<TeamManager.Team> teams;
    private static final HandlerList HANDLERS = new HandlerList();

    public TimeOutCountDownEndsEvent() {

    }

    public TimeOutCountDownEndsEvent(ArrayList<Player> alive, ArrayList<TeamManager.Team> teams) {
        this.alive = alive;
        this.teams = teams;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public ArrayList<Player> getAlive() {
        return alive;
    }

    public ArrayList<TeamManager.Team> getTeams() {
        return teams;
    }
}
