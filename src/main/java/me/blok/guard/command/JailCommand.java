package me.blok.guard.command;

import me.blok.guard.Guard;
import me.blok.guard.data.Messages;
import me.blok.guard.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/8/2018.
 */
public class JailCommand implements CommandExecutor{
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
        if(!p.hasPermission("guardduty.jail")){
            p.sendMessage(ChatUtils.format("&cNo permission!"));
            return false;
        }

        if(args.length != 1){
           p.sendMessage(ChatUtils.message("&cUsage: /jail <player>"));
           return false;
        }

        if(Guard.getInstance().getJailLocation() == null){
            p.sendMessage(ChatUtils.message(Messages.JAIL_NOT_SET));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            p.sendMessage(ChatUtils.message("&cThat player couldn't be found!"));
            return false;
        }

        if(target.hasPermission("guardduty.bypass")){
            p.sendMessage(ChatUtils.message(Messages.BYPASSED_PLAYER));
            return false;
        }

        if(Guard.getInstance().getJailedPlayers().contains(target.getUniqueId())){
            p.sendMessage(ChatUtils.message(Messages.ALREADY_IN_JAIL));
            return false;
        }

        target.teleport(Guard.getInstance().getJailLocation());
        Guard.getInstance().getJailedPlayers().add(target.getUniqueId());
        target.sendMessage(ChatUtils.message(ChatUtils.translate(Messages.JAILED, p)));
        p.sendMessage(ChatUtils.message(ChatUtils.translate(Messages.SENT_TO_JAIL, target)));

        return false;
    }
}
