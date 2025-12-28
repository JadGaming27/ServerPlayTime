package com.JadGameDev27.serverPlayTime.commands;

import com.JadGameDev27.serverPlayTime.Main;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Use24HrFormatCommand implements CommandExecutor, TabCompleter {

    private final Main plugin;

    public Use24HrFormatCommand (Main instance){
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player (not console)
        if (!(sender instanceof ConsoleCommandSender && sender.isOp())) {
            sender.sendMessage(ChatColor.RED + "You don't have access to this command!");
            return true;
        }
        if (args.length > 0) {
            switch (args[0]){
                case "true":
                    plugin.getConfig().set("use24hrformat", true);
                    plugin.saveConfig();
                    sender.sendMessage("Changed Use 24-Hour Format to : true");
                    break;
                case "false":
                    plugin.getConfig().set("use24hrformat", false);
                    plugin.saveConfig();
                    sender.sendMessage("Changed Use 24-Hour Format to : false");
                    break;
                default:
                    sender.sendMessage(ChatColor.RED+"Invalid Argument! (true/false)");
                    break;
            }
        } else {
            boolean use24hr = plugin.getConfig().getBoolean("use24hrformat");
            sender.sendMessage("Use 24-Hour Format is currently set to : " + Boolean.toString(use24hr));
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return List.of("false", "true");
        } else
            return null;
    }
}
