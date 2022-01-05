package net.uku3lig.ukutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public final class Ukutils extends JavaPlugin {
    private static final String UKUTILS = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA +
            "ukutils" + ChatColor.GRAY + "]" + ChatColor.RESET;
    private static final Pattern QUOTE_PATTERN = Pattern.compile("[\"'][^\"']++[\"']|[^\\s]+");

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("ukutils started");
        getCommand("color").setExecutor(new ColorCommand());
        getCommand("title").setExecutor(new TitleCommand());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("ukutils stopped");
    }

    public static void sendMessage(CommandSender p, String message) {
        p.sendMessage(UKUTILS_PREFIX + " " + message);
    }

    public static List<String> parseArgs(String... args) {
        String unified = String.join(" ", args);
        return QUOTE_PATTERN.matcher(unified).results()
                .map(MatchResult::group)
                .map(s -> s.replace("\"", ""))
                .toList();
    }
}
