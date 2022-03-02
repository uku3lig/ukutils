package net.uku3lig.ukutils.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface Mapping {
    int from();
    int to();
    FileConfiguration convert(FileConfiguration config);
}
