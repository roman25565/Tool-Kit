package kirca.kirca;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class Sponge implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Block spongeBlock = event.getBlockPlaced();
        if (spongeBlock.getType() == Material.WET_SPONGE) {
            boolean hasLava = GetHasLava(spongeBlock);
            if (!hasLava){ return; }

            List<Block> lavaPos = GetAllLava(spongeBlock.getWorld(), spongeBlock.getX(), spongeBlock.getY(),spongeBlock.getZ(), 3);
            DeleteBlocks(lavaPos);
            spongeBlock.setType(Material.SPONGE);
        } else if (spongeBlock.getType() == Material.SPONGE) {
            if (!HasWater(spongeBlock)) { return; }

            List<Block> blocks = getWaters(spongeBlock.getWorld(), spongeBlock.getX(), spongeBlock.getY(), spongeBlock.getZ(), 12);
            DeleteBlocks(blocks);
        }
    }
    private boolean GetHasLava(Block block) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block relativeBlock = block.getRelative(x, y, z);
                    // Check if block is breakable (optional)
                    if (relativeBlock.getType() == Material.LAVA_BUCKET || relativeBlock.getType() == Material.LAVA) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<Block> GetAllLava(World world, int x, int y, int z, int radius){
        List<Block> waterBlocks = new ArrayList<>();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int checkX = x + dx;
                    int checkY = y + dy;
                    int checkZ = z + dz;

                    if (isWaterBlock(world, checkX, checkY, checkZ)) {
                        waterBlocks.add(world.getBlockAt(checkX, checkY, checkZ));
                    }
                }
            }
        }

        return waterBlocks;
    }
    private static boolean isWaterBlock(World world, int x, int y, int z) {
        Block block = world.getBlockAt(x, y, z);
        return block.getType() == Material.LAVA || block.getType() == Material.LAVA_BUCKET;
    }

    private void DeleteBlocks(List<Block> deleteBlocks){
        deleteBlocks.forEach(block -> {
            block.setType(Material.AIR);
        });
    }

    private boolean HasWater(Block block) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block relativeBlock = block.getRelative(x, y, z);
                    // Check if block is breakable (optional)
                        if (relativeBlock.getType() == Material.WATER) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<Block> getWaters(World world, int x, int y, int z, int radius) {
        List<Block> waterBlocks = new ArrayList<>();

        // Ітерація по кожній координаті в сфері
        for (int cx = x - radius; cx <= x + radius; cx++) {
            for (int cy = y - radius; cy <= y + radius; cy++) {
                for (int cz = z - radius; cz <= z + radius; cz++) {
                    // Перевірка, чи точка всередині сфери
                    if (Math.pow(cx - x, 2) + Math.pow(cy - y, 2) + Math.pow(cz - z, 2) <= radius * radius) {
                        Block block = world.getBlockAt(cx, cy, cz);
                        if (block.getType() == Material.WATER || block.getType() == Material.WATER_CAULDRON ||
                                block.getType() == Material.SEAGRASS || block.getType() == Material.SEA_LANTERN || block.getType() == Material.SEA_PICKLE ||
                                block.getType() == Material.KELP || block.getType() == Material.KELP_PLANT || block.getType() == Material.TALL_SEAGRASS || block.getType() == Material.BUBBLE_COLUMN) {
                            waterBlocks.add(block);
                        }
                    }
                }
            }
        }
        System.out.println("" + waterBlocks.size());

        return waterBlocks;
    }
}
