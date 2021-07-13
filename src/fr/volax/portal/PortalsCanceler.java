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
package fr.volax.portal;

import fr.volax.portal.commands.PortalsCommands;
import fr.volax.portal.gui.WorldSettings;
import fr.volax.portal.gui.WorldsManager;
import fr.volax.portal.listeners.PortalsListener;
import fr.volax.portal.utils.ConfigBuilder;
import fr.volax.portal.utils.FileManager;
import fr.volax.portal.utils.GuiBuilder;
import fr.volax.portal.utils.GuiManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Small and easy Portals Canceller for Minecraft 1.8 to 1.17!
 * The project is on https://github.com/VolaxYT/PortalsCanceller
 *
 * @author Volax
 */

public class PortalsCanceler extends JavaPlugin {
    public static PortalsCanceler instance;
    private GuiManager guiManager;

    public final File debugFile = new File(getDataFolder(), "logs.txt");
    private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;

    @Override
    public void onEnable() {
        instance = this;
        this.registeredMenus = new HashMap<>();
        this.guiManager = new GuiManager();

        ConfigBuilder configBuilder = new ConfigBuilder(new FileManager(this));
        saveDefaultConfig();
        configBuilder.configs.getConfig("messages.yml").saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PortalsListener(), this);
        getServer().getPluginManager().registerEvents(new GuiManager(), this);
        getCommand("portals").setExecutor(new PortalsCommands());

        this.guiManager.addMenu(new WorldsManager());
        this.guiManager.addMenu(new WorldSettings());

        if(!debugFile.exists()) {
            try {
                debugFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static PortalsCanceler getInstance() {
        return instance;
    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}