package us.zethr.us.moovr;

import java.io.IOException;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
//import org.mcstats.MetricsLite;

public class Moovr extends JavaPlugin implements Listener {
  public Player sender;
  
  public void onEnable() {
    // Create an instance of MoovrBlock
    MoovrBlock moovrBlock = new MoovrBlock(this);

    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(moovrBlock, this);
    saveDefaultConfig();
    //try {
      //MetricsLite metrics = new MetricsLite((Plugin)this);
      //metrics.start();
    //} catch (IOException e) {
      //System.out.println("PluginMetrics: He's dead, Jim!");
    //} 
  }
  
  public void onDisable() {
    
  }
}
