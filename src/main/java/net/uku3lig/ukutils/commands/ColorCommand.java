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

public class ColorCommand extends UkutilsCommand {
    private static final String COLORS = "[0-9a-f]";
    private static final String RGB_COLOR = "#[0-9a-fA-F]{6}";
    private static final String COMMAND_FORMAT = "nick %s &%s%1$s";

    public ColorCommand(Ukutils plugin) {
        super(plugin);
    }

    @Override
    public String command() {
        return "color";
    }

    @Override
    public Set<String> depends() {
        return Collections.singleton("LuckPerms");
    }

    @Override
    public void onCommandReceived(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
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
