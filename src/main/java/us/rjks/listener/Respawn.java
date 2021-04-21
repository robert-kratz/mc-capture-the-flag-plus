package us.rjks.listener;

import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import us.rjks.event.GameEndEvent;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.game.Main;
import us.rjks.utils.Config;
import us.rjks.utils.Spectator;
import us.rjks.utils.TeamManager;
import us.rjks.utils.TitleManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 21.04.2021 / 11:42
 *
 **************************************************************************/

public class Respawn implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        if (GameManager.getGame().equals(Game.INGAME)) {
            respawn(event.getEntity());
            GameManager.removeIngamePlayer(event.getEntity());
            Spectator.setSpectator(event.getEntity());

            if (TeamManager.getTeamsAlive() == 1) {

                TeamManager.Team team = null;
                for (Player player : GameManager.getIngamePlayer()) {
                    team = TeamManager.getTeamFromPlayer(player);
                    break;
                }

                Bukkit.getPluginManager().callEvent(new GameEndEvent(team.getPlayers(), team));
            }

        } else if (GameManager.getGame().equals(Game.END)) {
            respawn(event.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent event) {

    }

    private void respawn(final Player p) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable(){

            @Override
            public void run() {
                ((CraftPlayer)p).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
            }
        }, 1);
    }
}
