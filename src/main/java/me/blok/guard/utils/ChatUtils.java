package me.blok.guard.utils;

import me.blok.guard.Guard;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Created by Blok on 6/3/2018.
 */
public class ChatUtils {

    public static String message(String string, Player player){
        String prefix = Guard.getInstance().getConfig().getString("prefix");
        return format(prefix + PlaceholderAPI.setPlaceholders(player, string));
    }

    public static String format(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String translate(String string, Player player){

        if(string.contains("%player")){
            string = string.replaceAll("%player%", player.getName());
        }

        return ChatUtils.format(string);
    }

    public static String translate(String string, OfflinePlayer player){
        string = string.replaceAll("%player%", player.getName());
        return ChatUtils.format(string);
    }

    public static String translate(String string, String replace){
        string = string.replaceAll("%player%", replace);
        return ChatUtils.format(string);
    }

}
