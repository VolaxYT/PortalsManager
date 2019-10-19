package fr.volax.portal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Small and easy Portal Canceller for Minecraft 1.7.10 !
 * The project is on https://github.com/VolaxYT/PortalsCanceller
 *
 * @author Volax
 */

public class MainPortal extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
    }

    /**
     * When player try to enter in portal of Nether and End, he is teleport to back location
     */
    @EventHandler
    public void onPortal(EntityPortalEnterEvent event){
       Player player = (Player) event.getEntity();
       player.teleport(new Location(player.getWorld(), player.getLocation().getX() - 1, player.getLocation().getY(), player.getLocation().getZ() - 1));
   }

    /**
     * When player try to create Nether or End portal -> the event is cancelled
     */
   @EventHandler
   public void onPortalCreate(PortalCreateEvent event){
        event.setCancelled(true);
   }

    /**
     * When player try to right click with...
     */
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player p = event.getPlayer();
        /**
         * AETHER ADDON - When player try to right click with a water bucket on block of glowstone -> the event is cancelled
         */
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(p.getItemInHand().getType() == Material.WATER_BUCKET){
                if(event.getClickedBlock().getType() == Material.GLOWSTONE){
                    event.setCancelled(true);
                }
            }
            /**
             * When player try to right click with a eye of ender on ender portal frame -> the event is cancelled
             */
        }else if(p.getItemInHand().getType() == Material.EYE_OF_ENDER){
            if(event.getClickedBlock().getType() == Material.ENDER_PORTAL_FRAME){
                event.setCancelled(true);
            }
            /**
             * When player try to right click with a eye of ender on ender portal frame -> the event is cancelled
             */
        }else if(p.getItemInHand().getType() == Material.FLINT_AND_STEEL){
            if(event.getClickedBlock().getType() == Material.OBSIDIAN){
                event.setCancelled(true);
            }
        }
    }
}
