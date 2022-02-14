package fr.volax.portalsmanager.gui;

import fr.volax.portalsmanager.PortalsManager;
import fr.volax.portalsmanager.utils.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WorldSettings implements GuiBuilder {
    @Override
    public String name() {
        return Translator.translateMessage("gui.worlds-settings.title");
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        String loreSeparator = Translator.translateMessage("gui.worlds-settings.items.world-information.lore-separator");
        String loreAllowed = Translator.translateMessage("gui.worlds-settings.items.world-information.lore-allowed");
        String loreProhibited = Translator.translateMessage("gui.worlds-settings.items.world-information.lore-prohibited");
        String lore1 = Translator.translateMessage("gui.worlds-settings.items.world-information.lore-1");
        String lore2 = Translator.translateMessage("gui.worlds-settings.items.world-information.lore-2");
        String lore3 = Translator.translateMessage("gui.worlds-settings.items.world-information.lore-3");
        String lore4 = Translator.translateMessage("gui.worlds-settings.items.world-information.lore-4");

        String world = MetadataUtil.getMetadataString("lastMenu", player);

        boolean createNetherPortal = !ConfigBuilder.getInstance().getListString("portals.nether.create-portals-not-allowed-worlds").contains(world);
        boolean enterNetherPortal  = !ConfigBuilder.getInstance().getListString("portals.nether.enter-portals-not-allowed-worlds").contains(world);
        boolean createEndPortal    = !ConfigBuilder.getInstance().getListString("portals.end.create-portals-not-allowed-worlds").contains(world);
        boolean enterEndPortal     = !ConfigBuilder.getInstance().getListString("portals.end.enter-portals-not-allowed-worlds").contains(world);

        inv.setItem(0, new ItemBuilder(Skull.getCustomSkull("http://textures.minecraft.net/texture/af33e7bb1256a12b5c88e705f21274fd8618bbde93c0dd3e22d9dbcf0b3a12b3")).setName(Translator.translateMessage("gui.worlds-settings.items.world-information.name").replaceAll("%worldName%", world))
                .setLore(loreSeparator,
                        lore1 + (createNetherPortal ? loreAllowed : loreProhibited),
                        lore2 + (enterNetherPortal ? loreAllowed : loreProhibited),
                        lore3 + (createEndPortal ? loreAllowed : loreProhibited),
                        lore4 + (enterEndPortal ? loreAllowed : loreProhibited),
                        loreSeparator).toItemStack());

        inv.setItem(2, createNetherPortal ? new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.worlds-settings.items.prohibit-nether-portal-creation.name")).setLore(Translator.translateMessage("gui.worlds-settings.items.prohibit-nether-portal-creation.lore")).toItemStack() :
                        new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.worlds-settings.items.allow-nether-portal-creation.name")).setLore(Translator.translateMessage("gui.worlds-settings.items.allow-nether-portal-creation.lore")).toItemStack());
        inv.setItem(3, enterNetherPortal ? new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.worlds-settings.items.prohibit-nether-portal-entering.name")).setLore(Translator.translateMessage("gui.worlds-settings.items.prohibit-nether-portal-entering.lore")).toItemStack() :
                new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.worlds-settings.items.allow-nether-portal-entering.name")).setLore(Translator.translateMessage("gui.worlds-settings.items.allow-nether-portal-entering.lore")).toItemStack());
        inv.setItem(5, createEndPortal ? new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.worlds-settings.items.prohibit-ender-eyes-placing.name")).setLore(Translator.translateMessage("gui.worlds-settings.items.prohibit-ender-eyes-placing.lore")).toItemStack() :
                new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.worlds-settings.items.allow-ender-eyes-placing.name")).setLore(Translator.translateMessage("gui.worlds-settings.items.allow-ender-eyes-placing.lore")).toItemStack());
        inv.setItem(6, enterEndPortal ? new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.worlds-settings.items.prohibit-end-portal-entering.name")).setLore(Translator.translateMessage("gui.worlds-settings.items.prohibit-end-portal-entering.lore")).toItemStack() :
                new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.worlds-settings.items.allow-end-portal-entering.name")).setLore(Translator.translateMessage("gui.worlds-settings.items.allow-end-portal-entering.lore")).toItemStack());

        inv.setItem(8, new ItemBuilder(Material.BARRIER).setName(Translator.translateMessage("gui.generic-items.return.name")).toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        String world = MetadataUtil.getMetadataString("lastMenu", player);

        if(current == null || !current.hasItemMeta() || current.getType() == null || current.getItemMeta().getDisplayName() == null)
            return;

        if(slot == 8 && current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.generic-items.return.name")))
            PortalsManager.getInstance().getGuiManager().open(player, WorldsManager.class);

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.worlds-settings.items.prohibit-nether-portal-creation.name"))){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.nether.create-portals-not-allowed-worlds");
            worlds.add(world);
            ConfigBuilder.getInstance().set("portals.nether.create-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.worlds-settings.items.allow-nether-portal-creation.name"))){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.nether.create-portals-not-allowed-worlds");
            worlds.remove(world);
            ConfigBuilder.getInstance().set("portals.nether.create-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.worlds-settings.items.prohibit-nether-portal-entering.name"))){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.nether.enter-portals-not-allowed-worlds");
            worlds.add(world);
            ConfigBuilder.getInstance().set("portals.nether.enter-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.worlds-settings.items.allow-nether-portal-entering.name"))){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.nether.enter-portals-not-allowed-worlds");
            worlds.remove(world);
            ConfigBuilder.getInstance().set("portals.nether.enter-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.worlds-settings.items.prohibit-ender-eyes-placing.name"))){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.end.create-portals-not-allowed-worlds");
            worlds.add(world);
            ConfigBuilder.getInstance().set("portals.end.create-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.worlds-settings.items.allow-ender-eyes-placing.name"))){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.end.create-portals-not-allowed-worlds");
            worlds.remove(world);
            ConfigBuilder.getInstance().set("portals.end.create-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.worlds-settings.items.prohibit-end-portal-entering.name"))){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.end.enter-portals-not-allowed-worlds");
            worlds.add(world);
            ConfigBuilder.getInstance().set("portals.end.enter-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.worlds-settings.items.allow-end-portal-entering.name"))){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.end.enter-portals-not-allowed-worlds");
            worlds.remove(world);
            ConfigBuilder.getInstance().set("portals.end.enter-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }
    }

    private void reloadInv(Player player){
        PortalsManager.getInstance().getGuiManager().open(player, this.getClass());
    }
}