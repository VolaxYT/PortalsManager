package fr.volax.portal.utils;

import fr.volax.portal.PortalsCanceler;
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
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();

        if(event.getCurrentItem() == null) return;

        PortalsCanceler.getInstance().getRegisteredMenus().values().stream()
                .filter(menu -> inv.getName().equalsIgnoreCase(menu.name()))
                .forEach(menu -> {
                    menu.onClick(player, inv, current, event.getSlot());
                    event.setCancelled(true);
                });
    }

    public void addMenu(GuiBuilder m){
        PortalsCanceler.getInstance().getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(Player player, Class<? extends GuiBuilder> gClass){
        if(!PortalsCanceler.getInstance().getRegisteredMenus().containsKey(gClass)) return;

        GuiBuilder menu = PortalsCanceler.getInstance().getRegisteredMenus().get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
        menu.contents(player, inv);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.openInventory(inv);
            }
        }.runTaskLater(PortalsCanceler.getInstance(), 1);
    }
}
