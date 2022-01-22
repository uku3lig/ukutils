package net.uku3lig.ukutils.commands;

import net.uku3lig.ukutils.Ukutils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TitleCommand implements CommandExecutor {
    private static final String TITLE_FORMAT = "lp user %s meta setprefix 1000 \"&r[%s&r]\"";
    private static final String REMOVE_TITLE_FORMAT = "lp user %s meta removeprefix 1000";
    private static final String COLOR_PATTERN = "&?#[a-f0-9]{6}|&[a-f0-9]";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = Ukutils.parseArgs(args);
        System.out.println(arguments);
        if (sender instanceof Player player && arguments.size() < 2) {
            if (arguments.isEmpty()) resetTitle(sender, player);
            else {
                Player target = Bukkit.getPlayerExact(arguments.get(0));
                if (target != null && sender.hasPermission("ukutils.title.others")) resetTitle(sender, target);
                else changeTitle(sender, player, arguments.get(0));
            }
        } else {
            Player target = Bukkit.getPlayerExact(arguments.get(0));
            if (target == null) Ukutils.sendMessage(sender, ChatColor.RED + "Error: cannot find that player");
            else if (!sender.hasPermission("ukutils.title.others"))
                Ukutils.sendMessage(sender, ChatColor.RED + "Error: cannot change this person's title");
            else changeTitle(sender, target, arguments.get(1));
        }
        return true;
    }

    private void resetTitle(CommandSender sender, Player target) {
        String c = REMOVE_TITLE_FORMAT.formatted(target.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
        Ukutils.sendMessage(sender, "Successfully removed title.");
    }

    private void changeTitle(CommandSender sender, Player target, String title) {
        int maxLength = Math.min(title.length(), target.hasPermission("ukutils.title.longer") ? 20 : 15);
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
