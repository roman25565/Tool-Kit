package kirca.kirca;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    public static ItemStack MultibreakPickaxe;
    public static ItemStack MultibreakShovel;

    public static void init() {
        createMultibreakPickaxe();
        createMultibreakShovel();
    }

    private static void createMultibreakPickaxe(){
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Multibreak Pickaxe");
        List<String> lore = new ArrayList<>();
        lore.add("§6Бульдозер");
        lore.add("§7Breaks 3x3 area");
        meta.setLore(lore);
        item.setItemMeta(meta);
        MultibreakPickaxe = item;
    }

    private static void createMultibreakShovel(){
        ItemStack item = new ItemStack(Material.DIAMOND_SHOVEL, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Multibreak Pickaxe");
        List<String> lore = new ArrayList<>();
        lore.add("§6Бульдозер");
        lore.add("§7ламає блоки 3x3");
        meta.setLore(lore);
        item.setItemMeta(meta);
        MultibreakShovel = item;
    }

}
