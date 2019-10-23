package fr.volax.portal;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Small and easy Portals Canceller for Minecraft 1.8 to 1.12!
 * The project is on https://github.com/VolaxYT/PortalsCanceller
 *
 * @author Volax
 */

public class MainPortal extends JavaPlugin implements Listener {
    
    public static MainPortal instance;

    @Override
    public void onEnable() {
        instance = this;
        
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PortalsEvent(), this);
        
        getCommand("portal").setExecutor(new PortalsCommands());
        
        saveDefaultConfig();
    }
    
    public static MainPortal getMain() {
        return instance;
    }
}
