package net.uku3lig.ukutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ukutils extends JavaPlugin {
    private static final String UKUTILS = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA +
            "ukutils" + ChatColor.GRAY + "]" + ChatColor.RESET;

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
        p.sendMessage(UKUTILS + " " + message);
    }
}
