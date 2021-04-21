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

public class StartCountDownEndEvent extends Event {

    private ArrayList<Player> players;
    private static final HandlerList HANDLERS = new HandlerList();

    public StartCountDownEndEvent() {

    }

    public StartCountDownEndEvent(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
