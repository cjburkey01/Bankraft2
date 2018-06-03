package com.cjburkey.bankraft2.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.bank.Account;

public class GuiDeposit implements IGui {
	
	public final Account account;
	
	public GuiDeposit(Account account) {
		this.account = account;
	}
	
	public void onOpen(Inventory inventory, Player player) {
		ItemStack one = GuiHandler.createItem("actionIcon", Util.getLang("guiDeposit") + " &a" + Bankraft2.getInstance().economy.format(1.0d));
		ItemStack ten = GuiHandler.createItem("actionIcon", Util.getLang("guiDeposit") + " &a" + Bankraft2.getInstance().economy.format(10.0d));
		ItemStack hundred = GuiHandler.createItem("actionIcon", Util.getLang("guiDeposit") + " &a" + Bankraft2.getInstance().economy.format(100.0d));
		ItemStack thousand = GuiHandler.createItem("actionIcon", Util.getLang("guiDeposit") + " &a" + Bankraft2.getInstance().economy.format(1000.0d));
		ItemStack tenk = GuiHandler.createItem("actionIcon", Util.getLang("guiDeposit") + " &a" + Bankraft2.getInstance().economy.format(10000.0d));
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
			handleDeposit(player, 1.0d);
			break;
		case 2:
			handleDeposit(player, 10.0d);
			break;
		case 3:
			handleDeposit(player, 100.0d);
			break;
		case 4:
			handleDeposit(player, 1000.0d);
			break;
		case 5:
			handleDeposit(player, 10000.0d);
			break;
		case 7:
			Bankraft2.getInstance().getGuiHandler().openGui(player, new GuiSingleAccount(account));
			break;
		}
	}
	
	private void handleDeposit(Player player, double amt) {
		if (Bankraft2.getInstance().economy.buy(player.getUniqueId(), amt)) {
			account.deposit(amt);
			Util.msgLang(player, "deposit");
			return;
		}
		Util.msgLang(player, "notEnoughInPocket");
	}
	
	public String getName() {
		return Util.getLang("guiDepositTitle");
	}
	
	public int getRows() {
		return 1;
	}
	
}