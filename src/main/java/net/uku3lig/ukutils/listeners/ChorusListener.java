package net.uku3lig.ukutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChorusListener implements Listener {
    private final boolean cancelTp;

    public ChorusListener(JavaPlugin plugin) {
        this.cancelTp = plugin.getConfig().getBoolean("disable-chorus-tp");
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (cancelTp && event.getCause() == PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT) {
            event.setCancelled(true);
        }
    }
}
