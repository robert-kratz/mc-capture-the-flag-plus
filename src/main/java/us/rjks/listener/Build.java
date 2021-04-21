package us.rjks.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.projectiles.ProjectileSource;
import us.rjks.game.Game;
import us.rjks.game.GameManager;
import us.rjks.utils.TeamManager;
import us.rjks.utils.TimeOut;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 21.04.2021 / 09:46
 *
 **************************************************************************/

public class Build implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (GameManager.getGame().equals(Game.LOBBY)) {
            e.setCancelled(true);
        } else if (GameManager.getGame().equals(Game.INGAME)) {
            if (GameManager.getSpectatorPlayers().contains(e.getEntity())) {
                e.setCancelled(true);
            } else {
                if (GameManager.getTimeOutTime() - GameManager.getTimeOut().getCountdown() < 3) {
                    e.setCancelled(true);
                }
            }
        } else if (GameManager.getGame().equals(Game.END)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (GameManager.getGame().equals(Game.LOBBY) || GameManager.getGame().equals(Game.END)) {
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e)
    {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (GameManager.getGame().equals(Game.LOBBY) || GameManager.getGame().equals(Game.END)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPick(PlayerPickupItemEvent e) {
        if (GameManager.getGame().equals(Game.LOBBY) || GameManager.getGame().equals(Game.END)) {
            if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLeaves(LeavesDecayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent e) {
        if (GameManager.getGame().equals(Game.LOBBY) || GameManager.getGame().equals(Game.END)) {
            e.setDeathMessage(null);
            e.setKeepInventory(true);
        }
    }

    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent event) {
        if (GameManager.getGame().equals(Game.LOBBY)) {
            event.setCancelled(true);
        } else if (GameManager.getGame().equals(Game.INGAME)) {
            if ((event.getEntity() instanceof Player && event.getDamager() instanceof Player)) {
                if (TeamManager.getTeamFromPlayer(((Player) event.getEntity()).getPlayer()).getName().equalsIgnoreCase(TeamManager.getTeamFromPlayer(((Player) event.getDamager()).getPlayer()).getName())) {
                    event.setCancelled(true);
                } else {
                    event.setCancelled(false);
                }
            } else if ((event.getDamager() instanceof Projectile && event.getEntity() instanceof Player)) {
                ProjectileSource shooter = ((Projectile) event.getDamager()).getShooter();
                if (!(shooter instanceof Player)) {
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onDamageEvent(EntityDamageEvent event) {
        if (GameManager.getGame().equals(Game.LOBBY)) {
            event.setCancelled(true);
        } else if (GameManager.getGame().equals(Game.INGAME)) {

        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (GameManager.getGame().equals(Game.LOBBY) || GameManager.getGame().equals(Game.END)) {
            if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onInt(PlayerInteractEvent event){
        try {
            if (GameManager.getSpectatorPlayers().contains(event.getPlayer())) {
                event.setCancelled(true);
            }
            if (event.getClickedBlock().getType() == Material.BED_BLOCK) {
                event.setCancelled(true);
            }
        } catch(Exception e1){

        }
    }

    @EventHandler
    public void onErfolg(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBrake(BlockBreakEvent e) {
        if (GameManager.getGame().equals(Game.LOBBY)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBrake(PlayerArmorStandManipulateEvent e) {
        if (GameManager.getGame().equals(Game.LOBBY)) {
            if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (GameManager.getGame().equals(Game.LOBBY)) {
            if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                e.setCancelled(true);
            }
        }
    }

}
