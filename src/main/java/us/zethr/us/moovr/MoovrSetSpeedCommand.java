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
            player.sendMessage(ChatColor.RED + "Usage: /moovr setspeed <amount>");
            return true;
        }

        double speed;
        try {
            speed = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Invalid speed. Please enter a valid number.");
            return true;
        }

        // Set the new Moovr speed
        plugin.setMoovrSpeed(speed);
        plugin.saveConfig();

        player.sendMessage(ChatColor.GREEN + "Moovr speed set to " + speed);

        return true;
    }
}