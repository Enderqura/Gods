package com.enderaura.gods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Gods extends JavaPlugin implements Listener{

    private ItemStack ares = new ItemStack(Material.ARROW, 1), zeus = new ItemStack(Material.BLAZE_ROD, 1), posieden = new ItemStack(Material.WATER_BUCKET, 1);
    private Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.select_god.name")));
    private Inventory c = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.select_class.name")));
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

    List<String> zeusLore = new ArrayList<>(), posiedenLore = new ArrayList<>(), aresLore = new ArrayList<>();

        ItemMeta aresMeta = ares.getItemMeta();
        aresMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.ares.name")));
        for(String s : getConfig().getStringList("gui.ares.lore")) aresLore.add(ChatColor.translateAlternateColorCodes('&', s));
        aresMeta.setLore(aresLore);
        ares.setItemMeta(aresMeta);


        ItemMeta zeusMeta = zeus.getItemMeta();
        zeusMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.zeus.name")));
        for(String s : getConfig().getStringList("gui.zeus.lore")) zeusLore.add(ChatColor.translateAlternateColorCodes('&', s));
        zeusMeta.setLore(zeusLore);
        zeus.setItemMeta(zeusMeta);


        ItemMeta posiedenMeta = posieden.getItemMeta();
        posiedenMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.poseiden.name")));
        for(String s : getConfig().getStringList("gui.poseiden.lore")) posiedenLore.add(ChatColor.translateAlternateColorCodes('&', s));
        posiedenMeta.setLore(posiedenLore);
        posieden.setItemMeta(posiedenMeta);

        inventory.setItem(10, zeus);
        inventory.setItem(12, ares);
        inventory.setItem(14, posieden);

    }

    @EventHandler
    public void clickGod(InventoryClickEvent event){
        if(!event.getClickedInventory().equals(inventory)) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(event.getCurrentItem().equals(zeus)) godPlayer(God.ZEUS, (Player) event.getWhoClicked());
        if(event.getCurrentItem().equals(ares)) godPlayer(God.ARES, (Player) event.getWhoClicked());
        if(event.getCurrentItem().equals(zeus)) godPlayer(God.POSIEDEN, (Player) event.getWhoClicked());
    }

    @EventHandler
    public void clickClass(InventoryClickEvent event){
        if(!event.getClickedInventory().equals(inventory)) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(event.getCurrentItem().equals(zeus)) godPlayer(God.ZEUS, (Player) event.getWhoClicked());
        if(event.getCurrentItem().equals(ares)) godPlayer(God.ARES, (Player) event.getWhoClicked());
        if(event.getCurrentItem().equals(zeus)) godPlayer(God.POSIEDEN, (Player) event.getWhoClicked());
    }

    private void godPlayer(God god,Player player){
        settings.getData().set("player." + player.getUniqueId() + ".god", god.toString());
    }

    private void classPlayer(Class clazz, Player player){
        settings.getData().set("player." + player.getUniqueId() + ".class", clazz.toString());
    }
  
    public God getTeam(Player player){
        return God.valueOf(settings.getData().getString("player." + player.getUniqueId() + ".god"));
    }

    public Class getClass(Player player){


    }


}
