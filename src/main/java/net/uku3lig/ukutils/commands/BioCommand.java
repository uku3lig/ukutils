package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public class BioCommand extends UkutilsCommand {
    private static final String BIO_FORMAT = "lp user %s meta setsuffix 1000 \"%s\"";
    private static final String REMOVE_BIO_FORMAT = "lp user %s meta removesuffix 1000";
    private static final String COLOR_PATTERN = "&?#[a-fA-F0-9]{6}|&[a-f0-9k-or]";

    public BioCommand(Ukutils plugin) {
        super(plugin);
    }

    @Override
    public String command() {
        return "bio";
    }

    @Override
    public Set<String> depends() {
        return Collections.singleton("LuckPerms");
    }

    @Override
    public void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player && args.length < 2) {
            if (args.length == 0) resetBio(sender, player);
            else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target != null && sender.hasPermission("ukutils.bio.others")) resetBio(sender, target);
                else changeBio(sender, player, args[0]);
            }
        } else {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) Ukutils.sendMessage(sender, ChatColor.RED + "Error: cannot find that player");
            else if (!sender.hasPermission("ukutils.bio.others"))
                Ukutils.sendMessage(sender, ChatColor.RED + "Error: cannot change this person's bio");
            else changeBio(sender, target, args[0]);
        }
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
