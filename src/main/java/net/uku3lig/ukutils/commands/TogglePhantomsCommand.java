package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class TogglePhantomsCommand extends UkutilsCommand {
    public TogglePhantomsCommand(Ukutils plugin) {
        super(plugin);
    }

    @Override
    public String command() {
        return "togglephantoms";
    }

    @Override
    public void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            if (sender instanceof Player player) {
                boolean newState = plugin.getDatabase().togglePhantom(player.getUniqueId());
                Ukutils.sendMessage(player, "Successfully " + (newState ? "disabled" : "enabled") + " phantoms");
                // 72000 ticks = 3 mc days, allowing phantoms to spawn
                if (!newState) player.setStatistic(Statistic.TIME_SINCE_REST, 72000);
            } else Ukutils.sendMessage(sender, ChatColor.RED + "you're not a player");
        } catch (SQLException e) {
            Ukutils.sendMessage(sender, ChatColor.RED + "An error occurred and the phantom state could not be changed.");
            Bukkit.getLogger().warning("[ukutils] Error: could not execute SQL statement");
            e.printStackTrace();
        }
    }
}
