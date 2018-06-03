package com.cjburkey.bankraft2.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.bank.Account;

public class GuiWithdraw implements IGui {
	
	public final Account account;
	
	public GuiWithdraw(Account account) {
		this.account = account;
	}
	
	public void onOpen(Inventory inventory, Player player) {
		ItemStack one = GuiHandler.createItem("actionIcon", Util.getLang("guiWithdraw") + " &a" + Bankraft2.getInstance().economy.format(1.0d));
		ItemStack ten = GuiHandler.createItem("actionIcon", Util.getLang("guiWithdraw") + " &a" + Bankraft2.getInstance().economy.format(10.0d));
		ItemStack hundred = GuiHandler.createItem("actionIcon", Util.getLang("guiWithdraw") + " &a" + Bankraft2.getInstance().economy.format(100.0d));
		ItemStack thousand = GuiHandler.createItem("actionIcon", Util.getLang("guiWithdraw") + " &a" + Bankraft2.getInstance().economy.format(1000.0d));
		ItemStack tenk = GuiHandler.createItem("actionIcon", Util.getLang("guiWithdraw") + " &a" + Bankraft2.getInstance().economy.format(10000.0d));
		ItemStack back = GuiHandler.createItem("backIcon", Util.getLang("guiBack"));
		
		inventory.setItem(1, one);
		inventory.setItem(2, ten);
		inventory.setItem(3, hundred);
		inventory.setItem(4, thousand);
		inventory.setItem(5, tenk);
		inventory.setItem(7, back);
	}
	
	public void onClose(Inventory inventory, Player player) {
	}
	
	public void onClick(Inventory inventory, Player player, int slot, ClickType clickType, ItemStack stack) {
		if (!clickType.equals(ClickType.LEFT)) {
			return;
		}
		switch (slot) {
		case 1:
			handleWithdraw(player, 1.0d);
			break;
		case 2:
			handleWithdraw(player, 10.0d);
			break;
		case 3:
			handleWithdraw(player, 100.0d);
			break;
		case 4:
			handleWithdraw(player, 1000.0d);
			break;
		case 5:
			handleWithdraw(player, 10000.0d);
			break;
		case 7:
			Bankraft2.getInstance().getGuiHandler().openGui(player, new GuiSingleAccount(account));
			break;
		}
	}
	
	private void handleWithdraw(Player player, double amt) {
		if (account.withdraw(amt)) {
			Bankraft2.getInstance().economy.addMoney(player.getUniqueId(), amt);
			Util.msgLang(player, "withdraw");
			return;
		}
		Util.msgLang(player, "notEnoughInAccount");
	}
	
	public String getName() {
		return Util.getLang("guiWithdrawTitle");
	}
	
	public int getRows() {
		return 1;
	}
	
}