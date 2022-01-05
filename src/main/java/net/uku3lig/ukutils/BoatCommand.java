package net.uku3lig.ukutils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class BoatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            player.getWorld().spawnEntity(player.getLocation(), EntityType.BOAT);
            Ukutils.sendMessage(sender, "Successfully summoned boat.");
        } else Ukutils.sendMessage(sender, ChatColor.RED + "Error: you are not a player.");
        return true;
    }
}
