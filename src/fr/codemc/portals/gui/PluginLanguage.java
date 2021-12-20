package fr.codemc.portals.gui;

import fr.codemc.portals.PortalsManager;
import fr.codemc.portals.utils.ConfigBuilder;
import fr.codemc.portals.utils.GuiBuilder;
import fr.codemc.portals.utils.ItemBuilder;
import fr.codemc.portals.utils.Skull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PluginLanguage implements GuiBuilder {
    @Override
    public String name() {
        return "§ePlugin Language";
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(0, new ItemBuilder(Skull.getCustomSkull("http://textures.minecraft.net/texture/51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc")).setName("§eChange to French").toItemStack());
        inv.setItem(1, new ItemBuilder(Skull.getCustomSkull("http://textures.minecraft.net/texture/cd91456877f54bf1ace251e4cee40dba597d2cc40362cb8f4ed711e50b0be5b3")).setName("§eChange to English").toItemStack());
        inv.setItem(8, new ItemBuilder(Material.BARRIER, 1).setName("§cReturn").toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(current == null || !current.hasItemMeta() || current.getType() == null || current.getItemMeta().getDisplayName() == null)
            return;

        if(current.getItemMeta().getDisplayName().equals("§cReturn"))
            PortalsManager.getInstance().getGuiManager().open(player, PluginSettings.class);

        if(current.getItemMeta().getDisplayName().equals("§eChange to French")){
            ConfigBuilder.getInstance().set("language", "french");
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals("§eChange to English")){
            ConfigBuilder.getInstance().set("language", "english");
            reloadInv(player);
        }
    }

    private void reloadInv(Player player){
        PortalsManager.getInstance().getGuiManager().open(player, this.getClass());
    }
}
