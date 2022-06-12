package fr.volax.portalsmanager;

import fr.volax.portalsmanager.commands.CurrentWorldCommands;
import fr.volax.portalsmanager.commands.PortalsCommands;
import fr.volax.portalsmanager.gui.*;
import fr.volax.portalsmanager.listeners.PortalsListener;
import fr.volax.portalsmanager.utils.ConfigBuilder;
import fr.volax.portalsmanager.utils.FileManager;
import fr.volax.portalsmanager.utils.GuiBuilder;
import fr.volax.portalsmanager.utils.GuiManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Portals Manager for Minecraft 1.18!
 * The project is on https://github.com/VolaxYT/PortalsManager/
 *
 * @author Volax
 */

public class PortalsManager extends JavaPlugin {
    @Getter private static PortalsManager instance;
    @Getter private File debugFile;
    @Getter private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;

    @Override
    public void onEnable() {
        instance = this;
        this.registeredMenus = new HashMap<>();
        this.debugFile = new File(getDataFolder(), "logs.txt");

        ConfigBuilder configBuilder = new ConfigBuilder(new FileManager(this));
        saveDefaultConfig();

        for(String pathname : Objects.requireNonNull(new File(Bukkit.getServer().getPluginsFolder() + "/PortalsManager/langs").list((f, name) -> name.endsWith(".yml")))){
            configBuilder.configs.getConfig("langs/" + pathname).saveDefaultConfig();
        }

        getServer().getPluginManager().registerEvents(new PortalsListener(), this);
        getServer().getPluginManager().registerEvents(new GuiManager(), this);

        getCommand("portals").setExecutor(new PortalsCommands());
        getCommand("currentWorld").setExecutor(new CurrentWorldCommands());

        GuiManager.getInstance().addMenu(new WorldsManager());
        GuiManager.getInstance().addMenu(new WorldSettings());
        GuiManager.getInstance().addMenu(new PluginSettings());
        GuiManager.getInstance().addMenu(new PluginLanguage());
        GuiManager.getInstance().addMenu(new PluginLogs());

        Pattern versionPattern = Pattern.compile("1\\.(\\d{1,2})(?:\\.(\\d{1,2}))?");
        Matcher versionMatcher = versionPattern.matcher(this.getServer().getVersion());
        Integer versionInteger = null;

        if (versionMatcher.find()) {
            try {
                int minor = Integer.parseInt(versionMatcher.group(1));
                String patchS = versionMatcher.group(2);
                int patch = (patchS == null || patchS.isEmpty()) ? 0 : Integer.parseInt(patchS);
                versionInteger = (minor * 100) + patch;
            } catch (NumberFormatException ignored) {}
        }

        getServer().getConsoleSender().sendMessage("§bMinecraft " + versionInteger + " found.");

        if(!debugFile.exists()) {
            try {
                debugFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}