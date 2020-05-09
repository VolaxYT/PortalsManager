package fr.volax.portal;

import fr.volax.portal.tools.ConfigBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PortalsCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 1) { helpMessage(sender); return false; }
        if(args[0].equalsIgnoreCase("reload")){
            if(sender.isOp() || sender.hasPermission(ConfigBuilder.getString("permissions.reload"))){
                MainPortal.getMain().reloadConfig();
                System.out.println(ConfigBuilder.getString("messages.config-reload"));
                return false;
            }
        }
        helpMessage(sender);
        return true;
    }

    private void helpMessage(CommandSender sender) {
        sender.sendMessage(MainPortal.getMain().PREFIX + " Usage: /portal reload");
    }
}