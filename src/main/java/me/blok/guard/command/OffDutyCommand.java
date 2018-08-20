package me.blok.guard.command;

import me.blok.guard.Guard;
import me.blok.guard.data.Messages;
import me.blok.guard.data.PlayerInv;
import me.blok.guard.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/7/2018.
 */
public class OffDutyCommand implements CommandExecutor{
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

        if(!p.hasPermission("guardduty.toggle")){
            p.sendMessage(ChatUtils.format("&cNo permission!"));
            return false;
        }

        if(args.length != 0){
            p.sendMessage(ChatUtils.message("&cUsage: /offduty"));
            return false;
        }

        if(!Guard.getInstance().getOnDuty().containsKey(p.getUniqueId())){
            p.sendMessage(ChatUtils.message(Messages.ALREADY_ON_DUTY));
            return false;
        }

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        PlayerInv inv = Guard.getInstance().getOnDuty().get(p.getUniqueId());
        p.getInventory().setContents(inv.getContents());
        p.getInventory().setArmorContents(inv.getArmorContents());
        Guard.getInstance().getOnDuty().remove(p.getUniqueId());

        p.sendMessage(ChatUtils.message(Messages.NOW_OFF_DUTY));
        Bukkit.broadcastMessage(ChatUtils.translate(Messages.BROADCAST_OFF_DUTY, p));

        return false;
    }
}
