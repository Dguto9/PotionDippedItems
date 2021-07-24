package com.potionenchants.plugin;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main instance;

	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(new EventListener(), this);
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

        return false;
    }
	
}
