package me.blok.guard;

import me.blok.guard.command.*;
import me.blok.guard.data.PlayerInv;
import me.blok.guard.listener.PlayerListener;
import me.blok.guard.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public final class Guard extends JavaPlugin {

    private static Guard instance;
    private Location jailLocation;

    private ArrayList<UUID> jailedPlayers;
    private ArrayList<UUID> unJailed;
    private  HashMap<UUID, PlayerInv> onDuty;
    Files files = new Files();


    @Override
    public void onEnable() {
        instance = this;
        files.createFiles(this);
        registerCommands();
        jailedPlayers = new ArrayList<>();
        onDuty = new HashMap<>();
        unJailed = new ArrayList<>();

        FileConfiguration players = Files.getInstance().getPlayers();
        for (String uuid : players.getKeys(false)){
            if(players.getString(uuid  + ".inventory") != null){
                onDuty.put(UUID.fromString(uuid), InventoryUtils.playerInventoryFromString(players.getString(uuid + ".inventory")));
            }
        }

        if(players.getStringList("jailed") != null){
            for (String uuid : players.getStringList("jailed")){
                jailedPlayers.add(UUID.fromString(uuid));
            }
        }

        if(players.getStringList("jailed") != null){
            for (String uuid : players.getStringList("unjailed")){
                unJailed.add(UUID.fromString(uuid));
            }
        }

        Bukkit.getLogger().info("[Guard] Loaded on duty guards and jail time!");

        double x = getConfig().getDouble("jail.x");
        double y = getConfig().getDouble("jail.y");
        double z = getConfig().getDouble("jail.z");
        jailLocation = new Location(Bukkit.getWorld(getConfig().getString("jail.world")), x, y, z);
        if(jailLocation == null){
            Bukkit.getLogger().severe("[Guard] Jail Location couldn't be loaded!");
        }else{
            Bukkit.getLogger().info("[Guard] Loaded jail location!");
        }

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new DutyPlaceHolder(this).hook();
        }

    }

    @Override
    public void onDisable() {
        FileConfiguration players = Files.getInstance().getPlayers();

        for (Map.Entry<UUID, PlayerInv> onDuty : onDuty.entrySet()){
            players.set(onDuty.getKey().toString() + ".inventory", InventoryUtils.playerInvToString(onDuty.getValue()));
        }

        getConfig().set("jail.x", jailLocation.getX());
        getConfig().set("jail.y", jailLocation.getY());
        getConfig().set("jail.z", jailLocation.getZ());
        getConfig().set("jail.world", jailLocation.getWorld().getName());

        players.set("jailed", jailedPlayers.stream().map(UUID::toString).collect(Collectors.toList())); //Save list to file
        players.set("unjailed", unJailed.stream().map(UUID::toString).collect(Collectors.toList())); //Save list of players to unjail upon login

        Files.getInstance().saveMessages();
        Files.getInstance().savePlayers();
        saveConfig();
    }

    private void registerCommands(){
        getCommand("onduty").setExecutor(new OnDutyCommand());
        getCommand("offduty").setExecutor(new OffDutyCommand());
        getCommand("setjail").setExecutor(new JailSetCommand());
        getCommand("jail").setExecutor(new JailCommand());
        getCommand("unjail").setExecutor(new UnjailCommand());
        getCommand("duty").setExecutor(new DutyCommand());
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static Guard getInstance() {
        return instance;
    }

    public ArrayList<UUID> getJailedPlayers() {
        return jailedPlayers;
    }

    public HashMap<UUID, PlayerInv> getOnDuty() {
        return onDuty;
    }

    public Location getJailLocation() {
        return jailLocation;
    }

    public void setJailLocation(Location jailLocation) {
        this.jailLocation = jailLocation;
    }

    public ArrayList<UUID> getUnJailed() {
        return unJailed;
    }
}
