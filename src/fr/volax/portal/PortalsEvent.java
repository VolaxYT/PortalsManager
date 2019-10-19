package fr.volax.portal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalsEvent implements Listener {
    /**
     * When player try to enter in portal of Nether, he is teleport to back location
     * if in config the option : 'portals.enter-nether' is set to false -> the event is cancelled
     */
    @EventHandler
    public void onPortal(EntityPortalEnterEvent event){
        Player player = (Player) event.getEntity();
        player.teleport(new Location(player.getWorld(), player.getLocation().getX() - 1, player.getLocation().getY(), player.getLocation().getZ() - 1));
    }

    /**
     * When player try to create Nether or End portal -> the event is cancelled
     * if in config the option : 'portals.create-end' is set to false -> the event is cancelled
     */
    @EventHandler
    public void onPortalCreate(PortalCreateEvent event){
        if(!MainPortal.getMain().getConfig().getBoolean("portals.create-nether")){
            event.setCancelled(true);
        }
    }

    /**
     * When player try to right click with...
     */
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player p = event.getPlayer();
        /**
         * AETHER ADDON - When player try to right click with a water bucket on block of glowstone
         * if in config the option : 'portals.create-end' is set to false -> the event is cancelled
         */
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(p.getItemInHand().getType() == Material.WATER_BUCKET){
                if(event.getClickedBlock().getType() == Material.GLOWSTONE){
                    if(!MainPortal.getMain().getConfig().getBoolean("portals.create-aether")) {
                        event.setCancelled(true);
                    }
                }
                /**
                 * When player try to right click with a eye of ender on ender portal frame
                 * if in config the option : 'portals.create-end' is set to false -> the event is cancelled
                 */
            }else if(p.getItemInHand().getType() == Material.EYE_OF_ENDER){
                if(event.getClickedBlock().getType() == Material.ENDER_PORTAL_FRAME){
                    if(!MainPortal.getMain().getConfig().getBoolean("portals.create-end")) {
                        event.setCancelled(true);
                    }
                }
                /**
                 * When player try to right click with a flint and steal on obsidian block
                 * if in config the option : 'portals.create-nether' is set to false -> the event is cancelled
                 */
            }else if(p.getItemInHand().getType() == Material.FLINT_AND_STEEL) {
                if (event.getClickedBlock().getType() == Material.OBSIDIAN) {
                    if (!MainPortal.getMain().getConfig().getBoolean("portals.create-nether")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    /**
     * When player try to use dispenser for place fire on blocks ( all blocks of game )
     * if in config the option : 'portals.dispenser-use-flint' is set to false -> the event is cancelled
     */
    @EventHandler
    public void onBlockInteract(BlockDispenseEvent event){
        if(event.getItem().getType() == Material.FLINT_AND_STEEL) {
            if (!MainPortal.getMain().getConfig().getBoolean("portals.dispenser-use-flint")){
                event.setCancelled(true);
            }
        }
    }
}
