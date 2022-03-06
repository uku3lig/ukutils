package net.uku3lig.ukutils;

import lombok.Getter;
import net.uku3lig.ukutils.commands.DisabledCommand;
import net.uku3lig.ukutils.commands.UkutilsCommand;
import net.uku3lig.ukutils.config.ConfigConverter;
import net.uku3lig.ukutils.listeners.ChorusListener;
import net.uku3lig.ukutils.listeners.LitebansListener;
import net.uku3lig.ukutils.listeners.RenewableElytraListener;
import net.uku3lig.ukutils.util.Database;
import net.uku3lig.ukutils.util.ReflectionsUtil;
import net.uku3lig.ukutils.util.StatResetTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.advancement.Advancement;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public final class Ukutils extends JavaPlugin {
    private static final String UKUTILS_PREFIX = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA +
            "ukutils" + ChatColor.GRAY + "]" + ChatColor.RESET;
    private static final Pattern QUOTE_PATTERN = Pattern.compile("[\"'][^\"']++[\"']|[^\\s]+");

    @Getter
    private final Database database = new Database(this);

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("ukutils started");
        saveDefaultConfig();

        try {
            new ConfigConverter(this).updateConfig();
        } catch (IOException e) {
            Bukkit.getLogger().severe("Error: could not update ukutils config");
            e.printStackTrace();
        }

        for (UkutilsCommand command : ReflectionsUtil.getCommands(this)) {
            PluginCommand pluginCommand = getCommand(command.command());
            if (pluginCommand == null) {
                Bukkit.getLogger().severe("command " + command.command() + " is not registered in plugin.yml!!!");
            } else if (!command.depends().stream().allMatch(d -> Bukkit.getPluginManager().isPluginEnabled(d))) {
                pluginCommand.setExecutor(new DisabledCommand(command.depends()));
            } else pluginCommand.setExecutor(command);
        }

        getServer().getPluginManager().registerEvents(new RenewableElytraListener(this), this);
        getServer().getPluginManager().registerEvents(new ChorusListener(this), this);

        if (Bukkit.getPluginManager().isPluginEnabled("LiteBans")) {
            LitebansListener.register(this);
        }

        // 1200 ticks = 20 * 60 = 1 irl minute
        new StatResetTask(this).runTaskTimerAsynchronously(this, 0, 1200);
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

    public static Location toMiddle(Location loc) {
        double x = loc.getX() + 0.5;
        double z = loc.getZ() + 0.5;
        return new Location(loc.getWorld(), x, loc.getY(), z, loc.getYaw(), loc.getPitch());
    }

    public static Advancement getAdvancement(String name) {
        Iterator<Advancement> it = Bukkit.getServer().advancementIterator();
        while (it.hasNext()) {
            Advancement next = it.next();
            if (next.getKey().getKey().equalsIgnoreCase(name)) return next;
        }
        throw new IllegalArgumentException("could not find this advancement");
    }
}
