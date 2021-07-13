package fr.volax.portal.gui;

import fr.volax.portal.PortalsCanceler;
import fr.volax.portal.utils.GuiBuilder;
import fr.volax.portal.utils.ItemBuilder;
import fr.volax.portal.utils.MetadataUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WorldsManager implements GuiBuilder {
    @Override
    public String name() {
        return "§eWorlds Manager";
    }

    @Override
    public int getSize() {
        return (int) Math.ceil(Bukkit.getWorlds().size() / 9.0) * 9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        int slot = 0;
        for(World world : Bukkit.getWorlds()){
            inv.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, (short)3).setSkullOwner("BlockminersTV").setName("§eConfigure the world §6" + world.getName()).toItemStack());
            slot++;
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(current == null || current.getItemMeta() == null || current.getType() == null)
            return;

        String worldName = current.getItemMeta().getDisplayName().substring(24);
        if(Bukkit.getWorlds().contains(Bukkit.getWorld(worldName))){
            MetadataUtil.setMetadata("lastMenu", worldName, player);
            PortalsCanceler.getInstance().getGuiManager().open(player, WorldSettings.class);
        }
    }
}
