package fr.volax.portalsmanager;

import fr.volax.portalsmanager.commands.CurrentWorldCommands;
import fr.volax.portalsmanager.commands.PortalsCommands;
import fr.volax.portalsmanager.gui.*;
import fr.volax.portalsmanager.listeners.PortalsListener;
import fr.volax.portalsmanager.utils.ConfigBuilder;
import fr.volax.portalsmanager.utils.FileManager;
import fr.volax.portalsmanager.utils.GuiBuilder;
import fr.volax.portalsmanager.utils.GuiManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Portals Manager for Minecraft 1.18!
 * The project is on https://github.com/VolaxYT/PortalsManager/
 *
 * @author Volax
 */

public class PortalsManager extends JavaPlugin {
    public  static PortalsManager                               instance;
    private GuiManager guiManager;
    public         File                                         debugFile;
    private        Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;
    private int serverVersion;

    @Override
    public void onEnable() {
        instance = this;
        this.registeredMenus = new HashMap<>();
        this.guiManager = new GuiManager();
        this.debugFile = new File(getDataFolder(), "logs.txt");

        ConfigBuilder configBuilder = new ConfigBuilder(new FileManager(this));
        saveDefaultConfig();
        configBuilder.configs.getConfig("langs/fr_FR.yml").saveDefaultConfig();
        configBuilder.configs.getConfig("langs/en_US.yml").saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PortalsListener(), this);
        getServer().getPluginManager().registerEvents(new GuiManager(), this);

        getCommand("portals").setExecutor(new PortalsCommands());
        getCommand("currentWorld").setExecutor(new CurrentWorldCommands());

        this.guiManager.addMenu(new WorldsManager());
        this.guiManager.addMenu(new WorldSettings());
        this.guiManager.addMenu(new PluginSettings());
        this.guiManager.addMenu(new PluginLanguage());
        this.guiManager.addMenu(new PluginLogs());

        Pattern versionPattern = Pattern.compile("1\\.(\\d{1,2})(?:\\.(\\d{1,2}))?");
        Matcher versionMatcher = versionPattern.matcher(this.getServer().getVersion());
        Integer versionInteger = null;

        if (versionMatcher.find()) {
            try {
                int minor = Integer.parseInt(versionMatcher.group(1));
                String patchS = versionMatcher.group(2);
                int patch = (patchS == null || patchS.isEmpty()) ? 0 : Integer.parseInt(patchS);
                versionInteger = (minor * 100) + patch;
                getLogger().info("Detection de Minecraft " + versionMatcher.group());
            } catch (NumberFormatException ignored) {}
        }

        serverVersion = versionInteger;
        getServer().getConsoleSender().sendMessage("§bVersion du serveur: " + serverVersion);

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