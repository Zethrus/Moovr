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
import org.bukkit.Location;

public class Moovr extends JavaPlugin implements Listener {
  private double moovrSpeed; // Store Moovr speed into variable
  private boolean pluginEnabled;
  private boolean moovrSoundEnabled;
  private String moovrSound;

  private Logger logger = Bukkit.getLogger();

  @Override
  public void onEnable() {
    //Register MoovrBlock and load config
    loadConfig();
    logger.info("Moovr has been enabled!");

    // Register the commands
    getCommand("moovr").setExecutor(new MoovrReloadCommand(this));
    getCommand("moovr").setExecutor(new MoovrSetSpeedCommand(this));

    // Register the player move listener
    MoovrPlayerMoveListener playerMoveListener = new MoovrPlayerMoveListener(this);
    getServer().getPluginManager().registerEvents(playerMoveListener, this);
  }

  public void loadConfig() {

    getLogger().info("Loading config...");
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
    moovrSpeed = getConfig().getDouble("walkspeed", 0.5); 
    pluginEnabled = getConfig().getBoolean("enabled", true);
    moovrSoundEnabled = getConfig().getBoolean("sound.enabled", true);
    moovrSound = getConfig().getString("sound.sound", "ENTITY_MINECART_RIDING");

  }

  public double getMoovrSpeed() {
    return moovrSpeed;
  }

  public void setMoovrSpeed(double speed) {
    moovrSpeed = speed;
  }

  public boolean isPluginEnabled() {
    return pluginEnabled;
  }
  public boolean isMoovrSoundEnabled() {
    return moovrSoundEnabled;
  }

  public String getMoovrSound() {
    return moovrSound;
  }


  @Override
  public void onDisable() {
    // TODO Auto-generated method stub
  }


}