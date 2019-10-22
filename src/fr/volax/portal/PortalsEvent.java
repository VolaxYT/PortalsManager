package fr.volax.portal;

import org.bukkit.Material;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalsEvent implements Listener {

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
                        System.err.println("[PortalsCanceller]" + p.getName() + " Try to create a Aether portal");
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
                        System.err.println("[PortalsCanceller]" + p.getName() + " Try to create a End portal");
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
                        System.err.println("[PortalsCanceller]" + p.getName() + " Try to create a Nether portal");
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
    /**
     * When player try to enter in portal of Nether / End, he isn't teleport
     * if in config the option : 'portals.enter-nether' is set to false -> the event is cancelled
     * if in config the option : 'portals.enter-end' is set to false -> the event is cancelled
     */
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        Player p = event.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = event.getCause();
        if(cause.equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){
            if(!MainPortal.getMain().getConfig().getBoolean("portals.enter-end")){
                System.err.println("[PortalsCanceller]" + p.getName() + " Try to enter in End portal");
                event.setCancelled(true);
                return;
            }
        }

        if(cause.equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)){
            if(!MainPortal.getMain().getConfig().getBoolean("portals.enter-nether")) {
                System.err.println("[PortalsCanceller]" + p.getName() + " Try to enter in Nether portal");
                event.setCancelled(true);
                return;
            }
        }
    }
}
