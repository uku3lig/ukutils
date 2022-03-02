package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record BioCommand(JavaPlugin plugin) implements CommandExecutor {
    private static final String BIO_FORMAT = "lp user %s meta setsuffix 1000 \"%s\"";
    private static final String REMOVE_BIO_FORMAT = "lp user %s meta removesuffix 1000";
    private static final String COLOR_PATTERN = "&?#[a-fA-F0-9]{6}|&[a-f0-9k-or]";


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> arguments = Ukutils.parseArgs(args);
        System.out.println(arguments);
        if (sender instanceof Player player && arguments.size() < 2) {
            if (arguments.isEmpty()) resetBio(sender, player);
            else {
                Player target = Bukkit.getPlayerExact(arguments.get(0));
                if (target != null && sender.hasPermission("ukutils.bio.others")) resetBio(sender, target);
                else changeBio(sender, player, arguments.get(0));
            }
        } else {
            Player target = Bukkit.getPlayerExact(arguments.get(0));
            if (target == null) Ukutils.sendMessage(sender, ChatColor.RED + "Error: cannot find that player");
            else if (!sender.hasPermission("ukutils.bio.others"))
                Ukutils.sendMessage(sender, ChatColor.RED + "Error: cannot change this person's bio");
            else changeBio(sender, target, arguments.get(1));
        }
        return true;
    }

    private void resetBio(CommandSender sender, Player target) {
        String c = REMOVE_BIO_FORMAT.formatted(target.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
        Ukutils.sendMessage(sender, "Successfully removed bio.");
    }

    private void changeBio(CommandSender sender, Player target, String bio) {
        int maxLength = Math.min(bio.length(), plugin.getConfig().getInt("bio." +
                (target.hasPermission("ukutils.bio.longer") ? "longer-length" : "base-length")));
        if (bio.replaceAll(COLOR_PATTERN, "").length() > maxLength)
            Ukutils.sendMessage(sender, ChatColor.RED + "Error: the bio cannot be longer than " +
                    maxLength + " characters! (excluding color codes)");
        else {
            String c = BIO_FORMAT.formatted(target.getName(), bio);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
            Ukutils.sendMessage(sender, "Successfully changed bio.");
        }
    }
}
