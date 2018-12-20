package me.blok.guard.listener;

import me.blok.guard.Guard;
import me.blok.guard.data.Messages;
import me.blok.guard.data.PlayerInv;
import me.blok.guard.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by Blok on 6/11/2018.
 */
public class PlayerListener implements Listener {

    ArrayList<Player> toRespawn = new ArrayList<>();

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
            p.sendMessage(ChatUtils.message(Messages.NOW_OFF_DUTY, p));
            toRespawn.remove(p);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ItemStack itemStack = e.getItemDrop().getItemStack();
        if (!itemStack.hasItemMeta()) {
            return;
        }

        if (!itemStack.getItemMeta().hasDisplayName()) return;

        if (Guard.getInstance().getOnDuty().containsKey(p.getUniqueId())) {
            String name = itemStack.getItemMeta().getDisplayName();
            if (name.contains(Guard.getInstance().getItemContains())) {
                e.setCancelled(true); //Don't allow dropping it
            }
        }
    }

}
