package net.warvale.network.vale;

import net.warvale.network.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ValeUtil {

    private static Main plugin = Main.get();

    public static void setVale(Player player, int amount) throws SQLException {
        UUID uuid = player.getUniqueId();
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("UPDATE vale_eco SET amount = "+amount+" WHERE uuid = '"+uuid.toString()+"'");
        stmt.executeUpdate();

        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN.toString() + getVale(player) + (getVale(player)==1?" Vale":" Vales"));
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        if(plugin.getConfig().getString("valeitemininv").equals("true")){
            player.getInventory().setItem(8, item);
        }

    }

    public static int getVale(Player player) throws SQLException {
        UUID uuid = player.getUniqueId();
        PreparedStatement stmt = plugin.getDb().getConnection().prepareStatement("SELECT amount FROM vale_eco WHERE uuid = '"+uuid.toString()+"'");
        ResultSet set = stmt.executeQuery();
        set.next();
        return set.getInt("amount");
    }

    public static void removeVale(Player player, int amount) throws SQLException {
        int current = getVale(player);
        setVale(player, current-amount);
    }

    public static void addVale(Player player, int amount) throws SQLException {
        int current = getVale(player);
        setVale(player, current+amount);
    }

    public static void payVale(Player from, Player to, int amount) throws SQLException {
        int fromBal = getVale(from);
        int toBal = getVale(to);
        setVale(from, fromBal - amount);
        setVale(to, toBal + amount);
    }
}
