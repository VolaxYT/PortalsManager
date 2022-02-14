package fr.volax.portalsmanager.commands;

import fr.volax.portalsmanager.PortalsManager;
import fr.volax.portalsmanager.gui.PluginSettings;
import fr.volax.portalsmanager.gui.WorldsManager;
import fr.volax.portalsmanager.utils.ChatUtil;
import fr.volax.portalsmanager.utils.ConfigBuilder;
import fr.volax.portalsmanager.utils.Translator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PortalsCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 1) { ChatUtil.sendMessage(sender, Translator.translateMessage("commands.portals.help-message")); return false; }

        if(args[0].equalsIgnoreCase("reload")){
            if(sender.hasPermission(ConfigBuilder.getInstance().getString(("permissions.reload")))){
                PortalsManager.getInstance().reloadConfig();
                ConfigBuilder.getInstance().configs.getConfig("messages.yml").reload();
                ConfigBuilder.getInstance().configs.getConfig("english.yml").reload();
                ConfigBuilder.getInstance().configs.getConfig("french.yml").reload();
                ChatUtil.sendMessage(sender, Translator.translateMessage("commands.portals.config-reload"));
            }else
                ChatUtil.sendMessage(sender, Translator.translateMessage("commands.no-permission"));
            return false;
        }

        if(args[0].equalsIgnoreCase("worlds")){
            if(sender instanceof Player){
                if(sender.hasPermission(ConfigBuilder.getInstance().getString(("permissions.worlds"))))
                    PortalsManager.getInstance().getGuiManager().open(((Player) sender), WorldsManager.class);
                else
                    ChatUtil.sendMessage(sender, Translator.translateMessage("commands.no-permission"));
            }else
                ChatUtil.sendMessage(sender, Translator.translateMessage("commands.not-player"));
            return false;
        }

        if(args[0].equalsIgnoreCase("settings")){
            if(sender instanceof Player){
                if(sender.hasPermission(ConfigBuilder.getInstance().getString(("permissions.settings"))))
                    PortalsManager.getInstance().getGuiManager().open(((Player) sender), PluginSettings.class);
                else
                    ChatUtil.sendMessage(sender, Translator.translateMessage("commands.no-permission"));
            }else
                ChatUtil.sendMessage(sender, Translator.translateMessage("commands.not-player"));
            return false;
        }

        ChatUtil.sendMessage(sender, Translator.translateMessage("commands.portals.help-message"));
        return true;
    }
}