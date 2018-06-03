package com.cjburkey.bankraft2.cmds;

import org.bukkit.command.CommandSender;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.cmd.SubCommand;
import com.cjburkey.bankraft2.cmd.SubCommandHandler;

public class SubCommandHelp extends SubCommand {
	
	public SubCommandHelp() {
		super("help", false, false);
	}
	
	public String getDescription() {
		return "Displays information about commands";
	}
	
	public int getRequiredArguments() {
		return 0;
	}
	
	public String[] getArguments() {
		return new String[] { "sub-command" };
	}
	
	public String onCall(SubCommandHandler commandHandler, CommandSender sender, String prefix, String[] args) {
		if (args.length == 0) {
			StringBuilder builder = new StringBuilder();
			/*builder.append(Util.getLang("usagePrefix"));
			builder.append('/');
			builder.append(prefix);
			builder.append(' ');
			builder.append('<');
			for (SubCommand command : commandHandler.getSubCommands()) {
				if ((command.getPermission() != null && !sender.hasPermission(command.getPermission())) || (sender instanceof Player && command.consoleOnly) || (!(sender instanceof Player) && command.playerOnly)) {
					continue;	// Only show what the user can execute
				}
				builder.append(command.name);
				builder.append('/');
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append('>');*/
			for (SubCommand command : commandHandler.getSubCommands()) {
				builder.append(command.getUsage(prefix));
				builder.append('\n');
			}
			builder.deleteCharAt(builder.length() - 1);
			return builder.toString();
		}
		SubCommand cmd = commandHandler.getSubCommand(args[0]);
		if (cmd == null) {
			return Util.getLang("cmdNotFound");
		}
		return cmd.getUsage(prefix) + "\n  &6" + cmd.getDescription();
	}
	
}