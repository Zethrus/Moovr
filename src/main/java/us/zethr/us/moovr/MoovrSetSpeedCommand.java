package us.zethr.us.moovr;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MoovrSetSpeedCommand implements CommandExecutor {

    private final Moovr plugin;
    private final NumberFormat numberFormat;

    public MoovrSetSpeedCommand(Moovr plugin) {
        this.plugin = plugin;

        // Create a NumberFormat with the correct locale
        numberFormat = NumberFormat.getInstance(Locale.US);
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
            Number parsedNumber = numberFormat.parse(args[0]);
            speed = parsedNumber.doubleValue();
        } catch (ParseException | NumberFormatException e) {
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