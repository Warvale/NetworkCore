package net.warvale.network.listeners;

import net.warvale.network.Main;
import net.warvale.network.vale.ValeUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerListener implements Listener {
    private Main plugin;
    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.setPlayerListName(player.getDisplayName());
        try {
            PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT * FROM vale_eco WHERE uuid = '"+player.getUniqueId().toString()+"' LIMIT 1");
            ResultSet set = stmt.executeQuery();
            if (!set.next()) {
                stmt.close();
                stmt = plugin.getDb().getConnection().prepareStatement("INSERT INTO vale_eco (uuid, name, amount) VALUES ('"+player.getUniqueId().toString()+"','"+player.getName()+"', 0)");
                stmt.execute();
                stmt.close();
            }
            set.close();
            ValeUtil.setVale(player, ValeUtil.getVale(player));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getSlot() == 8){
            event.setCurrentItem(null);
            try {
                if(plugin.getConfig().getString("valeitemininv").equals("true")) {
                    ValeUtil.setVale((Player) event.getWhoClicked(), ValeUtil.getVale((Player) event.getWhoClicked()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        if(event.getItemDrop().getItemStack().getType().equals(Material.EMERALD)){
            event.setCancelled(true);
        }
    }
}
