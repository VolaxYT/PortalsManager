package fr.codemc.portals.gui;

import fr.codemc.portals.PortalsManager;
import fr.codemc.portals.utils.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WorldSettings implements GuiBuilder {
    @Override
    public String name() {
        return "§eWorld Settings";
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        String world = MetadataUtil.getMetadataString("lastMenu", player);

        boolean createNetherPortal = !ConfigBuilder.getInstance().getListString("portals.nether.create-portals-not-allowed-worlds").contains(world);
        boolean enterNetherPortal  = !ConfigBuilder.getInstance().getListString("portals.nether.enter-portals-not-allowed-worlds").contains(world);
        boolean createEndPortal    = !ConfigBuilder.getInstance().getListString("portals.end.create-portals-not-allowed-worlds").contains(world);
        boolean enterEndPortal     = !ConfigBuilder.getInstance().getListString("portals.end.enter-portals-not-allowed-worlds").contains(world);

        inv.setItem(0, new ItemBuilder(Skull.getCustomSkull("http://textures.minecraft.net/texture/af33e7bb1256a12b5c88e705f21274fd8618bbde93c0dd3e22d9dbcf0b3a12b3")).setName("§eSettings of §6" + world).setLore("§e§l-------------------------------------","§eCreating nether portals ⟶ " + (createNetherPortal ? "§aAllowed" : "§cProhibided"), "§eEntering in nether portals ⟶ " + (enterNetherPortal ? "§aAllowed" : "§cProhibided"), "§ePlacing eyes of ender on end frames ⟶ " + (createEndPortal ? "§aAllowed" : "§cProhibided"), "§eEntering in end portals ⟶ " + (enterEndPortal ? "§aAllowed" : "§cProhibided"),"§e§l-------------------------------------").toItemStack());
        inv.setItem(2, createNetherPortal ? new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§cProhibit nether portal creation").setLore("§cClick here to prohibit nether portals creation").toItemStack() : new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§aAllow nether portal creation").setLore("§aClick here to allow nether portals creation").toItemStack());
        inv.setItem(3, enterNetherPortal ? new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§cProhibit nether portal entering").setLore("§cClick here to prohibit nether portals entering").toItemStack() : new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§aAllow nether portal entering").setLore("§aClick here to allow nether portals entering").toItemStack());
        inv.setItem(5, createEndPortal ? new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§cProhibit eyes of ender placing").setLore("§cClick here to prohibit eyes of ender placing").toItemStack() : new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§aAllow eyes of ender placing").setLore("§aClick here to allow eyes of ender placing").toItemStack());
        inv.setItem(6, enterEndPortal ? new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§cProhibit end portal entering").setLore("§cClick here to prohibit end portals entering").toItemStack() : new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§aAllow end portal entering").setLore("§aClick here to allow end portals entering").toItemStack());
        inv.setItem(8, new ItemBuilder(Material.BARRIER).setName("§cReturn").toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        String world = MetadataUtil.getMetadataString("lastMenu", player);

        if(current == null || !current.hasItemMeta() || current.getType() == null || current.getItemMeta().getDisplayName() == null)
            return;

        if(slot == 8 && current.getItemMeta().getDisplayName().equals("§cReturn"))
            PortalsManager.getInstance().getGuiManager().open(player, WorldsManager.class);

        if(current.getItemMeta().getDisplayName().equals("§cProhibit nether portal creation")){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.nether.create-portals-not-allowed-worlds");
            worlds.add(world);
            ConfigBuilder.getInstance().set("portals.nether.create-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals("§aAllow nether portal creation")){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.nether.create-portals-not-allowed-worlds");
            worlds.remove(world);
            ConfigBuilder.getInstance().set("portals.nether.create-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals("§cProhibit nether portal entering")){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.nether.enter-portals-not-allowed-worlds");
            worlds.add(world);
            ConfigBuilder.getInstance().set("portals.nether.enter-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals("§aAllow nether portal entering")){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.nether.enter-portals-not-allowed-worlds");
            worlds.remove(world);
            ConfigBuilder.getInstance().set("portals.nether.enter-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals("§cProhibit eyes of ender placing")){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.end.create-portals-not-allowed-worlds");
            worlds.add(world);
            ConfigBuilder.getInstance().set("portals.end.create-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals("§aAllow eyes of ender placing")){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.end.create-portals-not-allowed-worlds");
            worlds.remove(world);
            ConfigBuilder.getInstance().set("portals.end.create-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals("§cProhibit end portal entering")){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.end.enter-portals-not-allowed-worlds");
            worlds.add(world);
            ConfigBuilder.getInstance().set("portals.end.enter-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals("§aAllow end portal entering")){
            List<String> worlds = ConfigBuilder.getInstance().getListString("portals.end.enter-portals-not-allowed-worlds");
            worlds.remove(world);
            ConfigBuilder.getInstance().set("portals.end.enter-portals-not-allowed-worlds", worlds);
            reloadInv(player);
        }
    }

    private void reloadInv(Player player){
        PortalsManager.getInstance().getGuiManager().open(player, WorldSettings.class);
    }
}