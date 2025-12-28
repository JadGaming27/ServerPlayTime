package com.JadGameDev27.serverPlayTime.commands;

import com.JadGameDev27.serverPlayTime.Main;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerPlayTimeCommand implements CommandExecutor {

    private final Main plugin;

    public ServerPlayTimeCommand (Main instance){
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player (not console)
//        if (!(sender instanceof Player)) {
//            sender.sendMessage("Only players can use this command!");
//            return true;
//        }

        //Player player = (Player) sender;

        // Command logic here
        StringBuilder res = new StringBuilder();
        boolean use24hr = plugin.getConfig().getBoolean("use24hrformat");
        res.append(ChatColor.WHITE+"All World Times :"+ChatColor.RESET+"\n");
        World[] worlds = sender.getServer().getWorlds().toArray(new World[0]);
        for (World w : worlds) {
            String t = use24hr ? get24HourTime(w) : get12HourTime(w);
            res.append(ChatColor.WHITE+"• "+ChatColor.YELLOW).append(w.getName()).append(ChatColor.WHITE+": ").append(ChatColor.GREEN+t).append(ChatColor.WHITE+" (").append(w.getGameTime() / 24000).append(" days (actual) / ").append(w.getFullTime()/24000).append(" days)"+ChatColor.RESET+"\n");
        }
        res.append("\n"+ChatColor.BOLD+ChatColor.AQUA+"Server Age : " + ChatColor.GREEN+getRealLifeDuration(worlds[0])+ChatColor.RESET+"\n");
        res.append("\n"+ChatColor.DARK_GRAY+"*Actual time is the real ticks / not affected by sleep*"+ChatColor.RESET);

        sender.sendMessage(res.toString());

        return true;
    }
    public String get24HourTime(World world) {
        long ticks = world.getTime(); // Get relative time (0-24000)

        // Calculate hours (0-23)
        // 0 ticks = 6:00 AM, so we add 6 hours and use modulo 24
        long hours = ((ticks / 1000) + 6) % 24;

        // Calculate minutes (0-59)
        // There are 1000 ticks per hour; 1000 / 60 = 16.6 ticks per minute
        long minutes = (ticks % 1000) * 60 / 1000;

        return String.format("%02d:%02d", hours, minutes);
    }
    public String get12HourTime(World world) {
        long ticks = world.getTime();

        // 1. Calculate 24-hour format first (0-23)
        int hours24 = (int) (((ticks / 1000) + 6) % 24);

        // 2. Calculate minutes (0-59)
        int minutes = (int) ((ticks % 1000) * 60 / 1000);

        // 3. Determine AM or PM
        String period = (hours24 < 12) ? "AM" : "PM";

        // 4. Convert 24h hour to 12h hour
        int hours12 = hours24 % 12;
        if (hours12 == 0) hours12 = 12; // Midnight or Noon should show as 12

        return String.format("%d:%02d%s", hours12, minutes, period);
    }
    public String getRealLifeDuration(World world) {
        // 1. Get absolute world age (cannot be reset by /time set)
        long totalTicks = world.getGameTime();

        // 2. Convert ticks to real-life seconds (20 ticks = 1 second)
        long totalSeconds = totalTicks / 20;

        // 3. Break down into Days, Hours, Minutes, and Seconds
        long days = TimeUnit.SECONDS.toDays(totalSeconds);
        long hours = TimeUnit.SECONDS.toHours(totalSeconds) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60;
        long seconds = totalSeconds % 60;

        return String.format("%d days, %d hours, %d minutes, %d seconds",
                days, hours, minutes, seconds);
    }
}
