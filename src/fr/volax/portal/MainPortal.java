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
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Small and easy Portals Canceller for Minecraft 1.8 to 1.12!
 * The project is on https://github.com/VolaxYT/PortalsCanceller
 *
 * @author Volax
 */

public class MainPortal extends JavaPlugin {
    public static MainPortal instance;
    public String PREFIX;

    @Override
    public void onEnable() {
        instance = this;

        PREFIX = ConfigBuilder.getString("messages.prefix");

        getServer().getPluginManager().registerEvents(new PortalsEvent(), this);
        getCommand("portal").setExecutor(new PortalsCommands());

        saveDefaultConfig();
    }

    public static MainPortal getMain() {
        return instance;
    }
}