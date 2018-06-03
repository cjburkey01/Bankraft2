package com.cjburkey.bankraft2.cmd;

import org.bukkit.command.CommandSender;
import com.cjburkey.bankraft2.Util;

public abstract class SubCommand implements Comparable<SubCommand> {
	
	public final String name;
	public final boolean consoleOnly;
	public final boolean playerOnly;
	
	protected SubCommand(String name, boolean consoleOnly, boolean playerOnly) {
		this.name = name;
		this.consoleOnly = consoleOnly;
		this.playerOnly = playerOnly;
	}
	
	public abstract String getDescription();
	public abstract int getRequiredArguments();
	public abstract String[] getArguments();
	public abstract String onCall(SubCommandHandler commandHandler, CommandSender sender, String prefix, String[] args);
	
	public String getPermission() {
		return null;
	}
	
	public final String getUsage(String prefix) {
		StringBuilder out = new StringBuilder();
		out.append(Util.getLang("usagePrefix"));
		out.append('/');
		out.append(prefix);
		out.append(' ');
		out.append(name);
		int i = 0;
		for (String argument : getArguments()) {
			boolean req = i < getRequiredArguments();
			out.append(' ');
			out.append((req) ? '<' : '[');
			out.append(argument);
			out.append((req) ? '>' : ']');
			i ++;
		}
		return out.toString();
	}
	
	public String toString() {
		return "SCMD " + name;
	}
	
	public int compareTo(SubCommand o) {
		return name.compareTo(o.name);
	}
	
}