package ru.chaossmp.classes;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class ClassStore {
    private final JavaPlugin plugin;
    private final File file;
    private YamlConfiguration data;

    public ClassStore(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "players.yml");
        load();
    }

    public synchronized void load() {
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
        data = YamlConfiguration.loadConfiguration(file);
    }

    public synchronized ClassType get(UUID uuid) {
        return ClassType.fromId(data.getString("players." + uuid));
    }

    public synchronized void set(UUID uuid, ClassType type) {
        data.set("players." + uuid, type == null ? null : type.name());
        save();
    }

    public synchronized void set(Player player, ClassType type) { set(player.getUniqueId(), type); }

    public synchronized void save() {
        try { data.save(file); }
        catch (IOException ex) { plugin.getLogger().severe("Не удалось сохранить players.yml: " + ex.getMessage()); }
    }
}
