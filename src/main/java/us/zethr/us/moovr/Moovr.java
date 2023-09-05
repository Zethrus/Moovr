package us.zethr.us.moovr;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Moovr extends JavaPlugin implements Listener {
    private double moovrSpeed; // Store Moovr speed into variable
    private boolean pluginEnabled;
    private boolean moovrSoundEnabled;
    private String moovrSound;
    private Map<UUID, Boolean> moovrEnabledMap;

    private Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();
        
        //Plugin ID for bStats
        int pluginId = 19734;
        //Set up metrics
        Metrics metrics = new Metrics(this, pluginId);

        // Register and load config
        loadConfig();
        logger.info("Moovr has been enabled!");

        // Register the commands
        getCommand("moovrreload").setExecutor(new MoovrReloadCommand(this));
        getCommand("moovrsetspeed").setExecutor(new MoovrSetSpeedCommand(this));
        getCommand("moovrtoggle").setExecutor(new MoovrToggleCommand(this));

        // Register the player move listener
        getServer().getPluginManager().registerEvents(new MoovrPlayerMoveListener(this), this);

        moovrEnabledMap = new HashMap<>();
    }

    public void loadConfig() {
        logger.info("Loading config...");
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
        getConfig().set("walkspeed", speed);
        saveConfig();
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

    public boolean isMoovrEnabled(Player player) {
        return moovrEnabledMap.getOrDefault(player.getUniqueId(), true);
    }

    public void setMoovrEnabled(Player player, boolean enabled) {
        moovrEnabledMap.put(player.getUniqueId(), enabled);
    }

    public void disableMoovrForPlayer(Player player) {
        player.setWalkSpeed(0.2f); // Set the walk speed back to default or any desired value
    }

    @Override
    public void onDisable() {
        // TODO Auto-generated method stub
    }
}