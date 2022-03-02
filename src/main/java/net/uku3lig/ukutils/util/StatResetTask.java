package net.uku3lig.ukutils.util;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class StatResetTask extends BukkitRunnable {
    private final Ukutils plugin;

    public StatResetTask(Ukutils plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        try {
            List<UUID> playersInDB = plugin.getDatabase().getPlayers();

            if (plugin.getConfig().getBoolean("phantoms-disabled-by-default")) {
                Bukkit.getOnlinePlayers().stream()
                        .map(Entity::getUniqueId)
                        .filter(p -> !playersInDB.contains(p))
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(p -> p.setStatistic(Statistic.TIME_SINCE_REST, 0));
            } else {
                playersInDB.stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(p -> p.setStatistic(Statistic.TIME_SINCE_REST, 0));
            }
        } catch (SQLException e) {
            Bukkit.getLogger().warning("[ukutils] Error: could not execute SQL statement");
            e.printStackTrace();
        }
    }
}
