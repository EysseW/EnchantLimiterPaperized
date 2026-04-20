package fun.swip.enchantLimiterPaperized;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;

import java.util.HashMap;
import java.util.Map;

public class EnchantListener implements Listener {
    private final ConfigurationSection configurationSection;
    public EnchantListener(ConfigurationSection config) {
        this.configurationSection = config;
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        int maxEnchants = configurationSection.getInt("max-enchants");
        System.out.println("Enchantment happened!");
        if (event.getEnchantsToAdd().size() >= maxEnchants) {
            Map<Enchantment, Integer> enchants = event.getEnchantsToAdd();
            int amount = 0;
            Map<Enchantment, Integer> enchantmentList = new HashMap();
            for (Enchantment enchantment : enchants.keySet()) {
                if (amount < maxEnchants) {
                    enchantmentList.put(enchantment, enchants.get(enchantment));
                    amount++;
                }
            }
            event.getEnchantsToAdd().clear();
            event.getEnchantsToAdd().putAll(enchantmentList);
            event.getInventory().getViewers().forEach(viewer -> viewer.sendMessage("Item has " + maxEnchants + " enchantments already, cancelling the rest!"));
        }
    }

    @EventHandler
    public void onEnchant(InventoryClickEvent event) {
        int maxEnchants = configurationSection.getInt("max-enchants");
        if (event.getInventory() instanceof AnvilInventory) {
            if (event.getRawSlot() == 2 && event.getCurrentItem() != null) {
                if (event.getCurrentItem().getEnchantments().size() >= maxEnchants) {
                    event.setCancelled(true);
                    event.getInventory().close();
                    event.getWhoClicked().sendMessage("Cannot accept more than " + maxEnchants + " enchants!");
                }
            }
        }
    }
}
