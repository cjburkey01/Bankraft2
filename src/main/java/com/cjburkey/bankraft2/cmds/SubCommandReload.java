package com.cjburkey.bankraft2.cmds;

import org.bukkit.command.CommandSender;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.cmd.SubCommand;
import com.cjburkey.bankraft2.cmd.SubCommandHandler;

public class SubCommandReload extends SubCommand {
	
	public SubCommandReload() {
		super("reload", false, false);
	}
	
	public String getDescription() {
		return "Reloads the bank accounts from the data file";
	}
	
	public int getRequiredArguments() {
		return 0;
	}
	
	public String[] getArguments() {
		return new String[0];
	}
	
	public String getPermission() {
		return "bankraft.admin";
	}
	
	public String onCall(SubCommandHandler commandHandler, CommandSender sender, String prefix, String[] args) {
		Bankraft2.getInstance().reloadConfig();
		Bankraft2.getInstance().load();
		return Util.getLang("reloaded");
	}
	
}