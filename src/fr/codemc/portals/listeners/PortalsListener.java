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
package fr.codemc.portals.listeners;

import fr.codemc.portals.PortalsManager;
import fr.codemc.portals.utils.BlockUtil;
import fr.codemc.portals.utils.ChatUtil;
import fr.codemc.portals.utils.ConfigBuilder;
import fr.codemc.portals.utils.Translator;
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

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().getType() == Material.EYE_OF_ENDER && event.getClickedBlock().getType() == Material.ENDER_PORTAL_FRAME) {
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
            } else if (player.getItemInHand().getType() == Material.FLINT_AND_STEEL && event.getClickedBlock().getType() == Material.OBSIDIAN) {
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
                    if (BlockUtil.doesBlockAround(event.getClickedBlock(), Material.PORTAL)) {
                        return;
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(PortalsManager.getInstance(), () -> {
                        if (BlockUtil.doesBlockAround(event.getClickedBlock(), Material.PORTAL)) {
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