package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class KillBoatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        long boats = Bukkit.getWorlds().stream()
                .flatMap(w -> w.getEntitiesByClass(Boat.class).stream())
                .filter(Entity::isEmpty)
                .mapToInt(e -> {
                    e.remove();
                    return 1;
                })
                .sum();
        Ukutils.sendMessage(sender, "Successfully removed %d empty boats".formatted(boats));
        return true;
    }
}
