package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderchestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0 && player.hasPermission("ukutils.ec.others"))
                openEC(player, Bukkit.getPlayerExact(args[0]));
            else openEC(player, player);
        } else Ukutils.sendMessage(sender, ChatColor.RED + "Error: you are not a player.");
        return true;
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
