package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;


public class DisabledCommand implements CommandExecutor {
    private final Collection<String> missing;

    public DisabledCommand(Collection<String> missing) {
        this.missing = missing;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Ukutils.sendMessage(sender, ChatColor.RED + "This command is disabled, please install " +
                String.join(", ", missing) + " and restart the server.");
        return true;
    }
}
