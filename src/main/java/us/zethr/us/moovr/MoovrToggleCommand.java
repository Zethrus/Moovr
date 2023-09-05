package us.zethr.us.moovr;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MoovrToggleCommand implements CommandExecutor {

    private final Moovr plugin;

    public MoovrToggleCommand(Moovr plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("moovr.toggle")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        boolean enabled = plugin.isMoovrEnabled(player);
        plugin.setMoovrEnabled(player, !enabled);

        if (enabled) {
            player.sendMessage(ChatColor.GREEN + "Moovr has been disabled for you."); } else { player.sendMessage(ChatColor.GREEN + "Moovr has been enabled for you."); }
                return true;
            }
        }
