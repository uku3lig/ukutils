package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public abstract class UkutilsCommand implements CommandExecutor {
    protected final Ukutils plugin;

    protected UkutilsCommand(Ukutils plugin) {
        this.plugin = plugin;
    }

    public abstract String command();

    public Set<String> depends() {
        return Collections.emptySet();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        onCommandReceived(sender, command, label, Ukutils.parseArgs(args).toArray(new String[0]));
        return true;
    }

    public abstract void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);
}
