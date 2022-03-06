package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnderchestCommand extends UkutilsCommand {
    public EnderchestCommand(Ukutils plugin) {
        super(plugin);
    }

    @Override
    public String command() {
        return "enderchest";
    }

    @Override
    public void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0 && player.hasPermission("ukutils.ec.others"))
                openEC(player, Bukkit.getPlayerExact(args[0]));
            else openEC(player, player);
        } else Ukutils.sendMessage(sender, ChatColor.RED + "Error: you are not a player.");
    }

    private void openEC(Player sender, Player player) {
        if (player == null) {
            Ukutils.sendMessage(sender, ChatColor.RED + "Error: could not find this player.");
            return;
        }

        if ((sender.equals(player) && player.getStatistic(Statistic.CRAFT_ITEM, Material.ENDER_CHEST) == 0) ||
                (!sender.equals(player) && !sender.hasPermission("ukutils.ec.others"))) {
            Ukutils.sendMessage(sender, ChatColor.RED + "You must craft an enderchest before using this command!");
            return;
        }

        sender.closeInventory();
        sender.openInventory(player.getEnderChest());
    }
}
