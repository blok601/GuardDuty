package me.blok.guard;

import me.blok.guard.utils.ChatUtils;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by Blok on 6/11/2018.
 */
public class DutyPlaceHolder extends EZPlaceholderHook{

    public DutyPlaceHolder(Plugin plugin){
        super(plugin, "guard");
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        if(s.equalsIgnoreCase("duty")){
            if(Guard.getInstance().getOnDuty().containsKey(player.getUniqueId())){
                return ChatUtils.format(Files.getInstance().getMessages().getString("on-duty-tag"));
            }else{
                return ChatUtils.format(Files.getInstance().getMessages().getString("off-duty-tag"));
            }

        }

        return null;

    }
}
