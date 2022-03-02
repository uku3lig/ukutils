package net.uku3lig.ukutils.config.mappings;

import net.uku3lig.ukutils.config.Mapping;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

public class ZeroToOneMapping implements Mapping {
    @Override
    public int from() {
        return -1;
    }

    @Override
    public int to() {
        return 1;
    }

    private static final String TITLE_PATH = "title";
    private static final String BANS_BROADCAST_PATH = "bans-broadcast";

    @Override
    public FileConfiguration convert(FileConfiguration config) {
        ConfigurationSection title = config.getConfigurationSection(TITLE_PATH);
        if (!config.isConfigurationSection(TITLE_PATH) || title == null) title = config.createSection(TITLE_PATH);
        if (!title.isSet("base-length")) title.set("base-length", 15);
        if (!title.isSet("longer-length")) title.set("longer-length", 20);

        if (!config.isSet("elytra-drop-chance")) config.set("elytra-drop-chance", 0.2);

        ConfigurationSection bansBroadcast = config.getConfigurationSection(BANS_BROADCAST_PATH);
        if (!config.isConfigurationSection(BANS_BROADCAST_PATH) || bansBroadcast == null)
            bansBroadcast = config.createSection(BANS_BROADCAST_PATH);
        if (!bansBroadcast.isSet("channel")) bansBroadcast.set("channel", "bans-mutes");
        if (!bansBroadcast.isSet("allowed")) bansBroadcast.set("allowed", Arrays.asList("muted", "warned", "warned.offline", "broadcast"));

        if (!config.isSet("disable-chorus-tp")) config.set("disable-chorus-tp", true);
        if (!config.isSet("phantoms-disabled-by-default")) config.set("phantoms-disabled-by-default", true);

        if (!config.isSet("config-version")) config.set("config-version", 1);

        return config;
    }
}
