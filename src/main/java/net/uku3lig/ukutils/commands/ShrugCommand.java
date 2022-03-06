package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShrugCommand extends UkutilsCommand {
    public ShrugCommand(Ukutils plugin) {
        super(plugin);
    }

    @Override
    public String command() {
        return "shrug";
    }

    @Override
    public void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            player.chat("¯\\_(ツ)_/¯");
        } else Ukutils.sendMessage(sender, "you are not a player");
    }
}
