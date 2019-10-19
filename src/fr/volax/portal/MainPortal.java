package fr.volax.portal;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Small and easy Portals Canceller for Minecraft 1.7 to 1.12!
 * The project is on https://github.com/VolaxYT/PortalsCanceller
 *
 * @author Volax
 */

public class MainPortal extends JavaPlugin implements Listener {
    public static MainPortal instance;

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PortalsEvent(), this);
        getCommand("portal").setExecutor(new PortalsCommands());
        saveDefaultConfig();
        instance = this;
    }

    public static MainPortal getMain(){
        return instance;
    }
}
