 package fr.codemc.portals.utils;

public class LanguagePreference {
    public static String getLanguage(){
        return ConfigBuilder.getInstance().getString("language");
    }

    public static String formatMessage(String messageConfigName) {
        String language = getLanguage();

        if (language.equalsIgnoreCase("english"))
            return ConfigBuilder.getInstance().getString(messageConfigName, "english.yml").replaceAll("&", "§");

        if (language.equalsIgnoreCase("french"))
            return ConfigBuilder.getInstance().getString(messageConfigName, "french.yml").replaceAll("&", "§");

        throw new NullPointerException("Error, language " + language + " not found !");
    }
}
