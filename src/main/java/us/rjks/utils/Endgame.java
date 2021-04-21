package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import us.rjks.event.EndCountDownStopEvent;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.game.Main;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 18.04.2021 / 14:04
 *
 **************************************************************************/

public class Endgame {

    private int counter, countdown;
    private boolean run = false;

    public Endgame() {
        this.countdown = GameManager.getEndCountDown();

        counter = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            public void run() {
                if(run && GameManager.getGame().equals(Game.END)) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        TitleManager.sendActionBar(all, "§8» §cThe Server Restarts in §e" + countdown + " seconds");
                    }
                    if(countdown == 90 || countdown == 60 ||countdown == 30 || countdown == 20 || countdown == 10 || countdown == 5 || countdown == 3 || countdown == 2 || countdown == 1) {
                        for(Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage("§8» §7The Server stops in §e" + countdown + " seconds§7.");
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 10, 10);
                        }
                    }
                    if(countdown == 0) {
                        Bukkit.getPluginManager().callEvent(new EndCountDownStopEvent());
                    }
                    countdown--;
                }
            }
        }, 20L, 20L);
    }

    public void start() {
        GameManager.setGame(Game.END);
        for (Player all : Bukkit.getOnlinePlayers()) {
            ScoreBoardManager.setScoreboard(all);
        }
        run = true;
    }

    public void deleteCounter() {
        countdown = GameManager.getEndCountDown();
        Bukkit.getScheduler().cancelTask(counter);
    }

    public boolean isRunning() {
        return run;
    }

    public void stopGame() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            all.kickPlayer("§cThe Server has been stopped.");
        }
    }

}
