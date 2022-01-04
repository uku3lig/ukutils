package net.uku3lig.ukutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ColorCommand implements CommandExecutor {
    private static final String COLORS = "[0-9a-f]";
    private static final String RGB_COLOR = "#[0-9a-f]{6}";
    private static final String COMMAND_FORMAT = "nick %s &%s%1$s";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player && args.length < 2) {
            if (args.length == 0) resetNick(sender, player);
            else { // reset nick for player or give color
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target != null && player.hasPermission("ukutils.color.others")) resetNick(sender, target);
                else changeColor(sender, player, args[0]);
            }
        } else {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) Ukutils.sendMessage(sender, ChatColor.RED + "Could not find this player");
            else if (!sender.hasPermission("ukutils.color.others"))
                Ukutils.sendMessage(sender, ChatColor.RED + "Error: cannot change this person's color");
            else changeColor(sender, target, args[1]);
        }

        return true;
    }

    private void resetNick(CommandSender sender, Player target) {
        String c = "nick %s off".formatted(target.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
        Ukutils.sendMessage(sender, "Your color has been reset.");
    }

    private void changeColor(CommandSender sender, Player target, String color) {
        if (color.matches(RGB_COLOR) && !target.hasPermission("ukutils.color.rgb"))
            Ukutils.sendMessage(sender, ChatColor.RED + "Error: you cannot use RGB colors. Please donate more.");
        else if (!color.matches(COLORS) && !color.matches(RGB_COLOR))
            Ukutils.sendMessage(sender, ChatColor.RED + "Error: use a minecraft color code or an hex color.");
        else {
            String c = COMMAND_FORMAT.formatted(target.getName(), color);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
            Ukutils.sendMessage(sender, "Your color has been changed!");
        }
    }
}
