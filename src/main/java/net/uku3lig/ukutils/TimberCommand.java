package net.uku3lig.ukutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimberCommand implements CommandExecutor {
    private static final String TOGGLE_TIMBER = "lp user %s permission set ultimatetimber.chop %b";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            boolean perm = player.hasPermission("ultimatetimber.chop");
            String c = TOGGLE_TIMBER.formatted(player.getName(), !perm);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
            Ukutils.sendMessage(sender, "Successfully " + (perm ? "disabled" : "enabled") + " tree feller.");
        } else Ukutils.sendMessage(sender, ChatColor.RED + "Error: you're not a player.");
        return true;
    }
}
