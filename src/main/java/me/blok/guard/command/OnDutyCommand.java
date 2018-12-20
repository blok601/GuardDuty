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
 * Created by Blok on 6/3/2018.
 */
public class OnDutyCommand implements CommandExecutor{


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
            p.sendMessage(ChatUtils.message("&cUsage: /onduty", p));
            return false;
        }

        if(Guard.getInstance().getOnDuty().containsKey(p.getUniqueId())){
            p.sendMessage(ChatUtils.message(Messages.ALREADY_ON_DUTY, p));
            return false;
        }

        PlayerInv inv = new PlayerInv(p);
        inv.setContents(p.getInventory().getContents());
        inv.setArmorContents(p.getInventory().getArmorContents());
        Guard.getInstance().getOnDuty().put(p.getUniqueId(), inv);

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.performCommand("essentials:kit guard");

        String msg = ChatUtils.translate(Messages.NOW_ON_DUTY, p);
        p.sendMessage(ChatUtils.message(msg, p));
        Bukkit.broadcastMessage(ChatUtils.format(ChatUtils.translate(Messages.BROADCAST_ON_DUTY, p)));
        return false;
    }
}
