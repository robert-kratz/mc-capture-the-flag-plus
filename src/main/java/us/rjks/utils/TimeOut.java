package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import sun.awt.ConstrainableGraphics;
import us.rjks.event.StartCountDownEndEvent;
import us.rjks.event.TimeOutCountDownEndsEvent;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.game.Main;

import java.util.ArrayList;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 18.04.2021 / 21:28
 *
 **************************************************************************/

public class TimeOut {

    private int counter, countdown;
    private boolean run = false;

    public TimeOut() {
        this.countdown = GameManager.getTimeOutTime();

        counter = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            public void run() {
                if(run && GameManager.getGame().equals(Game.INGAME)) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if(TeamManager.getTeamFromPlayer(all) != null) {
                            TeamManager.Team team = TeamManager.getTeamFromPlayer(all);
                            TitleManager.sendActionBar(all, Config.getMessageAsString("ingame_actionbar_player")
                                    .replaceAll("%team_color%", team.getColor())
                                    .replaceAll("%team_size%", Integer.toString(team.getPlayers().size()))
                                    .replaceAll("%team_max_size%", Integer.toString(team.getMaxPlayers()))
                                    .replaceAll("%team_prefix%", team.getPrefix())
                                    .replaceAll("%team_suffix%", team.getSuffix())
                                    .replaceAll("%team_name%", team.getName())
                                    .replaceAll("%timeout%", parseToClockFormat(getCountdown())));
                        } else {
                            TitleManager.sendActionBar(all, Config.getMessageAsString("ingame_actionbar_spectator"));
                        }
                    }
                    if(countdown == (60*20) || countdown == (60*15) || countdown == (60*10)
                            || countdown == (60*5) || countdown == (60*3) || countdown == (60*2) || countdown == (60*1)) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Config.getMessageAsString("end_countdown_for_restart")
                                    .replaceAll("%timeout%", parseToClockFormat(countdown)));
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 10, 10);
                        }
                    } else if(countdown == (30) || countdown == (10) || countdown == (5)
                            || countdown == (3) || countdown == (2) || countdown == (1)){
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Config.getMessageAsString("end_countdown_for_restart")
                                            .replaceAll("%timeout%", String.valueOf(getCountdown())));
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 10, 10);
                        }
                    }
                    if(countdown == 0) {
                        ArrayList<TeamManager.Team> teams = new ArrayList<>();
                        for (TeamManager.Team t : TeamManager.getTeams()) {
                            if(t.isAlive()) {
                                teams.add(t);
                            }
                        }
                        Bukkit.getPluginManager().callEvent(new TimeOutCountDownEndsEvent(GameManager.getIngamePlayer(), teams));
                        deleteCounter();
                    }
                    countdown--;
                }
            }
        }, 20L, 20L);
    }

    public void start() {
        run = true;
    }

    public void hold() {
        countdown = GameManager.getTimeOutTime();
    }

    private void deleteCounter() {
        countdown = GameManager.getTimeOutTime();
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

    private void startGame() {
        Bukkit.getPluginManager().callEvent(new StartCountDownEndEvent(GameManager.getIngamePlayer()));
    }

    public static String parseToClockFormat(int totalSecs) {
        int hours, minutes, seconds;
        hours = totalSecs / 3600;
        minutes = (totalSecs % 3600) / 60;
        seconds = totalSecs % 60;

        String msg = Config.getMessageAsString("timer_format")
                .replaceAll("%hours%", String.valueOf(hours))
                .replaceAll("%minutes%", String.valueOf(minutes))
                .replaceAll("%seconds%", String.valueOf(seconds));

        return msg;
    }

    private String parseToMinutes(int i) {
        return Math.round(i / 60) + " " + Config.getMessageAsString("minute_message");
    }

}
