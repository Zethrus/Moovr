import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

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

                    Location playerLocation = player.getLocation();
                    Vector direction = playerLocation.getDirection().normalize();
                    playerLocation.add(direction.multiply(0.5));
                    player.teleport(playerLocation);
                }
            } else {
                float defaultWalkSpeed = 0.2F;
                player.setWalkSpeed(defaultWalkSpeed);
            }
        }
    }
}