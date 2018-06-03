package com.cjburkey.bankraft2.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IGui {
	
	void onOpen(Inventory inventory, Player player);
	void onClose(Inventory inventory, Player player);
	void onClick(Inventory inventory, Player player, int slot, ClickType clickType, ItemStack stack);
	String getName();
	int getRows();
	
}