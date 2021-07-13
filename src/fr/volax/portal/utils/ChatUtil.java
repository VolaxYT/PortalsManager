package fr.volax.portal.utils;

import fr.volax.portal.PortalsCanceler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatUtil {
    public static final String PREFIX = ConfigBuilder.getInstance().getString("prefix", "messages.yml");

    public static final String CONFIG_RELOAD = ConfigBuilder.getInstance().getString("portals-command.config-reload", "messages.yml");
    public static final String HELP_MESSAGE = ConfigBuilder.getInstance().getString("portals-command.help-message", "messages.yml");
    public static final String NOT_PLAYER = ConfigBuilder.getInstance().getString("portals-command.not-player", "messages.yml");
    public static final String NO_PERMISSION = ConfigBuilder.getInstance().getString("portals-command.no-permission", "messages.yml");

    public static final String TRIED_CREATE_NETHER_NOTALLOWED = ConfigBuilder.getInstance().getString("tried-create-nether-notallowed", "messages.yml");
    public static final String TRIED_CREATE_NETHER_ALLOWED = ConfigBuilder.getInstance().getString("tried-create-nether-allowed", "messages.yml");
    public static final String TRIED_CREATE_END_NOTALLOWED = ConfigBuilder.getInstance().getString("tried-create-end-notallowed", "messages.yml");
    public static final String TRIED_CREATE_END_ALLOWED = ConfigBuilder.getInstance().getString("tried-create-end-allowed", "messages.yml");
    public static final String TRIED_ENTER_NETHER = ConfigBuilder.getInstance().getString("tried-enter-nether", "messages.yml");
    public static final String TRIED_ENTER_END = ConfigBuilder.getInstance().getString("tried-enter-end", "messages.yml");

    public static void sendMessage(CommandSender sender, String message){
        sender.sendMessage(PREFIX + " " + message);
    }

    public static void consoleMessage(String message){
        Bukkit.getServer().getConsoleSender().sendMessage(PREFIX + " " + message);
    }

    public static void logMessage(String message){
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(PortalsCanceler.getInstance().debugFile, true), true);
            if (!message.equals(""))
                writer.write("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]" + message);
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
