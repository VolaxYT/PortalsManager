package fr.codemc.portals.gui;

import fr.codemc.portals.utils.GuiBuilder;
import fr.codemc.portals.utils.ItemBuilder;
import fr.codemc.portals.utils.Skull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PluginSettings implements GuiBuilder {
    @Override
    public String name() {
        return "§ePlugin Settings";
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, new ItemBuilder(Skull.getCustomSkull("http://textures.minecraft.net/texture/51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc")).setName("§eLanguage").toItemStack());
        inv.setItem(1, new ItemBuilder(Material.COMMAND, 1).setName("§eReload configuration files").toItemStack());

    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {

    }
}