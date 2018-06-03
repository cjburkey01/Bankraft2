package com.cjburkey.bankraft2.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.bank.BankCreateStatus;
import com.cjburkey.bankraft2.cmd.SubCommand;
import com.cjburkey.bankraft2.cmd.SubCommandHandler;

public class SubCommandCreate extends SubCommand {
	
	public SubCommandCreate() {
		super("create", false, true);
	}
	
	public String getDescription() {
		return "Creates a bank account";
	}
	
	public int getRequiredArguments() {
		return 1;
	}
	
	public String[] getArguments() {
		return new String[] { "account name" };
	}
	
	public String onCall(SubCommandHandler commandHandler, CommandSender sender, String prefix, String[] args) {
		BankCreateStatus status = Bankraft2.getInstance().getBank().createAccount(((Player) sender).getUniqueId(), args[0]);
		switch (status) {
		case EXISTS:
			return Util.getLang("exists");
		case TOO_MANY_ACCOUNTS:
			return Util.getLang("tooManyAccounts");
		case SUCCESS:
			return Util.getLang("created");
		}
		return null;
	}
	
}