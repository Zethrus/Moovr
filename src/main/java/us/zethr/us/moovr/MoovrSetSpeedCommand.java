package us.zethr.us.moovr;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoovrSetSpeedCommand implements CommandExecutor {

    private final Moovr plugin;

    public MoovrSetSpeedCommand(Moovr plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("moovr.setspeed")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /moovrsetspeed <amount>");
            return true;
        }

        double speed;
        try {
            speed = Double.parseDouble(args[0]);
            if (speed > 1.0 || speed < -1.0) {
                throw new IllegalArgumentException();
            }
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Invalid speed. Please enter a number between -1.0 and 1.0.");
            return true;
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Invalid speed range. Please enter a number between -1.0 and 1.0.");
            return true;
        }

        // Set the new Moovr speed
        plugin.setMoovrSpeed(speed);
        plugin.saveConfig();

        player.sendMessage(ChatColor.GREEN + "Moovr speed set to " + speed);

        return true;
    }

}  