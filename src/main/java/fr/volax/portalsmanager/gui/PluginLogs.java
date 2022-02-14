package fr.volax.portalsmanager.gui;

import fr.volax.portalsmanager.PortalsManager;
import fr.volax.portalsmanager.utils.*;
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
        String loreSeparator = Translator.translateMessage("gui.plugin-logs.items.logs-info.lore-separator");
        String loreEnabled = Translator.translateMessage("gui.plugin-logs.items.logs-info.lore-enabled");
        String loreDisabled = Translator.translateMessage("gui.plugin-logs.items.logs-info.lore-disabled");
        String lore1 = Translator.translateMessage("gui.plugin-logs.items.logs-info.lore-1");
        String lore2 = Translator.translateMessage("gui.plugin-logs.items.logs-info.lore-2");
        String lore3 = Translator.translateMessage("gui.plugin-logs.items.logs-info.lore-3");

        boolean logPlayer = ConfigBuilder.getInstance().getBoolean("logs.player");
        boolean logConsole = ConfigBuilder.getInstance().getBoolean("logs.console");
        boolean logFile = ConfigBuilder.getInstance().getBoolean("logs.file");

        inv.setItem(0, new ItemBuilder(Skull.getCustomSkull("http://textures.minecraft.net/texture/af33e7bb1256a12b5c88e705f21274fd8618bbde93c0dd3e22d9dbcf0b3a12b3")).setName(Translator.translateMessage("gui.plugin-logs.items.logs-info.name"))
                .setLore(loreSeparator,
                        lore1 + (logPlayer ? loreEnabled : loreDisabled),
                        lore2 + (logConsole ? loreEnabled : loreDisabled),
                        lore3 + (logFile ? loreEnabled : loreDisabled),
                        loreSeparator).toItemStack());

        inv.setItem(3, logPlayer ? new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.plugin-logs.items.disable-logs-player.name")).setLore(Translator.translateMessage("gui.plugin-logs.items.disable-logs-player.lore")).toItemStack() :
                new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.plugin-logs.items.enable-logs-player.name")).setLore(Translator.translateMessage("gui.plugin-logs.items.enable-logs-player.lore")).toItemStack());
        inv.setItem(4, logConsole ? new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.plugin-logs.items.disable-logs-console.name")).setLore(Translator.translateMessage("gui.plugin-logs.items.disable-logs-console.lore")).toItemStack() :
                new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.plugin-logs.items.enable-logs-console.name")).setLore(Translator.translateMessage("gui.plugin-logs.items.enable-logs-console.lore")).toItemStack());
        inv.setItem(5, logFile ? new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.plugin-logs.items.disable-logs-file.name")).setLore(Translator.translateMessage("gui.plugin-logs.items.disable-logs-file.lore")).toItemStack() :
                new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3).setSkullOwner("stone").setName(Translator.translateMessage("gui.plugin-logs.items.enable-logs-file.name")).setLore(Translator.translateMessage("gui.plugin-logs.items.enable-logs-file.lore")).toItemStack());

        inv.setItem(8, new ItemBuilder(Material.BARRIER, 1).setName(Translator.translateMessage("gui.generic-items.return.name")).toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(current == null || !current.hasItemMeta() || current.getType() == null || current.getItemMeta().getDisplayName() == null)
            return;

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.plugin-logs.items.disable-logs-player.name"))){
            ConfigBuilder.getInstance().set("logs.player", false);
            ChatUtil.sendMessage(player, Translator.translateMessage("changed-logs").replaceAll("%status%", Translator.translateMessage("logs-disabled")).replaceAll("%logsType%", Translator.translateMessage("logs-player")));
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.plugin-logs.items.enable-logs-player.name"))){
            ConfigBuilder.getInstance().set("logs.player", true);
            ChatUtil.sendMessage(player, Translator.translateMessage("changed-logs").replaceAll("%status%", Translator.translateMessage("logs-enabled")).replaceAll("%logsType%", Translator.translateMessage("logs-player")));
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.plugin-logs.items.disable-logs-console.name"))){
            ConfigBuilder.getInstance().set("logs.console", false);
            ChatUtil.sendMessage(player, Translator.translateMessage("changed-logs").replaceAll("%status%", Translator.translateMessage("logs-disabled")).replaceAll("%logsType%", Translator.translateMessage("logs-console")));
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.plugin-logs.items.enable-logs-console.name"))){
            ConfigBuilder.getInstance().set("logs.console", true);
            ChatUtil.sendMessage(player, Translator.translateMessage("changed-logs").replaceAll("%status%", Translator.translateMessage("logs-enabled")).replaceAll("%logsType%", Translator.translateMessage("logs-console")));
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.plugin-logs.items.disable-logs-file.name"))){
            ConfigBuilder.getInstance().set("logs.file", false);
            ChatUtil.sendMessage(player, Translator.translateMessage("changed-logs").replaceAll("%status%", Translator.translateMessage("logs-disabled")).replaceAll("%logsType%", Translator.translateMessage("logs-file")));
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.plugin-logs.items.enable-logs-file.name"))){
            ConfigBuilder.getInstance().set("logs.file", true);
            ChatUtil.sendMessage(player, Translator.translateMessage("changed-logs").replaceAll("%status%", Translator.translateMessage("logs-enabled")).replaceAll("%logsType%", Translator.translateMessage("logs-file")));
            reloadInv(player);
        }

        if(current.getItemMeta().getDisplayName().equals(Translator.translateMessage("gui.generic-items.return.name")))
            PortalsManager.getInstance().getGuiManager().open(player, PluginSettings.class);
    }

    private void reloadInv(Player player){
        PortalsManager.getInstance().getGuiManager().open(player, this.getClass());
    }
}
