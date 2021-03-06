package me.blok.guard.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by Blok on 8/9/2017.
 */
public class PlayerInv {

    private Player player;
    private ItemStack[] contents;
    private ItemStack[] armorContents;

    public PlayerInv() {
    }

    public PlayerInv(ItemStack[] contents, ItemStack[] armorContents) {
        this.contents = contents;
        this.armorContents = armorContents;
    }

    public PlayerInv(Player player) {
        this.player = player;
    }

    public static PlayerInv fromPlayerInventory(PlayerInventory inv) {
        return new PlayerInv(inv.getContents(), inv.getArmorContents());
    }

    public ItemStack[] getContents() {
        return this.contents;
    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    public ItemStack[] getArmorContents() {
        return this.armorContents;
    }

    public void setArmorContents(ItemStack[] armorContents) {
        this.armorContents = armorContents;
    }

    public ItemStack getHelmet() {
        return this.armorContents[3];
    }

    public ItemStack getChestPiece() {
        return this.armorContents[2];
    }

    public ItemStack getLeggings() {
        return this.armorContents[1];
    }

    public ItemStack getBoots() {
        return this.armorContents[0];
    }

    public ItemStack getSword() {
        return this.contents[0];
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
