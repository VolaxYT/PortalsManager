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
package fr.volax.portal.commands;

import fr.volax.portal.PortalsCanceler;
import fr.volax.portal.gui.WorldsManager;
import fr.volax.portal.utils.ChatUtil;
import fr.volax.portal.utils.ConfigBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PortalsCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 1) { helpMessage(sender); return false; }

        if(args[0].equalsIgnoreCase("reload")){
            if(sender.hasPermission(ConfigBuilder.getInstance().getString(("permissions.reload")))){
                PortalsCanceler.getInstance().reloadConfig();
                ConfigBuilder.getInstance().configs.getConfig("messages.yml").reload();
                ChatUtil.sendMessage(sender, ChatUtil.CONFIG_RELOAD);
                return false;
            }else{
                ChatUtil.sendMessage(sender, ChatUtil.NO_PERMISSION);
                return false;
            }
        }

        if(args[0].equalsIgnoreCase("manage")){
            if(sender instanceof Player){
                if(sender.hasPermission(ConfigBuilder.getInstance().getString(("permissions.reload")))){
                    PortalsCanceler.getInstance().getGuiManager().open(((Player) sender), WorldsManager.class);
                    return false;
                }else{
                    ChatUtil.sendMessage(sender, ChatUtil.NO_PERMISSION);
                    return false;
                }
            }else{
                ChatUtil.sendMessage(sender, ChatUtil.NOT_PLAYER);
                return false;
            }
        }

        helpMessage(sender);
        return true;
    }

    private void helpMessage(CommandSender sender) {
        ChatUtil.sendMessage(sender, ChatUtil.HELP_MESSAGE);
    }
}