package com.cjburkey.bankraft2.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.bank.Account;

public class GuiSingleAccount implements IGui {
	
	public final Account account;
	
	public GuiSingleAccount(Account account) {
		this.account = account;
	}
	
	public void onOpen(Inventory inventory, Player player) {
		ItemStack accountBalance = GuiHandler.createItem("balanceIcon", Util.getLang("balance") + Bankraft2.getInstance().economy.format(account.getBalance()));
		ItemStack pocketBalance = GuiHandler.createItem("balanceIcon", Util.getLang("pocketBalance") + Bankraft2.getInstance().economy.format(Bankraft2.getInstance().economy.getMoney(player.getUniqueId())));
		ItemStack deposit = GuiHandler.createItem("actionIcon", Util.getLang("guiDeposit"));
		ItemStack withdraw = GuiHandler.createItem("actionIcon", Util.getLang("guiWithdraw"));
		ItemStack back = GuiHandler.createItem("backIcon", Util.getLang("guiBack"));
		
		inventory.setItem(1, accountBalance);
		inventory.setItem(2, pocketBalance);
		inventory.setItem(4, deposit);
		inventory.setItem(5, withdraw);
		inventory.setItem(7, back);
	}
	
	public void onClose(Inventory inventory, Player player) {
	}
	
	public void onClick(Inventory inventory, Player player, int slot, ClickType clickType, ItemStack stack) {
		if (!clickType.equals(ClickType.LEFT)) {
			return;
		}
		switch (slot) {
		case 4:
			Bankraft2.getInstance().getGuiHandler().openGui(player, new GuiDeposit(account));
			break;
		case 5:
			Bankraft2.getInstance().getGuiHandler().openGui(player, new GuiWithdraw(account));
			break;
		case 7:
			Bankraft2.getInstance().getGuiHandler().openGui(player, new GuiAccountList());
			break;
		}
	}
	
	public String getName() {
		return Util.getLang("guiAccountActionName");
	}
	
	public int getRows() {
		return 1;
	}
	
}