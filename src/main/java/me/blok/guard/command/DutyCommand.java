package me.blok.guard.command;

import me.blok.guard.Guard;
import me.blok.guard.data.Messages;
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

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!p.hasPermission("guardduty.reload")) {
                    p.sendMessage(ChatUtils.message("&cNo permission!", p));
                    return false;
                }

                p.sendMessage(ChatUtils.message("&eReloading all messages and data...", p));
                Guard.getInstance().reloadConfig();
                Guard.getInstance().savePlayers();
                Guard.getInstance().getOnDuty().clear();
                Guard.getInstance().loadPlayers();
                p.sendMessage(ChatUtils.message("&eLoaded all player data!", p));
                Messages.reload();
                p.sendMessage(ChatUtils.message("&eLoaded all messages!", p));
                p.sendMessage(ChatUtils.message("&eReload complete!", p));
                return true;
            }
            if (!p.hasPermission("guard.help")) {
                p.sendMessage(ChatUtils.format("&cNo permission!"));
                return false;
            }
            p.sendMessage(ChatUtils.message("&eGuard Duty Help:", p));
            p.sendMessage(ChatUtils.format("&e/onduty - Toggles guard duty on"));
            p.sendMessage(ChatUtils.format("&e/offduty - Toggles guard duty off"));
            p.sendMessage(ChatUtils.format("&e/duty reload - Reloads all messages and player data"));
        }


        if(!p.hasPermission("guard.help")){
            p.sendMessage(ChatUtils.format("&cNo permission!"));
            return false;
        }
        p.sendMessage(ChatUtils.message("&eGuard Duty Help:", p));
        p.sendMessage(ChatUtils.format("&e/onduty - Toggles guard duty on"));
        p.sendMessage(ChatUtils.format("&e/offduty - Toggles guard duty off"));
        p.sendMessage(ChatUtils.format("&e/duty reload - Reloads all messages and player data"));

        return false;
    }
}
