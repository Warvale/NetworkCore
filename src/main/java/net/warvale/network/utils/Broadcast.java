package net.warvale.network.utils;

import net.warvale.network.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Broadcast {

    public enum BroadcastType {
        SUCCESS,
        FAILURE,
        BASIC
    }

    public static void toConsole(Level level, String message) {
        Main.get().getLogger().log(level, "[NetworkCore v" + "0.1" + "] " + message);
    }

    public static void toPlayer(Player player, String message) {
        player.sendMessage(message);
    }

    public static void toSender(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    public static void toPlayer(Player player, BroadcastType broadcastType, String message) {
        if(broadcastType == BroadcastType.SUCCESS) {
            player.sendMessage(ChatColor.DARK_GREEN + "[NetworkCore] " + ChatColor.GREEN + message);
        } else if(broadcastType == BroadcastType.FAILURE) {
            player.sendMessage(ChatColor.DARK_RED + "[NetworkCore] " + ChatColor.RED + message);
        } else if(broadcastType == BroadcastType.BASIC) {
            player.sendMessage(ChatColor.GOLD + "[NetworkCore] " + ChatColor.YELLOW + message);
        }
    }

    public static void toSender(CommandSender sender, BroadcastType broadcastType, String message) {
        if(broadcastType == BroadcastType.SUCCESS) {
            sender.sendMessage(ChatColor.DARK_GREEN + "[NetworkCore] " + ChatColor.GREEN + message);
        } else if(broadcastType == BroadcastType.FAILURE) {
            sender.sendMessage(ChatColor.DARK_RED + "[NetworkCore] " + ChatColor.RED + message);
        } else if(broadcastType == BroadcastType.BASIC) {
            sender.sendMessage(ChatColor.GOLD + "[NetworkCore] " + ChatColor.YELLOW + message);
        }
    }
}
