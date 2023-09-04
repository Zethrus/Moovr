package us.zethr.us.moovr;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Moovr extends JavaPlugin implements Listener {
  private double moovrSpeed; // Store Moovr speed into variable

  private Logger logger = Bukkit.getLogger();

  @Override
  public void onEnable() {
    //Register MoovrBlock and load config
    Bukkit.getPluginManager().registerEvents(this, this);
    loadConfig();
    logger.info("Moovr has been enabled!");
  }

  public void loadConfig() {
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
    moovrSpeed = getConfig().getDouble("boost-height", 2.0);
  }

@EventHandler
  public void onPlayerMoveEvent(PlayerMoveEvent event) {
    event.getFrom().getBlock().getLocation().equals(event.getTo().getBlock().getLocation());
    Player player = event.getPlayer();
    Block block = player.getLocation().getBlock();
    Block blockAbove = block.getRelative(BlockFace.UP);
    if ((player.hasPermission("moovr.use") || player.getPlayer().isOp()) && 
      blockAbove.getType() == Material.AIR)
      if (block.getType() == Material.POWERED_RAIL) {
        Block blockUnder = block.getRelative(BlockFace.DOWN);
        if (blockUnder.getType() == Material.GOLD_BLOCK && 
          player.hasPermission("moovr.use")) {
          float walkspeed = (float)moovrSpeed;
          player.setWalkSpeed(walkspeed);
        } 
      } else {
        float defaultwalkspeed = 0.2F;
        player.setWalkSpeed(defaultwalkspeed);
      }  
  }

  @Override
  public void onDisable() {
    // TODO Auto-generated method stub
  }


}


// OLD CODE
/*public class Moovr extends JavaPlugin implements Listener {
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
}*/