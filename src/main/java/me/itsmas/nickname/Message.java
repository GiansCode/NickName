package me.itsmas.nickname;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Message
{
    PLAYER_ONLY,
    INVALID_USAGE,
    TARGET_OFFLINE,
    NICK_RESET,
    NICK_UPDATED,
    NICK_UPDATED_OTHER,
    NO_PERMISSION;

    private String msg;

    private void setValue(String msg)
    {
        assert this.msg == null : "Message is already set";

        this.msg = msg;
    }

    public String value()
    {
        return msg;
    }

    static void send(CommandSender player, Message message, Object... params)
    {
        if (!message.value().isEmpty())
        {
            player.sendMessage(String.format(message.value(), params));
        }
    }

    static void init(NickName plugin)
    {
        String messagesPath = "messages";

        for (Message message : values())
        {
            String value = plugin.getConfig(messagesPath + "." + message.name().toLowerCase());

            if (value == null)
            {
                plugin.getLogger().severe("No value found for message " + message);
                continue;
            }

            message.setValue(ChatColor.translateAlternateColorCodes('&', value));
        }
    }
}

