package net.warvale.network.vale;

import net.warvale.network.commands.AbstractCommand;
import net.warvale.network.commands.CommandException;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayCommand extends AbstractCommand{
    public PayCommand(){
        super("payvales", "<player> <amount>");
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof Player)){
            throw new CommandException("Only players can use this command!");
        }
        Player player = (Player) sender;
        try {
            int playerBal = ValeUtil.getVale(player);
            if (args.length != 2) {
                return false;
            }
            if (!StringUtils.isNumeric(args[1])) {
                return false;
            }
            int amount = Integer.valueOf(args[1]);
            boolean x = false;
            for(Player p : Bukkit.getOnlinePlayers()){
                if(p.getName().equals(args[0])){
                    x = true;
                    break;
                }
            }
            if(!x){
                player.sendMessage(ChatColor.RED + "Could not find the specified player.");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (amount > playerBal) {
                player.sendMessage(ChatColor.RED + "You do not have " + ChatColor.DARK_RED + String.valueOf(amount) + ChatColor.RED + " vales!");
                return true;
            }
            if(amount==0){
                player.sendMessage(ChatColor.RED + "You can not pay " + ChatColor.DARK_RED + "0" + ChatColor.RED + " vales to someone!");
                return true;
            }
            ValeUtil.payVale(player, target, amount);
            player.sendMessage(ChatColor.GREEN + "You payed " + target.getName() + " " +ChatColor.GOLD + String.valueOf(amount) + ChatColor.GREEN + " vales!");
            target.sendMessage(ChatColor.GREEN + "You received " + ChatColor.GOLD + String.valueOf(amount) + ChatColor.GREEN + " vales from " + player.getName() + "!");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        ArrayList<String> a = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            a.add(p.getName());
        }
        return a;
    }
}
