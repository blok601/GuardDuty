package me.blok.guard.command;

import me.blok.guard.Guard;
import me.blok.guard.data.Messages;
import me.blok.guard.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/8/2018.
 */
public class JailSetCommand implements CommandExecutor{
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
        if(!p.hasPermission("guardduty.jail.set")){
            p.sendMessage(ChatUtils.format("No permission!"));
            return false;
        }

        Location location = p.getLocation();
        Guard.getInstance().setJailLocation(location);

        p.sendMessage(ChatUtils.message(Messages.SET_JAIL));

        return false;
    }
}
