package com.JadGameDev27.serverPlayTime;

import com.JadGameDev27.serverPlayTime.commands.ServerPlayTimeCommand;
import com.JadGameDev27.serverPlayTime.commands.Use24HrFormatCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Plugin startup logic
        this.getCommand("serverplaytime").setExecutor(new ServerPlayTimeCommand(this));
        this.getCommand("use24hrformat").setExecutor(new Use24HrFormatCommand(this));
        this.getCommand("use24hrformat").setTabCompleter(new Use24HrFormatCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
