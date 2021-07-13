package fr.codemc.portals.utils;

import fr.codemc.portals.PortalsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatUtil {
    public static final String PREFIX = ConfigBuilder.getInstance().getString("prefix", "messages.yml");

    public static final String NOT_PLAYER    = ConfigBuilder.getInstance().getString("commands.not-player", "messages.yml");
    public static final String NO_PERMISSION = ConfigBuilder.getInstance().getString("commands.no-permission", "messages.yml");

    public static final String CONFIG_RELOAD = ConfigBuilder.getInstance().getString("commands.portals.config-reload", "messages.yml");
    public static final String HELP_MESSAGE  = ConfigBuilder.getInstance().getString("commands.portals.help-message", "messages.yml");

    public static final String GET_WORLD = ConfigBuilder.getInstance().getString("commands.currentworld.get-world", "messages.yml");

    public static final String CREATE_NETHER_PROHIBITED   = ConfigBuilder.getInstance().getString("create-nether-prohibited", "messages.yml");
    public static final String CREATE_NETHER_ALLOWED      = ConfigBuilder.getInstance().getString("create-nether-allowed", "messages.yml");
    public static final String ENTER_NETHER_PROHIBITED    = ConfigBuilder.getInstance().getString("enter-nether-prohibited", "messages.yml");
    public static final String ENTER_NETHER_ALLOWED       = ConfigBuilder.getInstance().getString("enter-nether-allowed", "messages.yml");
    public static final String CREATE_END_PROHIBITED      = ConfigBuilder.getInstance().getString("create-end-prohibited", "messages.yml");
    public static final String CREATE_END_ALLOWED         = ConfigBuilder.getInstance().getString("create-end-allowed", "messages.yml");
    public static final String ENTER_END_PROHIBITED       = ConfigBuilder.getInstance().getString("enter-end-prohibited", "messages.yml");
    public static final String ENTER_END_ALLOW            = ConfigBuilder.getInstance().getString("enter-end-allowed", "messages.yml");
    public static final String CANT_ENTER_NETHER          = ConfigBuilder.getInstance().getString("cant-enter-nether", "messages.yml");
    public static final String CANT_CREATE_NETHER         = ConfigBuilder.getInstance().getString("cant-create-nether", "messages.yml");
    public static final String CANT_ENTER_END             = ConfigBuilder.getInstance().getString("cant-enter-end", "messages.yml");
    public static final String CANT_CREATE_END            = ConfigBuilder.getInstance().getString("cant-create-end", "messages.yml");

    public static void sendMessage(CommandSender sender, String message){
        sender.sendMessage(PREFIX + " " + message);
    }

    public static void consoleMessage(String message){
        Bukkit.getServer().getConsoleSender().sendMessage(PREFIX + " " + message);
    }

    public static void logMessage(String message){
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(PortalsManager.getInstance().debugFile, true), true);
            if (!message.equals(""))
                writer.write("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "] " + message.replaceAll("§e", "").replaceAll("§6", ""));
            writer.write(System.getProperty("line.separator"));
            writer.close();
        } catch (IOException e) {
            Bukkit.getServer().getLogger().warning(PREFIX + "An error occurred while writing to the log! IOException");
        }
    }

    public static void broadcastMessage(String message){
        Bukkit.broadcastMessage(PREFIX + " " + message);
    }
}
