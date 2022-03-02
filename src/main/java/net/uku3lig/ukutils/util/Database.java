package net.uku3lig.ukutils.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

public class Database {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ukutils_phantoms (uuid VARCHAR(36) PRIMARY KEY)";
    private static final String EXISTS = "SELECT EXISTS(SELECT uuid FROM ukutils_phantoms WHERE uuid = ?)";
    private static final String ADD = "INSERT OR REPLACE INTO ukutils_phantoms (uuid) VALUES (?)";
    private static final String REMOVE = "DELETE FROM ukutils_phantoms WHERE uuid = ?";
    private static final String GET = "SELECT uuid FROM ukutils_phantoms";

    private final JavaPlugin plugin;
    private final boolean phantomsDisabledDefault;
    private Connection connection;

    public Database(JavaPlugin plugin) {
        this.plugin = plugin;
        this.phantomsDisabledDefault = plugin.getConfig().getBoolean("phantoms-disabled-by-default");
    }

    private Connection getConnection() {
        Path db = plugin.getDataFolder().toPath().resolve("ukutils.db").normalize().toAbsolutePath();

        try {
            if (!Files.exists(db)) {
                Files.createFile(db);
            }
        } catch (IOException e) {
            Bukkit.getLogger().severe("[ukutils] Error: could not create database file!!");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
            throw new IllegalArgumentException(e);
        }

        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + db);

                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(CREATE_TABLE);
                }
            }

            return connection;
        } catch (SQLException e) {
            Bukkit.getLogger().severe("[ukutils] Error: could not initialize database");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
            throw new IllegalArgumentException(e);
        }
    }

    public boolean isPhantomDisabled(UUID uuid) throws SQLException {
        Objects.requireNonNull(uuid);
        try (PreparedStatement statement = getConnection().prepareStatement(EXISTS)) {
            statement.setString(1, uuid.toString());

            ResultSet result = statement.executeQuery();
            if (!result.next()) return phantomsDisabledDefault;

            return (result.getBoolean(1) && !phantomsDisabledDefault) || (!result.getBoolean(1) && phantomsDisabledDefault);
        }
    }

    public boolean togglePhantom(UUID uuid) throws SQLException {
        return setPhantomDisabled(uuid, !isPhantomDisabled(uuid));
    }

    public boolean setPhantomDisabled(UUID uuid, boolean disabled) throws SQLException {
        Objects.requireNonNull(uuid);

        String sql;
        if ((!disabled && phantomsDisabledDefault) || (disabled && !phantomsDisabledDefault)) sql = ADD;
        else sql = REMOVE;

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
            return disabled;
        }
    }

    public List<UUID> getPlayers() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            ResultSet result = statement.executeQuery(GET);
            List<UUID> uuid = new ArrayList<>();
            while (result.next()) {
                uuid.add(UUID.fromString(result.getString(1)));
            }
            return uuid;
        }
    }
}
