/*
 * PortalsCanceller, a Minecraft portals manipulation toolkit
 * Copyright (C) Volax
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.volax.portal.listeners;

import fr.volax.portal.PortalsCanceler;
import fr.volax.portal.utils.BlockUtil;
import fr.volax.portal.utils.ChatUtil;
import fr.volax.portal.utils.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.PortalCreateEvent;

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
        Player player = event.getPlayer();
        String playerName = player.getName();
        World world = player.getLocation().getWorld();
        int x = (int) player.getLocation().getX(), y = (int) player.getLocation().getY(), z = (int) player.getLocation().getZ();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().getType() == Material.EYE_OF_ENDER && event.getClickedBlock().getType() == Material.ENDER_PORTAL_FRAME) {
                List<World> worlds = ConfigBuilder.getInstance().getListWorlds("portals.end.create-portals-not-allowed-worlds");
                if (worlds.contains(world)) {
                    event.setCancelled(true);
                    String message = ChatUtil.TRIED_CREATE_END_NOTALLOWED.replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
                    if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                        ChatUtil.consoleMessage(message);
                    if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                        ChatUtil.logMessage(message);
                } else {
                    String message = ChatUtil.TRIED_CREATE_END_ALLOWED.replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());

                    if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                        ChatUtil.consoleMessage(message);
                    if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                        ChatUtil.logMessage(message);
                }
            } else if (player.getItemInHand().getType() == Material.FLINT_AND_STEEL && event.getClickedBlock().getType() == Material.OBSIDIAN) {
                List<World> worlds = ConfigBuilder.getInstance().getListWorlds("portals.nether.create-portals-not-allowed-worlds");

                if (worlds.contains(world)) {
                    event.setCancelled(true);
                    String message = ChatUtil.TRIED_CREATE_NETHER_NOTALLOWED.replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
                    if (ConfigBuilder.getInstance().getBoolean("logs.console"))
                        ChatUtil.consoleMessage(message);
                    if (ConfigBuilder.getInstance().getBoolean("logs.file"))
                        ChatUtil.logMessage(message);
                } else {
                    if (BlockUtil.doesBlockAround(event.getClickedBlock(), Material.PORTAL)) {
                        return;
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(PortalsCanceler.getInstance(), () -> {
                        if (BlockUtil.doesBlockAround(event.getClickedBlock(), Material.PORTAL)) {
                            String message = ChatUtil.TRIED_CREATE_NETHER_ALLOWED.replaceAll("%player%", playerName).replaceAll("%X%", String.valueOf(x)).replaceAll("%Y%", String.valueOf(y)).replaceAll("%Z%", String.valueOf(z)).replaceAll("%world%", world.getName());
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

    /*
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = event.getCause();

        if(cause.equals(PlayerTeleportEvent.TeleportCause.END_PORTAL) && !PortalsCanceler.getInstance().getConfig().getBoolean("portals.enter-end")){
            System.err.println(ConfigBuilder.getInstance().getString("messages.enter-end").replaceAll("%player%", player.getName()).replaceAll("%prefix%", PortalsCanceler.getInstance().PREFIX));
            System.err.println(ConfigBuilder.getInstance().getString("messages.coordonates").replaceAll("%X%", String.valueOf((int) player.getLocation().getX())).replaceAll("%Y%", String.valueOf((int) player.getLocation().getY())).replaceAll("%Z%", String.valueOf((int) player.getLocation().getZ())).replaceAll("%world%", player.getLocation().getWorld().getName()).replaceAll("%prefix%", PortalsCanceler.getInstance().PREFIX));
            event.setCancelled(true);
        }

        if(cause.equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) && !PortalsCanceler.getInstance().getConfig().getBoolean("portals.enter-nether")) {
            System.err.println(ConfigBuilder.getInstance().getString("messages.enter-nether").replaceAll("%player%", player.getName()).replaceAll("%prefix%", PortalsCanceler.getInstance().PREFIX));
            System.err.println(ConfigBuilder.getInstance().getString("messages.coordonates").replaceAll("%X%", String.valueOf((int) player.getLocation().getX())).replaceAll("%Y%", String.valueOf((int) player.getLocation().getY())).replaceAll("%Z%", String.valueOf((int) player.getLocation().getZ())).replaceAll("%world%", player.getLocation().getWorld().getName()).replaceAll("%prefix%", PortalsCanceler.getInstance().PREFIX));
            event.setCancelled(true);
        }
    }
     */
}