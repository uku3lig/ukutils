package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EndCommand extends UkutilsCommand {
    public EndCommand(Ukutils plugin) {
        super(plugin);
    }

    @Override
    public String command() {
        return "end";
    }

    @Override
    public void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            Bukkit.getWorlds().stream()
                    .filter(w -> w.getEnvironment().equals(World.Environment.THE_END))
                    .findFirst()
                    .ifPresentOrElse(world -> {
                        if (!player.getAdvancementProgress(Ukutils.getAdvancement("end/root")).isDone())
                            Ukutils.sendMessage(sender, ChatColor.RED + "Error: you have to visit the end first!");
                        else {
                            player.teleport(Ukutils.toMiddle(world.getSpawnLocation()));
                            Ukutils.sendMessage(sender, "Successfully teleported you to the end.");
                        }
                    }, () -> Ukutils.sendMessage(sender, ChatColor.RED + "Error: could not find the end."));
        } else Ukutils.sendMessage(sender, ChatColor.RED + "Error: you're not a player.");
    }
}
