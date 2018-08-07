package me.itsmas.nickname;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

class DataManager
{
    private final NickName plugin;

    DataManager(NickName plugin)
    {
        this.plugin = plugin;

        loadFile();
    }

    private void loadFile()
    {
        file = new File(plugin.getDataFolder(), "data.yml");

        if (!file.exists())
        {
            plugin.saveResource("data.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    String loadNick(Player player)
    {
        return config.getString(player.getUniqueId().toString());
    }

    private File file;
    private YamlConfiguration config;

    void updateNick(Player player, String nick)
    {
        config.set(player.getUniqueId().toString(), nick);
    }

    void resetNick(Player player)
    {
        config.set(player.getUniqueId().toString(), null);
    }

    void save()
    {
        try
        {
            config.save(file);
        }
        catch (IOException ex)
        {
            plugin.getLogger().severe("Error saving data file:");
            ex.printStackTrace();
        }
    }
}
