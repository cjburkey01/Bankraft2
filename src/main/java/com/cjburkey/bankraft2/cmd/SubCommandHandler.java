package com.cjburkey.bankraft2.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.bankraft2.Util;

public class SubCommandHandler {
	
	private final List<SubCommand> commands = new ArrayList<>();
	
	public void addSubCommand(SubCommand scmd) {
		commands.add(scmd);
		commands.sort((o1, o2) -> o1.compareTo(o2));
	}
	
	public SubCommand[] getSubCommands() {
		return commands.toArray(new SubCommand[0]);
	}
	
	public SubCommand getSubCommand(String name) {
		for (SubCommand scmd : commands) {
			if (scmd.name.equalsIgnoreCase(name)) {
				return scmd;
			}
		}
		return null;
	}
	
	public String onCall(CommandSender sender, String prefix, String[] args) {
		if (args.length < 1) {
			return Util.getLang("cmdNotFound");
		}
		SubCommand command = getSubCommand(args[0]);
		args = Arrays.copyOfRange(args, 1, args.length);
		if (command == null) {
			return Util.getLang("cmdNotFound");
		}
		if (command.playerOnly && command.consoleOnly) {
			Util.log("Command is both console and player limited: " + command.name);
			return Util.getLang("error");
		}
		if (command.consoleOnly && (sender instanceof Player)) {
			return Util.getLang("consoleOnly");
		}
		if (command.playerOnly && !(sender instanceof Player)) {
			return Util.getLang("ingameOnly");
		}
		if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
			return Util.getLang("noPermission");
		}
		if (args.length < command.getRequiredArguments() || args.length > command.getArguments().length) {
			return command.getUsage(prefix);
		}
		return command.onCall(this, sender, prefix, args);
	}
	
}