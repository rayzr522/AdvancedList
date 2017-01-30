/**
 * 
 */
package com.rayzr522.advancedlist.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.rayzr522.advancedlist.AdvancedList;

/**
 * @author Rayzr
 *
 */
public class CommandAdvancedList implements CommandExecutor {

    private AdvancedList plugin;

    /**
     * @param advancedList
     */
    public CommandAdvancedList(AdvancedList plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reload();
            sender.sendMessage(plugin.getMessage("reloaded"));
        } else {
            sender.sendMessage(String.format(plugin.getMessage("version"), plugin.getDescription().getVersion()));
        }
        return true;
    }

}
