package fr.codemc.portals.gui;

import fr.codemc.portals.PortalsManager;
import fr.codemc.portals.utils.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PluginLogs implements GuiBuilder {
    @Override
    public String name() {
        return Translator.translateMessage("gui.plugin-logs.title");
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        boolean logPlayer = !Boolean.getBoolean(ConfigBuilder.getInstance().getString("logs.player"));
        boolean logConsole = !Boolean.getBoolean(ConfigBuilder.getInstance().getString("logs.console"));
        boolean logFile = !Boolean.getBoolean(ConfigBuilder.getInstance().getString("logs.file"));

        //inv.setItem(0, new ItemBuilder(Skull.getCustomSkull("http://textures.minecraft.net/texture/51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc")).setName(Translator.translateMessage("gui.plugin-settings.items.change-language.name")).toItemStack());
        inv.setItem(8, new ItemBuilder(Material.BARRIER, 1).setName(Translator.translateMessage("gui.generic-items.return.name")).toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(current == null || !current.hasItemMeta() || current.getType() == null || current.getItemMeta().getDisplayName() == null)
            return;

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.generic-items.return.name")))
            PortalsManager.getInstance().getGuiManager().open(player, PluginSettings.class);


    }
}
