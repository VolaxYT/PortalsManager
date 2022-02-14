package fr.volax.portalsmanager.listeners;

import fr.volax.portalsmanager.PortalsManager;
import fr.volax.portalsmanager.utils.BlockUtil;
import fr.volax.portalsmanager.utils.ChatUtil;
import fr.volax.portalsmanager.utils.ConfigBuilder;
import fr.volax.portalsmanager.utils.Translator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PortalsListener implements Listener {

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event){
        World world = event.getWorld();
        if(ConfigBuilder.getInstance().getListWorlds("portals.nether.create-portals-not-allowed-worlds").contains(world))
            event.setCancelled(true);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player     = event.getPlayer();
        String playerName = player.getName();
        World  world      = player.getLocation().getWorld();
        ItemStack usedItem = event.getItem();

        if(usedItem == null) return;

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (usedItem.getType() == Material.ENDER_EYE && event.getClickedBlock().getType() == Material.END_PORTAL_FRAME) {
                int x = (int) event.getClickedBlock().getLocation().getX(), y = (int) event.getClickedBlock().getLocation().getY(), z = (int) event.getClickedBlock().getLocation().getZ();
                List<World> worlds = ConfigBuilder.getInstance().getListWorlds("portals.end.create-portals-not-allowed-worlds");
                if (worlds.contains(world)) {
                    String message = Translator.translateMessage("create-end-prohibited").replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
                    Block block = event.getClickedBlock();

                    event.setCancelled(true);
                    if(block.getData() <= 3){
                        if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                            ChatUtil.consoleMessage(message);
                        if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                            ChatUtil.logMessage(message);
                        if (ConfigBuilder.getInstance().getBoolean("logs.player"))
                            ChatUtil.sendMessage(player, Translator.translateMessage("cant-create-end"));
                    }
                } else {
                    String message = Translator.translateMessage("create-end-allowed").replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
                    Block block = event.getClickedBlock();

                    if(block.getData() <= 3){
                        if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                            ChatUtil.consoleMessage(message);
                        if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                            ChatUtil.logMessage(message);
                    }
                }
            } else if (usedItem.getType() == Material.FLINT_AND_STEEL && event.getClickedBlock().getType() == Material.OBSIDIAN) {
                int x = (int) event.getClickedBlock().getLocation().getX(), y = (int) event.getClickedBlock().getLocation().getY(), z = (int) event.getClickedBlock().getLocation().getZ();
                List<World> worlds = ConfigBuilder.getInstance().getListWorlds("portals.nether.create-portals-not-allowed-worlds");

                if (worlds.contains(world)) {
                    event.setCancelled(true);
                    String message = Translator.translateMessage("create-nether-prohibited").replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
                    if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                        ChatUtil.consoleMessage(message);
                    if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                        ChatUtil.logMessage(message);
                    if (ConfigBuilder.getInstance().getBoolean("logs.player"))
                        ChatUtil.sendMessage(player, Translator.translateMessage("cant-create-nether"));
                } else {
                    if (BlockUtil.doesBlockAround(event.getClickedBlock(), Material.NETHER_PORTAL)) {
                        return;
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(PortalsManager.getInstance(), () -> {
                        if (BlockUtil.doesBlockAround(event.getClickedBlock(), Material.NETHER_PORTAL)) {
                            String message = Translator.translateMessage("create-nether-allowed").replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
                            if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                                ChatUtil.consoleMessage(message);
                            if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                                ChatUtil.logMessage(message);
                        }
                    }, 1);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        String playerName = player.getName();
        int x = (int) player.getLocation().getX(), y = (int) player.getLocation().getY(), z = (int) player.getLocation().getZ();
        World world = player.getLocation().getWorld();

        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){
            List<World> worlds = ConfigBuilder.getInstance().getListWorlds("portals.end.enter-portals-not-allowed-worlds");
            if(worlds.contains(world)){
                String message = Translator.translateMessage("enter-end-prohibited").replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
                if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                    ChatUtil.consoleMessage(message);
                if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                    ChatUtil.logMessage(message);
                if (ConfigBuilder.getInstance().getBoolean("logs.player"))
                    ChatUtil.sendMessage(player, Translator.translateMessage("cant-enter-end"));
                    player.teleport(player.getLocation().clone().add(0,2,3));
                event.setCancelled(true);
            }else{
                String message = Translator.translateMessage("enter-end-allowed").replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
                if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                    ChatUtil.consoleMessage(message);
                if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                    ChatUtil.logMessage(message);
            }
        }

        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            List<World> worlds = ConfigBuilder.getInstance().getListWorlds("portals.nether.enter-portals-not-allowed-worlds");
            if(worlds.contains(world)){
                String message = Translator.translateMessage("enter-nether-prohibited").replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
                if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                    ChatUtil.consoleMessage(message);
                if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                    ChatUtil.logMessage(message);
                if (ConfigBuilder.getInstance().getBoolean("logs.player"))
                    ChatUtil.sendMessage(player, Translator.translateMessage("cant-enter-nether"));
                event.setCancelled(true);
            }else{
                String message = Translator.translateMessage("enter-nether-allowed").replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
                if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                    ChatUtil.consoleMessage(message);
                if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                    ChatUtil.logMessage(message);
            }
        }
    }
}