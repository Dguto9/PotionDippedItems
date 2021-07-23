package com.potionenchants.plugin;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main instance;
	FileConfiguration config = getConfig();
	public static boolean debug;

	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		config.addDefault("debug-mode", true);
        config.options().copyDefaults(true);
        saveConfig();
        debug = config.getBoolean("debug-mode");
	}
	
	@Override
	public void onDisable() {
		saveConfig();
	}
	
	public static Main getInstance() {
		return instance;
		}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("potenchantdebug")) {
            config.set("debug-mode", !config.getBoolean("debug-mode"));
            sender.sendMessage("Debug mode has been set to:" + String.valueOf(config.getBoolean("debug-mode")));
            debug = config.getBoolean("debug-mode");
            return true;
        }
        if (command.getName().equalsIgnoreCase("potenchantlist")) {
            sender.sendMessage("The available potion enchants include: All available potions execpt for Turtlemaster  "); 
            return true;
        }
        return false;
    }
	
}
