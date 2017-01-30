package com.rayzr522.advancedlist;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.advancedlist.command.CommandAdvancedList;
import com.rayzr522.advancedlist.command.CommandList;

import net.milkbowl.vault.permission.Permission;

/**
 * @author Rayzr
 */
public class AdvancedList extends JavaPlugin {

    private Map<String, String> ranks = new HashMap<String, String>();
    private Permission perms;

    @Override
    public void onEnable() {
        if (!setupPermissions()) {
            getLogger().severe("Failed to load permissions handler from Vault! Make sure you have a permissions plugin installed.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        reload();

        getCommand("list").setExecutor(new CommandList(this));
        getCommand("advancedlist").setExecutor(new CommandAdvancedList(this));
    }

    public void reload() {
        saveDefaultConfig();
        reloadConfig();

        if (!getFile("ranks.yml").exists()) {
            try {
                YamlConfiguration ranks = new YamlConfiguration();
                for (String rank : perms.getGroups()) {
                    ranks.set(rank, "&c" + rank);
                }
//                getFile("ranks.yml").createNewFile();
                ranks.save(getFile("ranks.yml"));
            } catch (IOException e) {
                getLogger().severe("Failed to save default ranks.yml");
            }
        }

        ranks.clear();

        YamlConfiguration ranksConfig = getConfig("ranks.yml");
        ranksConfig.getKeys(false).stream().forEach(s -> ranks.put(s, colorize(ranksConfig.getString(s))));
    }

    public YamlConfiguration getConfig(String path) {
        return YamlConfiguration.loadConfiguration(getFile(path));
    }

    public File getFile(String path) {
        return new File(getDataFolder(), path.replace('/', File.pathSeparatorChar));
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

    public String getDisplayRank(String rank) {
        return ranks.getOrDefault(rank, rank);
    }

}
