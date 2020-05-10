/*
 * PortalsCanceller, a Minecraft portals manipulation toolkit
 * Copyright (C) Volax <http://volax.fr>
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
package fr.volax.portal;

import fr.volax.portal.tools.ConfigBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PortalsCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 1) { helpMessage(sender); return false; }
        if(args[0].equalsIgnoreCase("reload")){
            if(sender.isOp() || sender.hasPermission(ConfigBuilder.getString("permissions.reload"))){
                MainPortal.getMain().reloadConfig();
                System.out.println(ConfigBuilder.getString("messages.config-reload"));
                return false;
            }
        }
        helpMessage(sender);
        return true;
    }

    private void helpMessage(CommandSender sender) {
        sender.sendMessage(MainPortal.getMain().PREFIX + " Usage: /portal reload");
    }
}