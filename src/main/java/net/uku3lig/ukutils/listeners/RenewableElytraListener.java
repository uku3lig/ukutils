package net.uku3lig.ukutils.listeners;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public record RenewableElytraListener(JavaPlugin plugin) implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!event.getEntity().getType().equals(EntityType.ENDER_DRAGON)) return;
        if (event.getEntity().getKiller() == null) return;
        if (!event.getEntity().getKiller().hasPermission("ukutils.elytra")) return;

        double drop = plugin.getConfig().getDouble("elytra-drop-chance", 0.02);
        if (Math.random() >= drop) return;

        Player player = event.getEntity().getKiller();
        if (!player.getInventory().addItem(new ItemStack(Material.ELYTRA)).isEmpty()) {
            event.getEntity().getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.ELYTRA));
        }
        Ukutils.sendMessage(player, "An elytra has been added to your inventory.");
    }
}
