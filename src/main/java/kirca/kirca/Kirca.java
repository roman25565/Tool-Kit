package kirca.kirca;

import org.bukkit.plugin.java.JavaPlugin;

public final class Kirca extends JavaPlugin {

    @Override
    public void onEnable() {

//        Bukkit.getLogger().warning("Something might be wrongAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        ItemManager.init();


        this.getCommand("givemultibreakpickaxe").setExecutor(new Commands());
        this.getCommand("givemultibreakShovel").setExecutor(new Commands());
        this.getCommand("lifetime").setExecutor(new Commands());
        this.getCommand("changeworld").setExecutor(new Commands());

        this.getServer().getPluginManager().registerEvents(new MultibreakPickaxe(), this);
        this.getServer().getPluginManager().registerEvents(new Sponge(), this);

    }
}
