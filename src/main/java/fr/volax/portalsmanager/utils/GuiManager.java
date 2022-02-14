package fr.volax.portalsmanager.utils;

import fr.volax.portalsmanager.PortalsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GuiManager implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player    player  = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if(event.getCurrentItem() == null) return;

        PortalsManager.getInstance().getRegisteredMenus().values().stream()
                .filter(menu -> event.getView().getTitle().equalsIgnoreCase(menu.name()))
                .forEach(menu -> {
                    menu.onClick(player, event.getInventory(), current, event.getSlot());
                    event.setCancelled(true);
                });
    }

    public void addMenu(GuiBuilder m){
        PortalsManager.getInstance().getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(Player player, Class<? extends GuiBuilder> gClass){
        if(!PortalsManager.getInstance().getRegisteredMenus().containsKey(gClass)) return;

        GuiBuilder menu = PortalsManager.getInstance().getRegisteredMenus().get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
        menu.contents(player, inv);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.openInventory(inv);
            }
        }.runTaskLater(PortalsManager.getInstance(), 1);
    }
}
