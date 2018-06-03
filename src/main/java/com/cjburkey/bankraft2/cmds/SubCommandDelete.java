package com.cjburkey.bankraft2.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.cmd.SubCommand;
import com.cjburkey.bankraft2.cmd.SubCommandHandler;

public class SubCommandDelete extends SubCommand {
	
	public SubCommandDelete() {
		super("delete", false, true);
	}
	
	public String getDescription() {
		return "Deletes a bank account";
	}
	
	public int getRequiredArguments() {
		return 1;
	}
	
	public String[] getArguments() {
		return new String[] { "account name" };
	}
	
	public String onCall(SubCommandHandler commandHandler, CommandSender sender, String prefix, String[] args) {
		if (Bankraft2.getInstance().getBank().getAccount(((Player) sender).getUniqueId(), args[0]) == null) {
			return Util.getLang("accountNotFound");
		}
		if (!Bankraft2.getInstance().getBank().deleteAccount(((Player) sender).getUniqueId(), args[0])) {
			return Util.getLang("error");
		}
		return Util.getLang("accountDeleted");
	}
	
}