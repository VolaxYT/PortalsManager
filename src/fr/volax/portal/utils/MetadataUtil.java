package fr.volax.portal.utils;

import fr.volax.portal.PortalsCanceler;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;

public class MetadataUtil {
    public static void setMetadata(String path, Integer variableToSet, Entity entity){
        if(entity.getMetadata(path).stream().anyMatch(metadataValue -> metadataValue.asString() != null))
            entity.removeMetadata(path, PortalsCanceler.getInstance());
        entity.setMetadata(path, new FixedMetadataValue(PortalsCanceler.getInstance(), variableToSet));
    }

    public static void setMetadata(String path, Double variableToSet, Entity entity){
        if(entity.getMetadata(path).stream().anyMatch(metadataValue -> metadataValue.asString() != null))
            entity.removeMetadata(path, PortalsCanceler.getInstance());
        entity.setMetadata(path, new FixedMetadataValue(PortalsCanceler.getInstance(), variableToSet));
    }

    public static int getMetadata(String path, Entity entity) {
        if (entity.getMetadata(path).stream().anyMatch(metadataValue -> metadataValue.asString() != null))
            return entity.getMetadata(path).get(0).asInt();
        throw new NullPointerException("La variable " + path + " de l'entity n'a pas été trouvé !");
    }

    public static void setMetadata(String path, String variableToSet, Entity entity){
        if(entity.getMetadata(path).stream().anyMatch(metadataValue -> metadataValue.asString() != null))
            entity.removeMetadata(path, PortalsCanceler.getInstance());
        entity.setMetadata(path, new FixedMetadataValue(PortalsCanceler.getInstance(), variableToSet));
    }

    public static String getMetadataString(String path, Entity entity) {
        if (entity.getMetadata(path).stream().anyMatch(metadataValue -> metadataValue.asString() != null))
            return entity.getMetadata(path).get(0).asString();
        throw new NullPointerException("La variable " + path + " de l'entity n'a pas été trouvé !");
    }

    public static Double getMetadataDouble(String path, Entity entity) {
        if (entity.getMetadata(path).stream().anyMatch(metadataValue -> metadataValue.asString() != null))
            return entity.getMetadata(path).get(0).asDouble();
        throw new NullPointerException("La variable " + path + " de l'entity n'a pas été trouvé !");
    }

    public static void addMetadata(String path, Integer variableToSet, Entity entity){
        int variable = (getMetadata(path, entity) + variableToSet);
        if(entity.getMetadata(path).stream().anyMatch(metadataValue -> metadataValue.asString() != null))
            entity.removeMetadata(path, PortalsCanceler.getInstance());
        entity.setMetadata(path, new FixedMetadataValue(PortalsCanceler.getInstance(), variable));
    }

    public static void removeMetadata(String path, Integer variableToSet, Entity entity){
        int variable = (getMetadata(path, entity) - variableToSet);
        if(entity.getMetadata(path).stream().anyMatch(metadataValue -> metadataValue.asString() != null))
            entity.removeMetadata(path, PortalsCanceler.getInstance());
        entity.setMetadata(path, new FixedMetadataValue(PortalsCanceler.getInstance(), variable));
    }
}
