package kirca.kirca;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Comparator;
import java.util.List;

public class Commands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command");
            return true;
        }

        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("lifetime")) {
            World world = Bukkit.getWorlds().get(0); // перший світ (як правило "world")
            SendWorldInfo(world, player);

            return true;
        }

        if (cmd.getName().equalsIgnoreCase("changeworld")) {
            String currentWorld = player.getWorld().getName();

            if (currentWorld.equals("creative_world")) {
                // З креативного тільки в newWorl
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv tp " + player.getName() + " newWorl");
                player.sendMessage("§aTeleported to Survival world!");
            } else if (currentWorld.equals("newWorl")) {
                // З newWorl тільки в креативний
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv tp " + player.getName() + " creative_world");
                player.sendMessage("§aTeleported to Creative world!");
            } else {
                player.sendMessage("§cYou can only switch between survival and creative worlds!");
            }

            return true;
        }

        if (player.hasPermission("op")) {
            if (cmd.getName().equalsIgnoreCase("givemultibreakpickaxe")) {
                player.getInventory().addItem(ItemManager.MultibreakPickaxe);
            }
            if (cmd.getName().equalsIgnoreCase("givemultibreakShovel")) {
                player.getInventory().addItem(ItemManager.MultibreakShovel);

                ItemMeta itemMeta = ((Player) sender).getPlayer().getInventory().getItemInMainHand().getItemMeta();
                List<String> lore = itemMeta.getLore();

                if (lore != null && !lore.isEmpty()) {
                    // Display lore in console
                    System.out.println("Item Lore:");
                    for (String line : lore) {
                        System.out.println(line);
                    }

                    // Send lore to player
                    player.sendMessage("Item Lore:");
                    for (String line : lore) {
                        player.sendMessage(line);
                    }
                }
            }

        } else {
            sender.sendMessage("You are not allowed to use this command");
        }
        return true;
    }

    private static void SendWorldInfo(World world, Player player) {
        long ticks = world.getGameTime(); // кількість тікiв з моменту створення світу

        long gameDays = ticks / 24000;         // ігрові дні
        long realHours = ticks / (20 * 60 * 60); // 20 тікiв = 1 секунда → 3600 сек = 1 година
        long realDays = realHours / 24;

        player.sendMessage("§6§l===== [ Інформація про сервер ] =====");
        player.sendMessage("§e⏳ Сервер існує:");
        player.sendMessage("  §a• " + gameDays + " §7ігрових днів");
        player.sendMessage("  §b• " + realHours + " §7реальних годин");
        player.sendMessage("  §d• " + realDays + " §7реальних днів");
        player.sendMessage("§6§l================================");
    }

    public World getOldestWorld() {
        return Bukkit.getWorlds().stream()
                .max(Comparator.comparingLong(World::getGameTime))
                .orElse(null); // якщо світів немає
    }
}
