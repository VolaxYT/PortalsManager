package fr.volax.portal.gui;

import fr.volax.portal.PortalsCanceler;
import fr.volax.portal.utils.ConfigBuilder;
import fr.volax.portal.utils.GuiBuilder;
import fr.volax.portal.utils.ItemBuilder;
import fr.volax.portal.utils.MetadataUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        boolean enterNetherPortal = !ConfigBuilder.getInstance().getListString("portals.nether.enter-portals-not-allowed-worlds").contains(world);
        boolean createEndPortal = !ConfigBuilder.getInstance().getListString("portals.end.create-portals-not-allowed-worlds").contains(world);
        boolean enterEndPortal = !ConfigBuilder.getInstance().getListString("portals.end.enter-portals-not-allowed-worlds").contains(world);

        inv.setItem(0, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§eSettings of §6" + world).setLore("§e§l-------------------------------------","§eCreating nether portals ⟶ " + (createNetherPortal ? "§aAllowed" : "§cDisallowed"), "§eEntering in nether portals ⟶ " + (enterNetherPortal ? "§aAllowed" : "§cDisallowed"), "§ePlacing eyes of ender on end frames ⟶ " + (createEndPortal ? "§aAllowed" : "§cDisallowed"), "§eEntering in end portals ⟶ " + (enterNetherPortal ? "§aAllowed" : "§cDisallowed"),"§e§l-------------------------------------").toItemStack());
        inv.setItem(2, createNetherPortal ? new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§cDisallow nether portal creation").toItemStack() : new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§aAllow nether portal creation").toItemStack());
        inv.setItem(3, enterNetherPortal ? new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§cDisallow nether portal entering").toItemStack() : new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§aAllow nether portal entering").toItemStack());
        inv.setItem(5, createEndPortal ? new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§cDisallow end portal creation").toItemStack() : new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§aAllow eyes of ender placing").toItemStack());
        inv.setItem(6, enterEndPortal ? new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§cDisallow end portal enter").toItemStack() : new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner("stone").setName("§aAllow end portal entering").toItemStack());
        inv.setItem(8, new ItemBuilder(Material.BARRIER).setName("§cReturn").toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        String world = MetadataUtil.getMetadataString("lastMenu", player);

        if(current == null || current.getItemMeta() == null || current.getType() == null)
            return;

        if(slot == 8 && current.getItemMeta().getDisplayName().equals("§cReturn"))
            PortalsCanceler.getInstance().getGuiManager().open(player, WorldsManager.class);
    }
}
