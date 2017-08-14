package com.enderaura.gods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Gods extends JavaPlugin implements Listener{

    private ItemStack ares = new ItemStack(Material.ARROW, 1), zeus = new ItemStack(Material.BLAZE_ROD, 1), posieden = new ItemStack(Material.WATER_BUCKET, 1);
    private Inventory inventory = Bukkit.createInventory(null, 27, "§d§lChoose a god!");
    private SettingsManager settings = SettingsManager.getInstance();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);
        init();
        settings.setup(this);

    }

    @Override
    public void onDisable() {
        settings.saveData();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        if(settings.getData().getStringList("players") != null && settings.getData().getStringList("players").contains(event.getPlayer().getUniqueId().toString())){

            createPlayer(event.getPlayer());

        }
    }

    private void createPlayer(Player player){



    }

    private void init(){

        ItemMeta aresMeta = ares.getItemMeta();
        aresMeta.setDisplayName(getConfig().getString("gui.ares.name"));
        aresMeta.setLore(ChatColor.translateAlternateColorCodes('&', getConfig().getStringList("gui.ares.lore")));
        ares.setItemMeta(aresMeta);

    }

}
