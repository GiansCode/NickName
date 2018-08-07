package me.itsmas.nickname;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class NickCommand implements CommandExecutor
{
    private final NickName plugin;

    private final String commandPermission;
    private final String colourPermission;
    private final String othersPermission;

    NickCommand(NickName plugin)
    {
        this.plugin = plugin;

        this.commandPermission = plugin.getConfig("permissions.nick_command");
        this.colourPermission = plugin.getConfig("permissions.nick_command_colours");
        this.othersPermission = plugin.getConfig("permissions.nick_command_others");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            Message.send(sender, Message.PLAYER_ONLY);
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission(commandPermission))
        {
            Message.send(player, Message.NO_PERMISSION);
            return true;
        }

        if (args.length == 0)
        {
            Message.send(player, Message.INVALID_USAGE);
            return true;
        }

        if (args[0].equalsIgnoreCase("off"))
        {
            plugin.getNickManager().resetNickName(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("set"))
        {
            if (!player.hasPermission(othersPermission))
            {
                Message.send(player, Message.NO_PERMISSION);
                return true;
            }

            if (args.length < 3)
            {
                Message.send(player, Message.INVALID_USAGE);
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null)
            {
                Message.send(player, Message.TARGET_OFFLINE);
                return true;
            }

            String nick = getNick(player, args, 2);

            Message.send(player, Message.NICK_UPDATED_OTHER, target.getName(), nick);
            plugin.getNickManager().updateNickName(target, nick);

            return true;
        }

        String nick = getNick(player, args, 0);
        plugin.getNickManager().updateNickName(player, nick);

        return true;
    }

    private String getNick(Player player, String[] args, int startIndex)
    {
        StringBuilder builder = new StringBuilder();

        for (int i = startIndex; i < args.length; i++)
        {
            builder.append(args[i]).append(" ");
        }

        String nick = builder.toString();

        if (player.hasPermission(colourPermission))
        {
            nick = ChatColor.translateAlternateColorCodes('&', nick);
        }

        return nick;
    }
}
