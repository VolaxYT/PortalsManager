package fr.codemc.portals.gui;

import fr.codemc.portals.PortalsManager;
import fr.codemc.portals.utils.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PluginLanguage implements GuiBuilder {
    @Override
    public String name() {
        return Translator.translateMessage("gui.plugin-langage.title");
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        String changeFrench = Translator.translateMessage("gui.plugin-langage.items.french.name");
        String changeEnglish = Translator.translateMessage("gui.plugin-langage.items.english.name");
        inv.setItem(0, new ItemBuilder(Skull.getCustomSkull("http://textures.minecraft.net/texture/51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc")).setName(Translator.translateMessage("gui.plugin-langage.items.french.name")).toItemStack());
        inv.setItem(1, new ItemBuilder(Skull.getCustomSkull("http://textures.minecraft.net/texture/cd91456877f54bf1ace251e4cee40dba597d2cc40362cb8f4ed711e50b0be5b3")).setName(Translator.translateMessage("gui.plugin-langage.items.english.name")).toItemStack());
        inv.setItem(8, new ItemBuilder(Material.BARRIER, 1).setName(Translator.translateMessage("gui.generic-items.return.name")).toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(current == null || !current.hasItemMeta() || current.getType() == null || current.getItemMeta().getDisplayName() == null)
            return;

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.generic-items.return.name")))
            PortalsManager.getInstance().getGuiManager().open(player, PluginSettings.class);

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.plugin-langage.items.french.name"))){
            ConfigBuilder.getInstance().set("language", "french");
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.plugin-langage.items.english.name"))){
            ConfigBuilder.getInstance().set("language", "english");
            reloadInv(player);
        }
    }

    private void reloadInv(Player player){
        PortalsManager.getInstance().getGuiManager().open(player, this.getClass());
    }
}
