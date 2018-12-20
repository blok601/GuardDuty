package me.blok.guard;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Blok on 6/3/2018.
 */
public class Files {

    private static Files instance;

    private FileConfiguration messages;
    private File mFile;

    private FileConfiguration players;
    private File pFile;

    public void createFiles(Plugin plugin){
        instance = this;
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdirs();
        }

        mFile = new File(plugin.getDataFolder(), "messages.yml");
        if(!mFile.exists()){
            Bukkit.getServer().getLogger().info("[Guard] Messages.yml file not found, creating!");
            try{
                mFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe("[Guard] Messages.yml creation failed! Please report the following:");
                e.printStackTrace();
            }
        }

        messages  = YamlConfiguration.loadConfiguration(mFile);
        messages.options().copyDefaults(true);
        Bukkit.getLogger().info("[Guard] Successfully loaded messages.yml!");

        pFile = new File(plugin.getDataFolder(), "players.yml");
        if(!pFile.exists()){
            try {
                pFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe("[Guard] players.yml creation failed! Please report the following:");
                e.printStackTrace();
            }
        }
        players = YamlConfiguration.loadConfiguration(pFile);
        players.options().copyDefaults(true);
        Bukkit.getLogger().info("[Guard] Successfully loaded players.yml!");

        Guard.getInstance().getConfig().options().copyDefaults(true);
        Guard.getInstance().saveConfig();
        savePlayers();
        Bukkit.getLogger().info("[Guard] Successfully loaded config.yml!");
    }

    public static Files getInstance() {
        return instance;
    }


    public FileConfiguration getPlayers() {
        return players;
    }


    public void savePlayers(){
        try{
         players.save(pFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadPlayers(){
        players = YamlConfiguration.loadConfiguration(pFile);
    }

}
