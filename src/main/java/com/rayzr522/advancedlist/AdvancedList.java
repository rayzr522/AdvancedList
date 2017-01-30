package com.rayzr522.advancedlist;

import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.advancedlist.command.CommandAdvancedList;
import com.rayzr522.advancedlist.command.CommandList;

import net.milkbowl.vault.permission.Permission;

/**
 * @author Rayzr
 */
public class AdvancedList extends JavaPlugin {

    private Permission perms;

    @Override
    public void onEnable() {
        if (!setupPermissions()) {
            getLogger().severe("Failed to load permissions handler from Vault! Make sure you have a permissions plugin installed.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        getCommand("list").setExecutor(new CommandList(this));
        getCommand("advancedlist").setExecutor(new CommandAdvancedList(this));
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public Permission getPerms() {
        return perms;
    }

    public String getMessage(String string) {
        return colorize(getConfig().getString("prefix") + getConfig().getString("messages." + string));
    }

    public String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

}
