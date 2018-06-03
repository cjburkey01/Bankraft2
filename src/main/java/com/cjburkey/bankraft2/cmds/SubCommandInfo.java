package com.cjburkey.bankraft2.cmds;

import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.bank.Account;
import com.cjburkey.bankraft2.cmd.SubCommand;
import com.cjburkey.bankraft2.cmd.SubCommandHandler;

public class SubCommandInfo extends SubCommand {
	
	public SubCommandInfo() {
		super("info", false, true);
	}
	
	public String getDescription() {
		return "Retrieves information for a specific bank account";
	}
	
	public int getRequiredArguments() {
		return 1;
	}
	
	public String[] getArguments() {
		return new String[] { "account name" };
	}
	
	public String onCall(SubCommandHandler commandHandler, CommandSender sender, String prefix, String[] args) {
		UUID uuid = ((Player) sender).getUniqueId();
		Account acc = Bankraft2.getInstance().getBank().getAccount(uuid, args[0]);
		if (acc == null) {
			return Util.getLang("accountNotFound");
		}
		StringBuilder out = new StringBuilder();
		out.append(Util.getLang("account"));
		out.append(acc.name);
		addSpacing(out);
		out.append(Util.getLang("balance"));
		out.append(Bankraft2.getInstance().economy.format(acc.getBalance()));
		addSpacing(out);
		out.append(Util.getLang("timeSinceLastDeposit"));
		out.append(Bankraft2.getInstance().getBank().getBankTime() - acc.getLastDepositTime());
		addSpacing(out);
		out.append(Util.getLang("timeSinceLastWithdrawal"));
		out.append(Bankraft2.getInstance().getBank().getBankTime() - acc.getLastWithdrawTime());
		addSpacing(out);
		out.append(Util.getLang("timeSinceCreation"));
		out.append(Bankraft2.getInstance().getBank().getBankTime() - acc.creation);
		return out.toString();
	}
	
	private static void addSpacing(StringBuilder builder) {
		builder.append('\n');
		builder.append(' ');
		builder.append(' ');
	}
	
}