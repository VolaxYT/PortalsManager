package fr.volax.portal;

import fr.volax.portal.tools.ConfigBuilder;
import org.bukkit.Material;
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
     * When a player tries to create a Nether Portal
     * if in config the option: 'portals.create-nether' is set to false -> the event is cancelled
     */
    @EventHandler
    public void onPortalCreate(PortalCreateEvent event){
        if(!ConfigBuilder.getBoolean("portals.create-nether")){
            event.setCancelled(true);
        }
    }

    /**
     * When player tries to right click
     */
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player p = event.getPlayer();
        /*
         * AETHER ADDON - When player tries to right click with a water bucket on block of glowstone
         * if in config the option : 'portals.create-aether' is set to false -> the event is cancelled
         */
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(p.getItemInHand().getType() == Material.WATER_BUCKET && event.getClickedBlock().getType() == Material.GLOWSTONE && !ConfigBuilder.getBoolean("portals.create-aether")) {
                System.err.println(ConfigBuilder.getString("messages.tried-place-aether").replaceAll("%player%", p.getName()));
                System.err.println(ConfigBuilder.getString("messages.coordonates").replaceAll("%X%", String.valueOf(p.getLocation().getX())).replaceAll("%Y%", String.valueOf(p.getLocation().getY())).replaceAll("%Z%", String.valueOf(p.getLocation().getZ())).replaceAll("%world%", p.getLocation().getWorld().getName()));
                event.setCancelled(true);

                /*
                 * When player tries to right click with a eye of ender on ender portal frame
                 * if in config the option: 'portals.create-end' is set to false -> the event is cancelled
                 */
            }else if(p.getItemInHand().getType() == Material.EYE_OF_ENDER && event.getClickedBlock().getType() == Material.ENDER_PORTAL_FRAME && !ConfigBuilder.getBoolean("portals.create-end")) {
                event.setCancelled(true);
                System.err.println(ConfigBuilder.getString("messages.tried-place-end").replaceAll("%player%", p.getName()));
                System.err.println(ConfigBuilder.getString("messages.coordonates").replaceAll("%X%", String.valueOf(p.getLocation().getX())).replaceAll("%Y%", String.valueOf(p.getLocation().getY())).replaceAll("%Z%", String.valueOf(p.getLocation().getZ())).replaceAll("%world%", p.getLocation().getWorld().getName()));

                /*
                 * When player tries to right click with a flint and steel on an obsidian block
                 * if in config the option: 'portals.create-nether' is set to false -> the event is cancelled
                 */
            }else if(p.getItemInHand().getType() == Material.FLINT_AND_STEEL && event.getClickedBlock().getType() == Material.OBSIDIAN && !ConfigBuilder.getBoolean("portals.create-nether")) {
                event.setCancelled(true);
                System.err.println(ConfigBuilder.getString("messages.tried-place-nether").replaceAll("%player%", p.getName()));
                System.err.println(ConfigBuilder.getString("messages.coordonates").replaceAll("%X%", String.valueOf(p.getLocation().getX())).replaceAll("%Y%", String.valueOf(p.getLocation().getY())).replaceAll("%Z%", String.valueOf(p.getLocation().getZ())).replaceAll("%world%", p.getLocation().getWorld().getName()));
            }
        }
    }

    /**
     * When player tries to use a dispenser for place fire on (all) blocks
     * if in config the option : 'portals.dispenser-use-flint' is set to false -> the event is cancelled
     */
    @EventHandler
    public void onBlockInteract(BlockDispenseEvent event){
        if(event.getItem().getType() == Material.FLINT_AND_STEEL && !ConfigBuilder.getBoolean("portals.dispenser-use-flint")) event.setCancelled(true);
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

        if(cause.equals(PlayerTeleportEvent.TeleportCause.END_PORTAL) && !MainPortal.getMain().getConfig().getBoolean("portals.enter-end")){
            System.err.println(ConfigBuilder.getString("messages.enter-end").replaceAll("%player%", p.getName()));
            System.err.println(ConfigBuilder.getString("messages.coordonates").replaceAll("%X%", String.valueOf(p.getLocation().getX())).replaceAll("%Y%", String.valueOf(p.getLocation().getY())).replaceAll("%Z%", String.valueOf(p.getLocation().getZ())).replaceAll("%world%", p.getLocation().getWorld().getName()));
            event.setCancelled(true);
            return;
        }

        if(cause.equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)&& !MainPortal.getMain().getConfig().getBoolean("portals.enter-nether")) {
            System.err.println(ConfigBuilder.getString("messages.enter-nether").replaceAll("%player%", p.getName()));
            System.err.println(ConfigBuilder.getString("messages.coordonates").replaceAll("%X%", String.valueOf(p.getLocation().getX())).replaceAll("%Y%", String.valueOf(p.getLocation().getY())).replaceAll("%Z%", String.valueOf(p.getLocation().getZ())).replaceAll("%world%", p.getLocation().getWorld().getName()));
            event.setCancelled(true);
            return;
        }
    }
}