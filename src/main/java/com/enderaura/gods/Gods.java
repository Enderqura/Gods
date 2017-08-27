package com.enderaura.gods;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FactionCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class Gods extends JavaPlugin implements Listener{

    private ItemStack ares = new ItemStack(Material.ARROW, 1), zeus = new ItemStack(Material.BLAZE_ROD, 1), posieden = new ItemStack(Material.WATER_BUCKET, 1);
    private ItemStack stragiton = new ItemStack(Material.ARROW, 1), pterarcho = new ItemStack(Material.BLAZE_ROD, 1), navarcho = new ItemStack(Material.WATER_BUCKET, 1), antinavarcho = new ItemStack(Material.BED);
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

    private void init(){

    List<String> zeusLore = new ArrayList<>(), posiedenLore = new ArrayList<>(), aresLore = new ArrayList<>(), stragitonLore = new ArrayList<>(), pterarchoLore = new ArrayList<>(), navarchoLore = new ArrayList<>(), antinavarchoLore = new ArrayList<>();

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

        ItemMeta stragitonMeta = stragiton.getItemMeta();
        stragitonMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.stragiton.name")));
        for(String s : getConfig().getStringList("gui.stragiton.lore")) stragitonLore.add(ChatColor.translateAlternateColorCodes('&', s));
        stragitonMeta.setLore(stragitonLore);
        stragiton.setItemMeta(stragitonMeta);

        ItemMeta pterarchoMeta = pterarcho.getItemMeta();
        pterarchoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.pterarcho.name")));
        for(String s : getConfig().getStringList("gui.pterarcho.lore")) pterarchoLore.add(ChatColor.translateAlternateColorCodes('&', s));
        pterarchoMeta.setLore(pterarchoLore);
        pterarcho.setItemMeta(pterarchoMeta);

        ItemMeta antinavarchoMeta = antinavarcho.getItemMeta();
        antinavarchoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.antinavarcho.name")));
        for(String s : getConfig().getStringList("gui.antinavarcho.lore")) antinavarchoLore.add(ChatColor.translateAlternateColorCodes('&', s));
        antinavarchoMeta.setLore(antinavarchoLore);
        antinavarcho.setItemMeta(antinavarchoMeta);

        ItemMeta navarchoMeta = navarcho.getItemMeta();
        navarchoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.navarcho.name")));
        for(String s : getConfig().getStringList("gui.navarcho.lore")) navarchoLore.add(ChatColor.translateAlternateColorCodes('&', s));
        navarchoMeta.setLore(navarchoLore);
        navarcho.setItemMeta(navarchoMeta);

        inventory.setItem(10, zeus);
        inventory.setItem(12, ares);
        inventory.setItem(14, posieden);

        c.setItem(10, antinavarcho);
        c.setItem(11, navarcho);
        c.setItem(13, pterarcho);
        c.setItem(14, stragiton);

    }

    @EventHandler
    public void clickGod(InventoryClickEvent event){
        if(!event.getClickedInventory().equals(inventory)) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(event.getCurrentItem().equals(zeus)) godPlayer(God.ZEUS, (Player) event.getWhoClicked());
        if(event.getCurrentItem().equals(ares)) godPlayer(God.ARES, (Player) event.getWhoClicked());
        if(event.getCurrentItem().equals(posieden)) godPlayer(God.POSIEDEN, (Player) event.getWhoClicked());
        event.getWhoClicked().openInventory(c);
    }

    @EventHandler
    public void clickClass(InventoryClickEvent event){
        if(!event.getClickedInventory().equals(inventory)) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(event.getCurrentItem().equals(stragiton)) classPlayer(Class.STRATIGON, (Player) event.getWhoClicked());
        if(event.getCurrentItem().equals(pterarcho)) classPlayer(Class.PTERARCHO, (Player) event.getWhoClicked());
        if(event.getCurrentItem().equals(navarcho)) classPlayer(Class.NAVARCHO, (Player) event.getWhoClicked());
        if(event.getCurrentItem().equals(antinavarcho)) classPlayer(Class.ANTINAVARCHO, (Player) event.getWhoClicked());
        event.getWhoClicked().closeInventory();
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        if(settings.getData().get("player." + e.getPlayer().getUniqueId()) != null) return;
        new BukkitRunnable(){
            @Override
            public void run() {
                e.getPlayer().openInventory(inventory);
            }
        }.runTaskLater(this, 20);
    }

    private void godPlayer(God god,Player player){
        settings.getData().set("player." + player.getUniqueId() + ".god", god.toString());
    }

    private void classPlayer(Class clazz, Player player){
        settings.getData().set("player." + player.getUniqueId() + ".class", clazz.toString());
        if(clazz == Class.STRATIGON) {
            player.getInventory().addItem(nameItem(new ItemStack(Material.IRON_SWORD), "&b&lStratigon's Sword"));
        }

        if(clazz == Class.PTERARCHO) {
            player.getInventory().addItem(nameItem(new ItemStack(Material.BOW), "&b&lPterarcho's Bow"));
        }

        if(clazz == Class.NAVARCHO) {
            player.getInventory().addItem(nameItem(new ItemStack(Material.IRON_AXE), "&b&lNavarcho's Axe"));
        }

        if(clazz == Class.ANTINAVARCHO) {
            player.getInventory().addItem(nameItem(new ItemStack(Material.IRON_AXE), "&b&lAntinavarcho's Axe"));
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();

        List<ItemStack> drops = new ArrayList<>();
        for(ItemStack item : e.getDrops()){
            if(!item.equals(nameItem(new ItemStack(Material.IRON_SWORD), "&b&lStratigon's Sword"))
            || !item.equals(nameItem(new ItemStack(Material.BOW), "&b&lPterarcho's Bow"))
            || !item.equals(nameItem(new ItemStack(Material.IRON_AXE), "&b&lNavarcho's Axe"))
            || !item.equals(nameItem(new ItemStack(Material.IRON_AXE), "&b&lAntinavarcho's Axe"))
            ) drops.add(item);
        }
        e.getDrops().clear();

        for(ItemStack item : drops){
            e.getDrops().add(item);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        Class clazz = getClass(e.getPlayer());

        if(clazz == Class.STRATIGON) {
            player.getInventory().addItem(nameItem(new ItemStack(Material.IRON_SWORD), "&b&lStratigon's Sword"));
        }

        if(clazz == Class.PTERARCHO) {
            player.getInventory().addItem(nameItem(new ItemStack(Material.BOW), "&b&lPterarcho's Bow"));
        }

        if(clazz == Class.NAVARCHO) {
            player.getInventory().addItem(nameItem(new ItemStack(Material.IRON_AXE), "&b&lNavarcho's Axe"));
        }

        if(clazz == Class.ANTINAVARCHO) {
            player.getInventory().addItem(nameItem(new ItemStack(Material.IRON_AXE), "&b&lAntinavarcho's Axe"));
        }
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();

        if(getTeam(player) == God.ARES){

            if(getClass(player) == Class.STRATIGON){
                e.setFormat(e.getFormat().replace("[GOD]", "A").replace("[CLASS]", "S"));
            }
            if(getClass(player) == Class.PTERARCHO){
                e.setFormat(e.getFormat().replace("[GOD]", "A").replace("[CLASS]", "P"));
            }
            if(getClass(player) == Class.NAVARCHO){
                e.setFormat(e.getFormat().replace("[GOD]", "A").replace("[CLASS]", "N"));
            }
            if(getClass(player) == Class.ANTINAVARCHO){
                e.setFormat(e.getFormat().replace("[GOD]", "A").replace("[CLASS]", "A"));
            }

        }

        if(getTeam(player) == God.ZEUS){

            if(getClass(player) == Class.STRATIGON){
                e.setFormat(e.getFormat().replace("[GOD]", "Z").replace("[CLASS]", "S"));
            }
            if(getClass(player) == Class.PTERARCHO){
                e.setFormat(e.getFormat().replace("[GOD]", "Z").replace("[CLASS]", "P"));
            }
            if(getClass(player) == Class.NAVARCHO){
                e.setFormat(e.getFormat().replace("[GOD]", "Z").replace("[CLASS]", "N"));
            }
            if(getClass(player) == Class.ANTINAVARCHO){
                e.setFormat(e.getFormat().replace("[GOD]", "Z").replace("[CLASS]", "A"));
            }
        }

        if(getTeam(player) == God.POSIEDEN){

            if(getClass(player) == Class.STRATIGON){
                e.setFormat(e.getFormat().replace("[GOD]", "P").replace("[CLASS]", "S"));
            }
            if(getClass(player) == Class.PTERARCHO){
                e.setFormat(e.getFormat().replace("[GOD]", "P").replace("[CLASS]", "P"));
            }
            if(getClass(player) == Class.NAVARCHO){
                e.setFormat(e.getFormat().replace("[GOD]", "P").replace("[CLASS]", "N"));
            }
            if(getClass(player) == Class.ANTINAVARCHO){
                e.setFormat(e.getFormat().replace("[GOD]", "P").replace("[CLASS]", "A"));
            }

        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Item item = e.getItemDrop();
        if(!item.equals(nameItem(new ItemStack(Material.IRON_SWORD), "&b&lStratigon's Sword"))
        || !item.equals(nameItem(new ItemStack(Material.BOW), "&b&lPterarcho's Bow"))
        || !item.equals(nameItem(new ItemStack(Material.IRON_AXE), "&b&lNavarcho's Axe"))
        || !item.equals(nameItem(new ItemStack(Material.IRON_AXE), "&b&lAntinavarcho's Axe"))
        ) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        ItemStack item = e.getCurrentItem();
        if(!item.equals(nameItem(new ItemStack(Material.IRON_SWORD), "&b&lStratigon's Sword"))
        || !item.equals(nameItem(new ItemStack(Material.BOW), "&b&lPterarcho's Bow"))
        || !item.equals(nameItem(new ItemStack(Material.IRON_AXE), "&b&lNavarcho's Axe"))
        || !item.equals(nameItem(new ItemStack(Material.IRON_AXE), "&b&lAntinavarcho's Axe"))
        ) return;

        e.setCancelled(true);
    }

    private ItemStack nameItem(ItemStack item, String name){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return item;
    }
  
    public God getTeam(Player player){
        return God.valueOf(settings.getData().getString("player." + player.getUniqueId() + ".god"));
    }

    public Class getClass(Player player){
        return Class.valueOf(settings.getData().getString("player." + player.getUniqueId() + ".class"));
    }

    public God getTeam(Faction faction){
        return God.valueOf(settings.getData().getString("faction." + faction.getId() + ".god"));
    }

    @EventHandler
    public void onFactionCreate(FactionCreateEvent e){
        settings.getData().set("faction." + e.getFPlayer().getFaction().getId() + ".god", getTeam(e.getFPlayer().getPlayer()));
    }

    @EventHandler
    public void onFJoin(FPlayerJoinEvent e){
        if(getTeam(e.getFaction()).equals(getTeam(e.getfPlayer().getPlayer()))) return;
        e.setCancelled(true);
        e.getfPlayer().sendMessage(ChatColor.RED + "You're not in the correct god team to join this faction!");
    }



}
