package fun.swip.enchantLimiterPaperized;

import org.bukkit.plugin.java.JavaPlugin;

public final class EnchantLimiterPaperized extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EnchantListener(this.getConfig()), this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
