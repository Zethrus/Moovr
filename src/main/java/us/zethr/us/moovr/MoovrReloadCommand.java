package us.zethr.us.moovr;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MoovrReloadCommand implements CommandExecutor {

    private final Moovr plugin;

    public MoovrReloadCommand(Moovr plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("moovr") && args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("moovr.reload")) {
                plugin.reloadConfig();
                sender.sendMessage("Moovr configuration reloaded.");
                return true;
            } else {
                sender.sendMessage("You do not have permission to reload the Moovr configuration.");
                return true;
            }
        }
        return false;
    }
}
