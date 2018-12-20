package me.blok.guard;

import me.blok.guard.command.DutyCommand;
import me.blok.guard.command.OffDutyCommand;
import me.blok.guard.command.OnDutyCommand;
import me.blok.guard.data.Messages;
import me.blok.guard.data.PlayerInv;
import me.blok.guard.listener.PlayerListener;
import me.blok.guard.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Guard extends JavaPlugin {

    private static Guard instance;

    private HashMap<UUID, PlayerInv> onDuty;
    private Files files = new Files();
    private String itemContains;


    @Override
    public void onEnable() {
        instance = this;
        files.createFiles(this);
        Messages.reload();
        registerCommands();
        onDuty = new HashMap<>();

        loadPlayers();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new DutyPlaceHolder(this).hook();
        }

    }

    @Override
    public void onDisable() {
        savePlayers();
    }

    private void registerCommands() {
        getCommand("onduty").setExecutor(new OnDutyCommand());
        getCommand("offduty").setExecutor(new OffDutyCommand());
        getCommand("duty").setExecutor(new DutyCommand());
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static Guard getInstance() {
        return instance;
    }


    public HashMap<UUID, PlayerInv> getOnDuty() {
        return onDuty;
    }


    public String getItemContains() {
        return itemContains;
    }

    public void savePlayers() {
        FileConfiguration players = Files.getInstance().getPlayers();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (this.onDuty.containsKey(player.getUniqueId())) {
                continue;
            }

            players.set(player.getUniqueId().toString(), null);
        }

        for (Map.Entry<UUID, PlayerInv> od : onDuty.entrySet()) {
            players.set(od.getKey().toString() + ".inventory", InventoryUtils.playerInvToString(od.getValue()));
        }

        //players.set("jailed", jailedPlayers.stream().map(UUID::toString).collect(Collectors.toList())); //Save list to file
        //players.set("unjailed", unJailed.stream().map(UUID::toString).collect(Collectors.toList())); //Save list of players to unjail upon login //Removed jail feature

        Files.getInstance().savePlayers();
        saveConfig();
    }

    public void loadPlayers() {
        FileConfiguration players = Files.getInstance().getPlayers();
        for (String uuid : players.getKeys(false)) {
            if (players.getString(uuid + ".inventory") != null) {
                onDuty.put(UUID.fromString(uuid), InventoryUtils.playerInventoryFromString(players.getString(uuid + ".inventory")));
            }
        }

        itemContains = getConfig().getString("guardItemContains");
        Bukkit.getLogger().info("[Guard] Loaded All player information and item information");

    }
}
