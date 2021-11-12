 package fr.codemc.portals.utils;

public class LanguagePreference {
    public static String getLanguage(){
        return ConfigBuilder.getInstance().getString("language");
    }

    public static String formatMessage(String messageConfigName) {
        String language = getLanguage();

        if (language.equalsIgnoreCase("english"))
            return ConfigBuilder.getInstance().getString("english." + messageConfigName, "messages.yml").replaceAll("&", "§");

        if (language.equalsIgnoreCase("french"))
            return ConfigBuilder.getInstance().getString("french." + messageConfigName, "messages.yml").replaceAll("&", "§");

        throw new NullPointerException("Error, language " + language + " not found !");
    }
}
