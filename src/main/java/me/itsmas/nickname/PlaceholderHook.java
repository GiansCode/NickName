package me.itsmas.nickname;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class PlaceholderHook extends EZPlaceholderHook
{
    private final NickName plugin;

    PlaceholderHook(NickName plugin)
    {
        super(plugin, "nickname");

        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier)
    {
        if (identifier.equals("nick") && player != null)
        {
            return plugin.getNickManager().getNickName(player);
        }

        return null;
    }
}
