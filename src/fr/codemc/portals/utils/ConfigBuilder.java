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
package fr.codemc.portals.utils;

import fr.codemc.portals.PortalsManager;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ConfigBuilder {
    public        FileManager   configs;
    public static ConfigBuilder instance;

    public ConfigBuilder(FileManager fileManager) {
        this.configs = fileManager;
        instance = this;
    }

    public String getString(String path, String config){
        return configs.getConfig(config).get().getString(path).replaceAll("&","§");
    }

    public int getInt(String path, String config){
        return configs.getConfig(config).get().getInt(path);
    }

    public double getDouble(String path, String config) {
        return configs.getConfig(config).get().getDouble(path);
    }

    public double getDouble(String path) {
        return PortalsManager.getInstance().getConfig().getDouble(path);
    }

    public Boolean getBoolean(String path, String config){
        return configs.getConfig(config).get().getBoolean(path);
    }

    public void setString(String path, String data, String config){
        configs.getConfig(config).set(path, data);
        configs.getConfig(config).save();
    }

    public void setInt(String path, int data, String config){
        configs.getConfig(config).set(path, data);
        configs.getConfig(config).save();
        configs.getConfig(config).reload();
    }

    public void setBoolean(String path, boolean data, String config){
        configs.getConfig(config).set(path, data);
        configs.getConfig(config).save();
    }

    public void setDouble(String path, double data, String config){
        configs.getConfig(config).set(path, data);
        configs.getConfig(config).save();
    }

    public String getString(String path){
        return PortalsManager.getInstance().getConfig().getString(path).replaceAll("&","§");
    }

    public int getInt(String path){
        return PortalsManager.getInstance().getConfig().getInt(path);
    }

    public boolean getBoolean(String path){
        return PortalsManager.getInstance().getConfig().getBoolean(path);
    }

    public void set(String path, Object data){
        PortalsManager.getInstance().getConfig().set(path, data);
        PortalsManager.getInstance().saveConfig();
        PortalsManager.getInstance().reloadConfig();
    }

    public void set(String path, Object data, String config){
        configs.getConfig(config).set(path, data);
        configs.getConfig(config).save();
        configs.getConfig(config).reload();
    }

    public List<?> getList(String path){
        return PortalsManager.getInstance().getConfig().getList(path);
    }

    public Object get(String path){
        return PortalsManager.getInstance().getConfig().get(path);
    }

    public Object get(String path, String config){
        return configs.getConfig(config).get(path);
    }

    public List<?> getList(String path, String config){
        return configs.getConfig(config).get().getList(path);
    }

    public List<String> getListString(String path){
        return (List<String>) PortalsManager.getInstance().getConfig().getList(path);
    }

    public List<String> getListString(String path, String config){
        return (List<String>) configs.getConfig(config).get().getList(path);
    }

    public List<Integer> getListInt(String path){
        return (List<Integer>) PortalsManager.getInstance().getConfig().getList(path);
    }

    public List<Integer> getListInt(String path, String config){
        return (List<Integer>) configs.getConfig(config).get().getList(path);
    }

    public List<World> getListWorlds(String path, String config){
        List<World> worlds = new ArrayList<>();

        for(String worldName : getListString(path, config)){
            worlds.add(Bukkit.getWorld(worldName));
        }

        return worlds;
    }

    public List<World> getListWorlds(String path){
        List<World> worlds = new ArrayList<>();

        for(String worldName : getListString(path)){
            worlds.add(Bukkit.getWorld(worldName));
        }

        return worlds;
    }

    public static ConfigBuilder getInstance() {
        return instance;
    }
}
