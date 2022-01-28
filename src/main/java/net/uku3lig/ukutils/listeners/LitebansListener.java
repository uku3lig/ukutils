package net.uku3lig.ukutils.listeners;

import litebans.api.Events;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LitebansListener extends Events.Listener {
    private static final String COMMAND = "discord bcast #%s %s";
    private final JavaPlugin plugin;

    public LitebansListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void broadcastSent(@NotNull String message, @Nullable String type) {
        List<String> allowed = plugin.getConfig().getStringList("bans-broadcast.allowed");
        if (type != null && !allowed.contains(type)) return;

        String channelName = plugin.getConfig().getString("bans-broadcast.channel");
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), COMMAND.formatted(channelName, message));
                System.out.printf("message: %s, type: %s%n", message, type);
            }
        }.runTask(plugin);
    }
}
