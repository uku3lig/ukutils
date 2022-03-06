package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TimberCommand extends UkutilsCommand {
    private static final String TOGGLE_TIMBER = "lp user %s permission set ultimatetimber.chop %b";

    public TimberCommand(Ukutils plugin) {
        super(plugin);
    }

    @Override
    public String command() {
        return "toggletimber";
    }

    @Override
    public Set<String> depends() {
        return Set.of("UltimateTimber", "LuckPerms");
    }

    @Override
    public void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            boolean perm = player.hasPermission("ultimatetimber.chop");
            String c = TOGGLE_TIMBER.formatted(player.getName(), !perm);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
            Ukutils.sendMessage(sender, "Successfully " + (perm ? "disabled" : "enabled") + " tree feller.");
        } else Ukutils.sendMessage(sender, ChatColor.RED + "Error: you're not a player.");
    }
}
