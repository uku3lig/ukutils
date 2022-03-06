package net.uku3lig.ukutils.config.mappings;

import net.uku3lig.ukutils.config.Mapping;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class OneToTwoMapping implements Mapping {
    @Override
    public int from() {
        return 1;
    }

    @Override
    public int to() {
        return 2;
    }

    private static final String DISCORD_PATH = "discord";

    @Override
    public FileConfiguration convert(FileConfiguration config) {
        ConfigurationSection discord = config.getConfigurationSection(DISCORD_PATH);
        if (discord == null) discord = config.createSection(DISCORD_PATH);

        if (!discord.isSet("enabled")) discord.set("enabled", true);
        if (!discord.isSet("bot-token")) discord.set("bot-token", "insert-your-token-here");

        return config;
    }
}
