package net.uku3lig.ukutils.config;

import net.uku3lig.ukutils.config.mappings.OneToTwoMapping;
import net.uku3lig.ukutils.config.mappings.ZeroToOneMapping;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public record ConfigConverter(JavaPlugin plugin) {
    private static final List<Mapping> mappings = List.of(
            new ZeroToOneMapping(),
            new OneToTwoMapping()
    );

    public int getConfigVersion(FileConfiguration config) {
        if (!config.isSet("config-version")) return -1;
        return config.getInt("config-version");
    }

    public int getJarConfigVersion() {
        try {
            InputStream i = Objects.requireNonNull(ConfigConverter.class.getClassLoader().getResourceAsStream("config.yml"));
            FileConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(i));
            return getConfigVersion(config);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void updateConfig() throws IOException {
        int jarVer = getJarConfigVersion();
        int configVer = getConfigVersion(plugin.getConfig());
        FileConfiguration config = plugin.getConfig();

        if (jarVer == -1) return;
        if (jarVer == configVer) return;

        for (Mapping m : mappings) {
            if (m.from() != configVer) continue;
            config = m.convert(config);
            configVer = m.to();
        }

        File file = plugin.getDataFolder().toPath().resolve("config.yml").toFile();
        config.save(file);
    }
}
