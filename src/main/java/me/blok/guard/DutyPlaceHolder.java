package me.blok.guard;

import me.blok.guard.utils.ChatUtils;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/11/2018.
 */
public class DutyPlaceHolder extends EZPlaceholderHook{

    private Guard guard;
    public DutyPlaceHolder(Guard guard){
        super(guard, "guard");

        this.guard = guard;
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        if(s.equalsIgnoreCase("duty")){
            if(Guard.getInstance().getOnDuty().containsKey(player.getUniqueId())){
                return ChatUtils.format(guard.getConfig().getString("on-duty-tag"));
            }else{
                return ChatUtils.format(guard.getConfig().getString("off-duty-tag"));
            }

        }

        return null;

    }
}
