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
    public static final String PREFIX = ConfigBuilder.getInstance().getString("prefix");

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
