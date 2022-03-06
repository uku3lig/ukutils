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

public class TitleCommand extends UkutilsCommand {
    private static final String TITLE_FORMAT = "lp user %s meta setprefix 1000 \"&r[%s&r]\"";
    private static final String REMOVE_TITLE_FORMAT = "lp user %s meta removeprefix 1000";
    private static final String COLOR_PATTERN = "&?#[a-fA-F0-9]{6}|&[a-f0-9k-or]";

    public TitleCommand(Ukutils plugin) {
        super(plugin);
    }

    @Override
    public String command() {
        return "title";
    }

    @Override
    public Set<String> depends() {
        return Collections.singleton("LuckPerms");
    }

    @Override
    public void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player && args.length < 2) {
            if (args.length == 0) resetTitle(sender, player);
            else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target != null && sender.hasPermission("ukutils.title.others")) resetTitle(sender, target);
                else changeTitle(sender, player, args[0]);
            }
        } else {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) Ukutils.sendMessage(sender, ChatColor.RED + "Error: cannot find that player");
            else if (!sender.hasPermission("ukutils.title.others"))
                Ukutils.sendMessage(sender, ChatColor.RED + "Error: cannot change this person's title");
            else changeTitle(sender, target, args[0]);
        }
    }

    private void resetTitle(CommandSender sender, Player target) {
        String c = REMOVE_TITLE_FORMAT.formatted(target.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
        Ukutils.sendMessage(sender, "Successfully removed title.");
    }

    private void changeTitle(CommandSender sender, Player target, String title) {
        int maxLength = Math.min(title.length(), plugin.getConfig().getInt("title." +
                (target.hasPermission("ukutils.title.longer") ? "longer-length" : "base-length")));
        if (title.replaceAll(COLOR_PATTERN, "").length() > maxLength)
            Ukutils.sendMessage(sender, ChatColor.RED + "Error: the title cannot be longer than " +
                    maxLength + " characters! (excluding color codes)");
        else {
            String c = TITLE_FORMAT.formatted(target.getName(), title);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
            Ukutils.sendMessage(sender, "Successfully changed title.");
        }
    }
}
