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