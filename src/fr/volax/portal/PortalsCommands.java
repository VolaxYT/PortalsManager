package fr.volax.portal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PortalsCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(msg.equalsIgnoreCase("portal")){
            if(args[0].equalsIgnoreCase("reload")){
                if(sender.hasPermission(MainPortal.getMain().getConfig().getString("permissions.reload"))){
                    MainPortal.getMain().reloadConfig();
                    System.out.println("[PortalsCanceller] The Configuration file has been reloaded");
                }
            }
        }
        return false;
    }
}
