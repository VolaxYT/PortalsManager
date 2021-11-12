package fr.codemc.portals.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockUtil {
    public static boolean doesBlockAround(Block block, Material material){
        return block.getLocation().subtract(0, 1, 0).getBlock().getType() == material ||
                block.getLocation().add(0, 1, 0).getBlock().getType() == material ||
                block.getLocation().subtract(1, 0, 0).getBlock().getType() == material ||
                block.getLocation().add(1, 0, 0).getBlock().getType() == material ||
                block.getLocation().subtract(0, 0, 1).getBlock().getType() == material ||
                block.getLocation().add(0, 0, 1).getBlock().getType() == material;
    }
}
