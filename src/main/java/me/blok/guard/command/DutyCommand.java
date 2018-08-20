package me.blok.guard.command;

import me.blok.guard.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/11/2018.
 */
public class DutyCommand implements CommandExecutor{
    /**
     * Executes the given command, returning its success
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return false;
        }


        Player p = (Player) sender;
        if(!p.hasPermission("guard.help")){
            p.sendMessage(ChatUtils.format("&cNo permission!"));
            return false;
        }
        p.sendMessage(ChatUtils.message("&eGuard Duty Help:"));
        p.sendMessage(ChatUtils.format("&e/onduty - Toggles guard duty on"));
        p.sendMessage(ChatUtils.format("&e/offduty - Toggles guard duty off"));
        p.sendMessage(ChatUtils.format("&e/jail <player> - Jails a player"));
        p.sendMessage(ChatUtils.format("&e/unjail <player> - Unjails a player"));
        p.sendMessage(ChatUtils.format("&e/setjail - Set the jail"));


        return false;
    }
}
