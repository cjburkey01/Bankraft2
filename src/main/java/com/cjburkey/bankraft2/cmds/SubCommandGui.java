package com.cjburkey.bankraft2.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.cmd.SubCommand;
import com.cjburkey.bankraft2.cmd.SubCommandHandler;
import com.cjburkey.bankraft2.gui.GuiAccountList;

public class SubCommandGui extends SubCommand {
	
	public SubCommandGui() {
		super("gui", false, true);
	}
	
	public String getDescription() {
		return "Opens the bank GUI system";
	}
	
	public int getRequiredArguments() {
		return 0;
	}
	
	public String[] getArguments() {
		return new String[0];
	}
	
	public String onCall(SubCommandHandler commandHandler, CommandSender sender, String prefix, String[] args) {
		if (Bankraft2.getInstance().getBank().getAccounts(((Player) sender).getUniqueId()).length <= 0) {
			return Util.getLang("noAccs");
		}
		Bankraft2.getInstance().getGuiHandler().openGui((Player) sender, new GuiAccountList());
		return Util.getLang("guiOpen");
	}
	
}