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

    private static List<Block> getBlocks(BlockBreakEvent event) {
        List<Block> blocks = new ArrayList<>();
        Block clickedBlock = event.getBlock();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block relativeBlock = clickedBlock.getRelative(x, y, z);

                    if (relativeBlock.getType() != Material.BEDROCK || (z == 0 && y == 0 && x == 0) || isNonAir(relativeBlock)) {
                        blocks.add(relativeBlock);
                    }
                }
            }
        }
        return blocks;
    }
    public static boolean isNonAir(Block block) {
        return block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR;
    }
}
