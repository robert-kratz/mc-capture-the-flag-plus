package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import us.rjks.event.StartCountDownEndEvent;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.game.GameProperty;
import us.rjks.game.Main;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.04.2021 / 16:00
 *
 **************************************************************************/

public class Countdown {

    private int counter, countdown;
    private boolean run = false;

    public Countdown() {
        this.countdown = GameManager.getCountDown();

        counter = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            public void run() {
                if(run && GameManager.getGame().equals(Game.LOBBY)) {
                    if(TeamManager.getFilledTeams() == 1 && TeamManager.getPlayersNotInTeam() == 0) {
                        hold();
                        //ALL PLAYERS IN ONE TEAM
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            TitleManager.sendActionBar(all, Config.getMessageAsString("min_two_teams_required"));
                        }
                    } else {
                        for (Player all : GameManager.getIngamePlayer()) {
                            TitleManager.sendActionBar(all, Config.getMessageAsString("lobby_action_bar")
                                    .replaceAll("%countdown%", Integer.toString(countdown)));
                        }

                        //Result of Mapvoting
                        if (countdown == 5) {
                            if (GameManager.getCurrentMap() == null) {
                                String name;

                                if (GameProperty.manager.getProperty("voting").getPlayers().isEmpty()) {
                                    name = MapManager.getRandomMap().getName();
                                } else {
                                    name = GameProperty.manager.getProperty("voting").getHighestValue().toString();
                                }

                                MapManager.Map map = MapManager.getMapFromName(name);

                                if (!map.isLoaded()) {
                                    map.loadMap();
                                    GameManager.setCurrentMap(map);
                                    for(Player all : GameManager.getIngamePlayer()) {
                                        all.sendMessage(Config.getMessageAsString("vote_ended_result")
                                                .replaceAll("%mapname%", map.getName()));
                                        all.playSound(all.getLocation(), Sound.ANVIL_USE, 10, 10);
                                    }
                                }
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    ScoreBoardManager.setScoreboard(all);
                                }
                            } else {
                                //MAP WAS FORCEMAPPED
                                MapManager.Map map = MapManager.getMapFromName(GameManager.getCurrentMap().getName());

                                if (!map.isLoaded()) {
                                    map.loadMap();
                                    GameManager.setCurrentMap(map);
                                }
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    ScoreBoardManager.setScoreboard(all);
                                }

                                for(Player all : GameManager.getIngamePlayer()) {
                                    all.sendMessage(Config.getMessageAsString("vote_ended_result")
                                            .replaceAll("%mapname%", GameManager.getCurrentMap().getName()));
                                    all.playSound(all.getLocation(), Sound.ANVIL_USE, 10, 10);
                                }
                            }
                        }

                        if(countdown == 90 || countdown == 60 ||countdown == 30 || countdown == 20 || countdown == 10 || countdown == 5 || countdown == 3 || countdown == 2 || countdown == 1) {
                            for(Player all : GameManager.getIngamePlayer()) {
                                all.sendMessage(Config.getMessageAsString("lobby_countdown_chat")
                                        .replaceAll("%countdown%", Integer.toString(countdown)));
                                all.playSound(all.getLocation(), Sound.NOTE_BASS, 10, 10);
                            }
                        }
                        if(countdown == 0) {
                            for(Player all : GameManager.getIngamePlayer()) {
                                TitleManager.sendActionBar(all, Config.getMessageAsString("lobby_countdown_starts_now_chat"));
                                all.sendMessage(Config.getMessageAsString("lobby_countdown_starts_now_actionbar"));
                                all.playSound(all.getLocation(), Sound.NOTE_BASS, 10, 10);
                            }
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.closeInventory();
                            }
                            Bukkit.getPluginManager().callEvent(new StartCountDownEndEvent(GameManager.getIngamePlayer()));
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.closeInventory();
                            }
                            deleteCounter();
                        }
                        countdown--;
                    }
                } else if(GameManager.getGame().equals(Game.LOBBY)) {
                    for (Player all : GameManager.getIngamePlayer()) {
                        if ((GameManager.getMinPlayerStart() - GameManager.getIngamePlayer().size()) == 1) {
                            TitleManager.sendActionBar(all, Config.getMessageAsString("lobby_waiting_for_player_singular").replaceAll("%amount%", GameManager.getMinPlayerStart() - GameManager.getIngamePlayer().size() + ""));
                        } else {
                            TitleManager.sendActionBar(all, Config.getMessageAsString("lobby_waiting_for_player_plural").replaceAll("%amount%", GameManager.getMinPlayerStart() - GameManager.getIngamePlayer().size() + ""));
                        }
                    }
                }
            }
        }, 20L, 20L);
    }

    public void start() {
        run = true;
    }

    public void stop() {
        countdown = GameManager.getCountDown();
        run = false;
        if (GameManager.getCurrentMap() != null && GameManager.getCurrentMap().isLoaded()) {
            GameManager.getCurrentMap().unloadMap();
            GameManager.setCurrentMap(null);
        }
        for(Player all : GameManager.getIngamePlayer()) {
            all.sendMessage(Config.getMessageAsString("not_enough_player_online"));
            all.setLevel(0);
        }
    }

    public void hold() {
        countdown = GameManager.getCountDown();
    }

    private void deleteCounter() {
        countdown = GameManager.getCountDown();
        Bukkit.getScheduler().cancelTask(counter);
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public boolean isRunning() {
        return run;
    }

}
