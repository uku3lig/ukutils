package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NetherCommand extends UkutilsCommand {
    public NetherCommand(Ukutils plugin) {
        super(plugin);
    }

    @Override
    public String command() {
        return "nether";
    }

    @Override
    public void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            Bukkit.getWorlds().stream()
                    .filter(w -> w.getEnvironment().equals(World.Environment.NETHER))
                    .findFirst()
                    .ifPresentOrElse(world -> {
                        if (!player.getAdvancementProgress(Ukutils.getAdvancement("nether/root")).isDone())
                            Ukutils.sendMessage(sender, ChatColor.RED + "Error: you have to visit the nether first!");
                        else {
                            player.teleport(Ukutils.toMiddle(world.getSpawnLocation()));
                            Ukutils.sendMessage(sender, "Successfully teleported you to the nether.");
                        }
                    }, () -> Ukutils.sendMessage(sender, ChatColor.RED + "Error: could not find nether."));
        } else Ukutils.sendMessage(sender, ChatColor.RED + "Error: you're not a player.");
    }
}
