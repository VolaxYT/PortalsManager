package fr.codemc.portals.utils;

import net.minecraft.server.v1_12_R1.MinecraftServer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Objects;

public class ServerUtil {
    public static String[] getWhitelistedPlayers() {
        return MinecraftServer.getServer().getPlayerList().getWhitelisted();
    }

    public static boolean isWhitelisted() {
        return MinecraftServer.getServer().getPlayerList().getHasWhitelist();
    }

    public static void enableWhitelist(boolean enabled) {
        MinecraftServer.getServer().getPlayerList().setHasWhitelist(enabled);
    }

    public static String getServerVersion() {
        try {
            return Reflection.getMethod(getMinecraftServer().invoke(getServerInstance()).getClass(), "getVersion").invoke(getServerInstance()).toString();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "Erreur";
    }

    public static void setMOTD(String string){
        try {
            Field motd = getMinecraft().getDeclaredField("motd");

            motd.setAccessible(true);
            motd.set(getServerInstance(), string);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static String getTPS(){

        try {
            return new DecimalFormat("##.##").format(Objects.requireNonNull((double[]) getMinecraft().getField("recentTps").get(getServerInstance()))[0]);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return "Erreur";
    }

    private static Class<?> getMinecraft(){
        return Objects.requireNonNull(Reflection.getNMSClass("MinecraftServer"));
    }

    private static Method getMinecraftServer() {
        try {
            return Reflection.getMethod(getMinecraft(), "getServer");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object getServerConnection(){
        try {
            return Reflection.getMethod(getMinecraftServer().invoke(getServerInstance()).getClass(), "getServerConnection").invoke(getServerInstance());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object getServerInstance(){
        try {
            return Reflection.getNMSClass("MinecraftServer").getMethod("getServer").invoke(null);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
