/**
 * 
 */
package com.rayzr522.advancedlist.command;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.rayzr522.advancedlist.AdvancedList;

import net.milkbowl.vault.permission.Permission;

/**
 * @author Rayzr
 *
 */
public class CommandList implements CommandExecutor {

    private AdvancedList plugin;

    /**
     * @param advancedList
     */
    public CommandList(AdvancedList plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Permission perms = plugin.getPerms();
        Map<String, String> output = new LinkedHashMap<>();

        String sep = plugin.getConfig().getString("name-separator");

        Bukkit.getOnlinePlayers().forEach(p -> {
            String rank = perms.getPrimaryGroup(p);
            if (!output.containsKey(rank)) {
                output.put(rank, p.getDisplayName());
            } else {
                output.put(rank, output.get(rank) + sep + p.getName());
            }
        });

        sender.sendMessage(String.format(plugin.getMessage("player-count"), Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers()));

        String rankForamt = plugin.getConfig().getString("rank-format");
        output.entrySet().stream().map(e -> String.format(rankForamt, e.getKey(), e.getValue())).map(plugin::colorize).forEach(sender::sendMessage);

        return true;
    }

}
