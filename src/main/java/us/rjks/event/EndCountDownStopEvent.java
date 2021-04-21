package us.rjks.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 18.04.2021 / 18:39
 *
 **************************************************************************/

public class EndCountDownStopEvent extends Event {

    private ArrayList<Player> winners;
    private String winTeam;
    private static final HandlerList HANDLERS = new HandlerList();

    public EndCountDownStopEvent() {

    }

    public EndCountDownStopEvent(ArrayList<Player> players, String winTeam) {
        this.winners = players;
        this.winTeam = winTeam;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public ArrayList<Player> getWinners() {
        return winners;
    }

    public String getWinTeam() {
        return winTeam;
    }
}
