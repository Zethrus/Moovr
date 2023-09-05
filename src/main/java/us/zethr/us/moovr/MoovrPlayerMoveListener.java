package us.zethr.us.moovr;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoovrPlayerMoveListener implements Listener {

    private final Moovr plugin;

    public MoovrPlayerMoveListener(Moovr plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock();
        Block blockAbove = block.getRelative(BlockFace.UP);

        if ((player.hasPermission("moovr.use") || player.isOp()) && blockAbove.getType() == Material.AIR) {
            if (block.getType() == Material.POWERED_RAIL) {
                Block blockUnder = block.getRelative(BlockFace.DOWN);
                if (blockUnder.getType() == Material.GOLD_BLOCK && blockUnder.getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_TORCH) {
                    float walkSpeed = (float) Math.max(Math.min(plugin.getMoovrSpeed(), 1.0), -1.0);
                    player.setWalkSpeed(walkSpeed);
                }
            } else {
                float defaultWalkSpeed = 0.2F;
                player.setWalkSpeed(defaultWalkSpeed);
            }
        }
    }
}