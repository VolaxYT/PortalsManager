package fr.volax.portal.API;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class ItemAPI {
    
    private ItemStack is;

    public ItemAPI(Material m) {
        this(m, 1);
    }

    public ItemAPI(ItemStack is) {
        this.is = is;
    }

    public ItemAPI(Material m, int amount) {
        is = new ItemStack(m, amount);
    }

    public ItemAPI(Material m, int amount, short meta){
        is = new ItemStack(m, amount, meta);
    }

    public ItemAPI clone() {
        return new ItemAPI(is);
    }

    public ItemAPI setDurability(short dur) {
        is.setDurability(dur);
        return this;
    }

    public ItemAPI setName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }

    public ItemAPI addUnsafeEnchantment(Enchantment ench, int level) {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemAPI removeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    public ItemAPI setSkullOwner(String owner) {
        try {
            SkullMeta im = (SkullMeta) is.getItemMeta();
            im.setOwner(owner);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    public ItemAPI addEnchant(Enchantment ench, int level) {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }
    public ItemAPI addFlag(ItemFlag itemsflags){
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(itemsflags);
        is.setItemMeta(im);
        return this;
    }
    public ItemAPI setInfinityDurability() {
        is.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemAPI setLore(String... lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemAPI setWoolColor(DyeColor color) {
        if (!is.getType().equals(Material.WOOL))
            return this;
        this.is.setDurability(color.getData());
        return this;
    }

    public ItemAPI setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException ignored) { }
        return this;
    }

    public ItemStack toItemStack() {
        return is;
    }
}
