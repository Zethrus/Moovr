package us.zethr.us.moovr;

import org.bukkit.ChatColor;
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
        if (!sender.hasPermission("moovr.reload")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        plugin.reloadConfig();
        plugin.loadConfig();
        sender.sendMessage(ChatColor.GREEN + "Moovr configuration reloaded.");

        return true;
    }
}