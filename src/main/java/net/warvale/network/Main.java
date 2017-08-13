package net.warvale.network;

import net.warvale.network.commands.CommandHandler;
import net.warvale.network.listeners.PlayerListener;
import net.warvale.network.sql.SQLConnection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {
    private static Main instance;
    private SQLConnection db;
    private CommandHandler cmds;

    @Override
    public void onEnable(){
        instance = this;
        init();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable(){

    }

    private void init(){
        loadConfiguration();
        db = new SQLConnection(getConfig().getString("hostname"), getConfig().getInt("port"), getConfig().getString("database"), getConfig().getString("username"), getConfig().getString("password"));
        try {
            db.openConnection(); } catch(Exception e) {
            getLogger().log(Level.WARNING, "Could not establish connection to database, exception: "+e);
            return;
        }
        cmds = new CommandHandler(this);
        cmds.registerCommands();
    }

    public void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }


    public SQLConnection getDb() {
        return db;
    }

    public static Main get(){
        return instance;
    }
}
