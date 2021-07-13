package fr.codemc.portals.commands;

import fr.codemc.portals.utils.ChatUtil;
import fr.codemc.portals.utils.ConfigBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CurrentWorldCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(player.hasPermission(ConfigBuilder.getInstance().getString(("permissions.currentworld"))))
                ChatUtil.sendMessage(player, ChatUtil.GET_WORLD.replaceAll("%world%", player.getWorld().getName()));
            else
                ChatUtil.sendMessage(sender, ChatUtil.NO_PERMISSION);
        }else
            ChatUtil.sendMessage(sender, ChatUtil.NOT_PLAYER);
        return false;
    }
}
