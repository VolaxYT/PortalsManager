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
package fr.codemc.portals;

import fr.codemc.portals.commands.CurrentWorldCommands;
import fr.codemc.portals.commands.PortalsCommands;
import fr.codemc.portals.gui.WorldSettings;
import fr.codemc.portals.gui.WorldsManager;
import fr.codemc.portals.listeners.PortalsListener;
import fr.codemc.portals.utils.ConfigBuilder;
import fr.codemc.portals.utils.FileManager;
import fr.codemc.portals.utils.GuiBuilder;
import fr.codemc.portals.utils.GuiManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Small and easy Portals Manager for Minecraft 1.8 to 1.17!
 * The project is on https://github.com/VolaxYT/Manager
 *
 * @author Volax
 */

public class PortalsManager extends JavaPlugin {
    public  static PortalsManager                               instance;
    private        GuiManager                                   guiManager;
    public         File                                         debugFile;
    private        Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;

    @Override
    public void onEnable() {
        instance = this;
        this.registeredMenus = new HashMap<>();
        this.guiManager = new GuiManager();
        this.debugFile = new File(getDataFolder(), "logs.txt");

        ConfigBuilder configBuilder = new ConfigBuilder(new FileManager(this));
        saveDefaultConfig();
        configBuilder.configs.getConfig("messages.yml").saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PortalsListener(), this);
        getServer().getPluginManager().registerEvents(new GuiManager(), this);

        getCommand("portals").setExecutor(new PortalsCommands());
        getCommand("currentWorld").setExecutor(new CurrentWorldCommands());

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

    public static PortalsManager getInstance() {
        return instance;
    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}