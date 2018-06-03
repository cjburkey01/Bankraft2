package com.cjburkey.bankraft2.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.bank.Account;
import com.cjburkey.bankraft2.cmd.SubCommand;
import com.cjburkey.bankraft2.cmd.SubCommandHandler;

public class SubCommandList extends SubCommand {
	
	public SubCommandList() {
		super("list", false, true);
	}
	
	public String getDescription() {
		return "Lists all bank accounts";
	}
	
	public int getRequiredArguments() {
		return 0;
	}
	
	public String[] getArguments() {
		return new String[0];
	}
	
	public String onCall(SubCommandHandler commandHandler, CommandSender sender, String prefix, String[] args) {
		StringBuilder builder = new StringBuilder();
		Account[] accs = Bankraft2.getInstance().getBank().getAccounts(((Player) sender).getUniqueId());
		if (accs.length < 1) {
			return Util.getLang("noAccs");
		}
		builder.append(Util.getLang("accountPrefix"));
		for (Account account : accs) {
			builder.append('\n');
			builder.append(' ');
			builder.append(' ');
			builder.append('&');
			builder.append('a');
			builder.append(account.name);
			builder.append(' ');
			builder.append('&');
			builder.append('b');
			builder.append('|');
			builder.append(' ');
			builder.append('&');
			builder.append('e');
			builder.append(Bankraft2.getInstance().economy.format(account.getBalance()));
		}
		return builder.toString();
	}
	
}