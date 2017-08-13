package net.warvale.network.commands;

import net.warvale.network.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ValeItemInInvCommand extends AbstractCommand {
    private Main plugin;
    public ValeItemInInvCommand(Main plugin){
        super("valeitemininv", "<true|false>");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof Player)){
            throw new CommandException("Only players can execute this command");
        }
        Player player = (Player) sender;
        if(args.length != 1){
            return false;
        }
        if(!(args[0].equals("true") || args[0].equals("false"))){
            return false;
        }
        plugin.getConfig().set("valeitemininv", args[0]);
        player.sendMessage(ChatColor.GREEN + (args[0].equals("true")?"Vale Emeralds will now appear in the ninth slot of player's hotbars!":"Vale Emeralds will no longer appear in the ninth slot of player's hotbars!"));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        ArrayList<String> a = new ArrayList<>();
        a.add("true");
        a.add("false");
        return a;
    }
}
