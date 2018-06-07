package com.cjburkey.bankraft2.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.bankraft2.Util;

public class BankCommandHandler implements CommandExecutor {
	
	public static final SubCommandHandler commandHandler = new SubCommandHandler();
	
	private static SubCommand helpCommand;
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!command.getName().equalsIgnoreCase("bank")) {
			return false;
		}
		if (sender instanceof Player) {
			Player ply = (Player) sender;
			if (!ply.hasPermission("bankraft2.use")) {
				Util.msgLang(sender, "noPermission");
				return true;
			}
		}
		if (args.length == 0) {
			if (helpCommand == null) {
				helpCommand = commandHandler.getSubCommand("help");
				if (helpCommand == null) {
					Util.log("Failed to locate the help command");
					Util.msgLang(sender, "error");
					return true;
				}
			}
			Util.msg(sender, helpCommand.getUsage(command.getName()));
			return true;
		}
		Util.msg(sender, commandHandler.onCall(sender, command.getName(), args));
		return true;
	}
	
}