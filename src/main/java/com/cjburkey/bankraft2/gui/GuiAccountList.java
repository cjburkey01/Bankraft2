package com.cjburkey.bankraft2.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;
import com.cjburkey.bankraft2.bank.Account;

public class GuiAccountList implements IGui {
	
	private Account[] accounts;
	
	public void onOpen(Inventory inventory, Player player) {
		Account[] accounts = Bankraft2.getInstance().getBank().getAccounts(player.getUniqueId());
		this.accounts = new Account[accounts.length];
		int i = 0;
		for (Account account : accounts) {
			Material mat = Material.valueOf(Bankraft2.getInstance().getConfig().getString("gui.accountIcon"));
			if (mat == null) {
				Util.log("Failed to determine material for gui.accountIcon, using default CHEST");
				mat = Material.CHEST;
			}
			ItemStack stack = new ItemStack(mat, 1);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(Util.color(Util.getLang("account") + account.name));
			List<String> lore = new ArrayList<>();
			lore.addAll(Arrays.asList(
				"",
				Util.color(Util.getLang("balance") + Bankraft2.getInstance().economy.format(account.getBalance())),
				Util.color(Util.getLang("timeSinceLastDeposit") + account.getElapsedSinceDeposit()),
				Util.color(Util.getLang("timeSinceLastWithdrawal") + account.getElapsedSinceWithdraw()),
				Util.color(Util.getLang("timeSinceCreation") + account.getElapsedSinceCreation())
			));
			meta.setLore(lore);
			stack.setItemMeta(meta);
			this.accounts[i] = account;
			inventory.setItem(i, stack);
			i ++;
		}
		
		ItemStack totalBank = GuiHandler.createItem("balanceIcon", Util.getLang("guiTotalBankBalance") + Bankraft2.getInstance().economy.format(Bankraft2.getInstance().getBank().getTotalBalance(player.getUniqueId())));
		ItemStack pocketBank = GuiHandler.createItem("balanceIcon", Util.getLang("pocketBalance") + Bankraft2.getInstance().economy.format(Bankraft2.getInstance().economy.getMoney(player.getUniqueId())));
		ItemStack close = GuiHandler.createItem("backIcon", Util.getLang("guiClose"));

		inventory.setItem(getRows() * 9 - 1, close);
		inventory.setItem(getRows() * 9 - 2, pocketBank);
		inventory.setItem(getRows() * 9 - 3, totalBank);
	}
	
	public void onClose(Inventory inventory, Player player) {
	}
	
	public void onClick(Inventory inventory, Player player, int slot, ClickType clickType, ItemStack stack) {
		if (!clickType.equals(ClickType.LEFT)) {
			return;
		}
		if (slot >= 0 && slot < accounts.length) {
			Bankraft2.getInstance().getGuiHandler().openGui(player, new GuiSingleAccount(accounts[slot]));
			return;
		}
		if (slot == getRows() * 9 - 1) {
			Bankraft2.getInstance().getGuiHandler().closeGui(player);
			player.closeInventory();
		}
	}
	
	public String getName() {
		return Util.getLang("guiAccountListName");
	}
	
	public int getRows() {
		return ((int) Math.ceil(Bankraft2.getInstance().getConfig().getInt("bank.accountLimit") / 9.0f)) + 1;
	}
	
}