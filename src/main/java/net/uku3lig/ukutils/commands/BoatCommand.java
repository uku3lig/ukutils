package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BoatCommand extends UkutilsCommand {
    public BoatCommand(Ukutils plugin) {
        super(plugin);
    }

    @Override
    public String command() {
        return "boat";
    }

    @Override
    public void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            player.getWorld().spawnEntity(player.getLocation(), EntityType.BOAT);
            Ukutils.sendMessage(sender, "Successfully summoned boat.");
        } else Ukutils.sendMessage(sender, ChatColor.RED + "Error: you are not a player.");
    }
}
