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
import org.bukkit.util.Vector;

public class Moovr extends JavaPlugin implements Listener {
  private double moovrSpeed; // Store Moovr speed into variable

  private Logger logger = Bukkit.getLogger();

  @Override
  public void onEnable() {
    //Register MoovrBlock and load config
    Bukkit.getPluginManager().registerEvents(this, this);
    loadConfig();
    logger.info("Moovr has been enabled!");

    // Register the reload command
    getCommand("moovr").setExecutor(new MoovrReloadCommand(this));
  }

  public void loadConfig() {

    getLogger().info("Loading config...");
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
    moovrSpeed = getConfig().getDouble("walkspeed", 0.5); 

    for (Player player : Bukkit.getOnlinePlayers()) {
      if (player.hasPermission("moovr.use")) {
        float walkspeed = (float) Math.max(Math.min(moovrSpeed, 1.0), -1.0);
        player.setWalkSpeed(walkspeed);
      }
    }

  }

  @EventHandler
  public void onPlayerMoveEvent(PlayerMoveEvent event) {
    event.getFrom().getBlock().getLocation().equals(event.getTo().getBlock().getLocation());
    Player player = event.getPlayer();
    Block block = player.getLocation().getBlock();
    Block blockAbove = block.getRelative(BlockFace.UP);

    if ((player.hasPermission("moovr.use") || player.getPlayer().isOp()) && blockAbove.getType() == Material.AIR) {
      if (block.getType() == Material.POWERED_RAIL) {
        Block blockUnder = block.getRelative(BlockFace.DOWN);
        if (blockUnder.getType() == Material.GOLD_BLOCK
            && blockUnder.getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_TORCH
            && player.hasPermission("moovr.use")) {
          float walkspeed = (float) Math.max(Math.min(moovrSpeed, 1.0), -1.0);
          player.setWalkSpeed(walkspeed);

          // Get the direction the player is facing
          Vector direction = player.getLocation().getDirection();
          // Apply a forward velocity to the player
          player.setVelocity(direction.multiply(0.5));
        }
      } else {
        float defaultwalkspeed = 0.2F;
        player.setWalkSpeed(defaultwalkspeed);
      }
    }
  }

  @Override
  public void onDisable() {
    // TODO Auto-generated method stub
  }


}