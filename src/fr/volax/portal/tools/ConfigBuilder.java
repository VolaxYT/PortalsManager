package fr.volax.portal.tools;

import fr.volax.portal.MainPortal;

public class ConfigBuilder {

    /**
     * Return un String dans la config principal
     * @param value La direction du string à get
     * @return Contenue de "value" dans la config
     */
    public static String getString(String value){
        return MainPortal.getMain().getConfig().getString(value).replaceAll("&","§").replaceAll("%prefix%", MainPortal.getMain().PREFIX);
    }

    /**
     * Return un int dans la config principal
     * @param value La direction du int à get
     * @return Contenue de "value" dans la config
     */
    public static int getInt(String value){
        return MainPortal.getMain().getConfig().getInt(value);
    }

    /**
     * Return un Boolean dans la config principal
     * @param value La direction du boolean à get
     * @return Contenue de "value" dans la config
     */
    public static boolean getBoolean(String value){
        return MainPortal.getMain().getConfig().getBoolean(value);
    }

    /**
     * Modifier une donnée dans la config principale
     * @param value La direction de la donnée à modifier
     * @param data Donnée à modifier dans la config
     */
    public static void set(String value, String data){
        MainPortal.getMain().getConfig().set(value, data);
        MainPortal.getMain().saveConfig();
        MainPortal.getMain().reloadConfig();
    }
}
