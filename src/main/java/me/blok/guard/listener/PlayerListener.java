package me.blok.guard.listener;

import me.blok.guard.Guard;
import me.blok.guard.data.Messages;
import me.blok.guard.data.PlayerInv;
import me.blok.guard.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blok on 6/11/2018.
 */
public class PlayerListener implements Listener {

    List<String> allowed = Guard.getInstance().getConfig().getStringList("allowedcommands");
    ArrayList<Player> toRespawn = new ArrayList<>();

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        if(Guard.getInstance().getJailedPlayers().contains(e.getPlayer().getUniqueId())){
            String[] command = e.getMessage().split("/");
            if(!allowed.contains(command)){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatUtils.message(Messages.NOT_ALLOWED_COMMAND));
                return;
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(Guard.getInstance().getUnJailed().contains(p.getUniqueId())){
            //un jail the player

            Guard.getInstance().getUnJailed().remove(p.getUniqueId());
            p.sendMessage(ChatUtils.message(Messages.FINISHED_JAILTIME));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + p.getName());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        if(Guard.getInstance().getOnDuty().containsKey(p.getUniqueId())){
            e.getDrops().clear();
            toRespawn.add(p);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        if(toRespawn.contains(p)){
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            PlayerInv inv = Guard.getInstance().getOnDuty().get(p.getUniqueId());
            p.getInventory().setContents(inv.getContents());
            p.getInventory().setArmorContents(inv.getArmorContents());
            Guard.getInstance().getOnDuty().remove(p.getUniqueId());
            p.sendMessage(ChatUtils.message(Messages.NOW_OFF_DUTY));
            toRespawn.remove(p);
        }
    }

}
