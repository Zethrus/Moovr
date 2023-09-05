package us.zethr.us.moovr;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Sound;
import org.bukkit.Location;

public class MoovrPlayerMoveListener implements Listener {

    private final Moovr plugin;

    public MoovrPlayerMoveListener(Moovr plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        // Check if the player is moving
        if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
            Block block = player.getLocation().getBlock();
            Block blockAbove = block.getRelative(BlockFace.UP);

            if ((player.hasPermission("moovr.use") || player.isOp()) && blockAbove.getType() == Material.AIR) {
                if (block.getType() == Material.POWERED_RAIL) {
                    Block blockUnder = block.getRelative(BlockFace.DOWN);
                    if (blockUnder.getType() == Material.IRON_BLOCK
                            && blockUnder.getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_TORCH
                            && player.hasPermission("moovr.use")) {
                        float walkspeed = (float) Math.max(Math.min(plugin.getMoovrSpeed(), 1.0), -1.0);
                        player.setWalkSpeed(walkspeed);

                        // Play Minecart rolling sound
                        if (plugin.isMoovrSoundEnabled()) {
                            player.playSound(player.getLocation(), Sound.valueOf(plugin.getMoovrSound()), 0.7f, 0.4f);
                        }
                    }
                } else {
                    float defaultwalkspeed = 0.2F;
                    player.setWalkSpeed(defaultwalkspeed);
                }
            }
        }
    }
}