package net.uku3lig.ukutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TitleCommand implements CommandExecutor {
    private static final String TITLE_FORMAT = "lp user %s meta setprefix 1000 \"&r[%s&r]\"";
    private static final String REMOVE_TITLE_FORMAT = "lp user %s meta removeprefix 1000";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
            else changeTitle(sender, target, args[1]);
        }
        return true;
    }

    private void resetTitle(CommandSender sender, Player target) {
        String c = REMOVE_TITLE_FORMAT.formatted(target.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
        Ukutils.sendMessage(sender, "Successfully removed title.");
    }

    private void changeTitle(CommandSender sender, Player target, String title) {
        int length = Math.min(title.length(), target.hasPermission("ukutils.title.longer") ? 20 : 15);
        String c = TITLE_FORMAT.formatted(target.getName(), title.substring(0, length));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
        Ukutils.sendMessage(sender, "Successfully changed title.");
    }
}
