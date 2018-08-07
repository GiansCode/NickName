package me.itsmas.nickname;

import org.bukkit.plugin.java.JavaPlugin;

public class NickName extends JavaPlugin
{
    private DataManager dataManager;
    private NickManager nickManager;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        Message.init(this);

        dataManager = new DataManager(this);
        nickManager = new NickManager(this);

        new PlaceholderHook(this).hook();
        getCommand("nick").setExecutor(new NickCommand(this));
    }

    @Override
    public void onDisable()
    {
        getDataManager().save();
    }

    @SuppressWarnings("unchecked")
    <T> T getConfig(String path)
    {
        return (T) getConfig().get(path);
    }

    NickManager getNickManager()
    {
        return nickManager;
    }

    DataManager getDataManager()
    {
        return dataManager;
    }
}
