package me.blok.guard.command;

import me.blok.guard.Guard;
import me.blok.guard.data.Messages;
import me.blok.guard.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/11/2018.
 */
public class UnjailCommand implements CommandExecutor{
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
        if(!p.hasPermission("guardduty.unjail")){
            p.sendMessage(ChatUtils.format("&cNo permission!"));
            return false;
        }

        if(args.length != 1){
            p.sendMessage(ChatUtils.message("&cUsage: /unjail <player>"));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            if(offlinePlayer == null){
                p.sendMessage(ChatUtils.message("&cThat player is invalid!"));
                return false;
            }

            if(Guard.getInstance().getJailedPlayers().contains(offlinePlayer.getUniqueId()) && !Guard.getInstance().getUnJailed().contains(offlinePlayer.getUniqueId())){
                Guard.getInstance().getUnJailed().add(offlinePlayer.getUniqueId());
                Guard.getInstance().getJailedPlayers().remove(offlinePlayer.getUniqueId());
                p.sendMessage(ChatUtils.message(ChatUtils.translate(Messages.UNJAILED_PLAYER, args[0])));
                return true;
            }

            p.sendMessage(ChatUtils.message("&cThat player is not in jail!"));
            return false;
        }else{
            Guard.getInstance().getJailedPlayers().remove(target.getUniqueId());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + target.getName());
            p.sendMessage(ChatUtils.message(ChatUtils.translate(Messages.UNJAILED_PLAYER, target)));
            target.sendMessage(ChatUtils.message(Messages.FINISHED_JAILTIME));
        }
        return false;
    }
}
