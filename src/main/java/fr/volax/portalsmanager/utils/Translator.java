 package fr.volax.portalsmanager.utils;

public class Translator {
    public static String getLanguage(){
        return ConfigBuilder.getInstance().getString("language");
    }

    public static String translateMessage(String messageConfigName) {
        String language = getLanguage();

        if (language.equalsIgnoreCase("english"))
            return ConfigBuilder.getInstance().getString(messageConfigName, "langs/en_US.yml").replaceAll("&", "§");

        if (language.equalsIgnoreCase("french"))
            return ConfigBuilder.getInstance().getString(messageConfigName, "langs/fr_FR.yml").replaceAll("&", "§");

        throw new NullPointerException("Error, language " + language + " not found !");
    }
}
