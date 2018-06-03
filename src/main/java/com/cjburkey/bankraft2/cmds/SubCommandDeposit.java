package com.cjburkey.bankraft2.cmds;

import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.bank.Account;
import com.cjburkey.bankraft2.cmd.SubCommand;
import com.cjburkey.bankraft2.cmd.SubCommandHandler;

public class SubCommandDeposit extends SubCommand {
	
	public SubCommandDeposit() {
		super("deposit", false, true);
	}
	
	public String getDescription() {
		return "Deposits money into a bank account";
	}
	
	public int getRequiredArguments() {
		return 2;
	}
	
	public String[] getArguments() {
		return new String[] { "account name", "amount" };
	}
	
	public String onCall(SubCommandHandler commandHandler, CommandSender sender, String prefix, String[] args) {
		try {
			UUID uuid = ((Player) sender).getUniqueId();
			Account acc = Bankraft2.getInstance().getBank().getAccount(uuid, args[0]);
			if (acc == null) {
				return Util.getLang("accountNotFound");
			}
			double amt = Double.parseDouble(args[1]);
			if (Bankraft2.getInstance().economy.buy(uuid, amt)) {
				acc.deposit(amt);
				return Util.getLang("deposit");
			}
			return Util.getLang("notEnoughInPocket");
		} catch (Exception e) {
		}
		return Util.getLang("invalidNumber");
	}
	
}