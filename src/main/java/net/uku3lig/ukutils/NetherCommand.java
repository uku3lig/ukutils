package net.uku3lig.ukutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NetherCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            Bukkit.getWorlds().stream()
                    .filter(w -> w.getEnvironment().equals(World.Environment.NETHER))
                    .findFirst()
                    .ifPresentOrElse(world -> {
                        player.teleport(Ukutils.toMiddle(world.getSpawnLocation()));
                        Ukutils.sendMessage(sender, "Successfully teleported you to the nether.");
                    }, () -> Ukutils.sendMessage(sender, ChatColor.RED + "Error: could not find nether."));
        } else Ukutils.sendMessage(sender, ChatColor.RED + "Error: you're not a player.");
        return true;
    }
}
