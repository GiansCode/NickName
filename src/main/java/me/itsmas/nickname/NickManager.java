package me.itsmas.nickname;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class NickManager implements Listener
{
    private final NickName plugin;

    NickManager(NickName plugin)
    {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final Map<UUID, String> nickMap = new HashMap<>();

    void updateNickName(Player player, String nick)
    {
        updateNickName(player, nick, true);
    }

    private void updateNickName(Player player, String nick, boolean inform)
    {
        nickMap.put(player.getUniqueId(), nick);
        plugin.getDataManager().updateNick(player, nick);

        if (inform)
        {
            Message.send(player, Message.NICK_UPDATED, nick);
        }
    }

    void resetNickName(Player player)
    {
        nickMap.remove(player.getUniqueId());
        plugin.getDataManager().resetNick(player);

        Message.send(player, Message.NICK_RESET);
    }

    String getNickName(Player player)
    {
        return nickMap.getOrDefault(player.getUniqueId(), player.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        if (!nickMap.containsKey(player.getUniqueId()))
        {
            String nick = plugin.getDataManager().loadNick(player);

            if (nick != null)
            {
                updateNickName(player, nick, false);
            }
        }
    }
}
