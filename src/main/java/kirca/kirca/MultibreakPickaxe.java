package kirca.kirca;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.ArrayList;
import java.util.List;

public class MultibreakPickaxe implements Listener {
    BlockFace blockface = null;

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            blockface = event.getBlockFace();
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()
                && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()
                && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains("§6Бульдозер")) {

            List<Block> blocks = getBlocks(event);
            // Break all blocks in the list
            for (Block b : blocks) {
                b.breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
            }
        }
    }

    private List<Block> getBlocks(BlockBreakEvent event) {
        Block clickedBlock = event.getBlock();
        BlockFace face = (blockface != null) ? blockface : BlockFace.UP;

        // Predefine offsets for each face to avoid repeated array creation
        final int[][] OFFSETS_XZ = {
                {-1, 0, -1}, {0, 0, -1}, {1, 0, -1},
                {-1, 0,  0}, {0, 0,  0}, {1, 0,  0},
                {-1, 0,  1}, {0, 0,  1}, {1, 0,  1}
        };
        final int[][] OFFSETS_XY = {
                {-1, -1, 0}, {0, -1, 0}, {1, -1, 0},
                {-1,  0, 0}, {0,  0, 0}, {1,  0, 0},
                {-1,  1, 0}, {0,  1, 0}, {1,  1, 0}
        };
        final int[][] OFFSETS_YZ = {
                {0, -1, -1}, {0, -1, 0}, {0, -1, 1},
                {0,  0, -1}, {0,  0, 0}, {0,  0, 1},
                {0,  1, -1}, {0,  1, 0}, {0,  1, 1}
        };

        int[][] offsets;
        switch (face) {
            case UP:
            case DOWN:
                offsets = OFFSETS_XZ;
                break;
            case NORTH:
            case SOUTH:
                offsets = OFFSETS_XY;
                break;
            case EAST:
            case WEST:
                offsets = OFFSETS_YZ;
                break;
            default:
                offsets = OFFSETS_XZ;
        }

        List<Block> blocks = new ArrayList<>(9);
        for (int[] offset : offsets) {
            Block relativeBlock = clickedBlock.getRelative(offset[0], offset[1], offset[2]);
            // Only add if not bedrock, or is the center block, or is not air
            if (relativeBlock.getType() != Material.BEDROCK
                    || (offset[0] == 0 && offset[1] == 0 && offset[2] == 0)
                    || isNonAir(relativeBlock)) {
                blocks.add(relativeBlock);
            }
        }
        return blocks;
    }
    public boolean isNonAir(Block block) {
        return block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR;
    }
}
