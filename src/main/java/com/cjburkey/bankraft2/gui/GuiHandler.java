package com.cjburkey.bankraft2.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;

public class GuiHandler {
	
	private Map<UUID, OpenGui> openGuis = new HashMap<>();
	
	public GuiHandler() {
		Bankraft2.getInstance().getServer().getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onGuiClick(InventoryClickEvent e) {
				UUID uuid = e.getWhoClicked().getUniqueId();
				if (openGuis.containsKey(uuid)) {
					if (e.getInventory() != null && e.getInventory().equals(openGuis.get(uuid).inventory)) {
						IGui gui = openGuis.get(uuid).gui;
						gui.onClick(openGuis.get(uuid).inventory, (Player) e.getWhoClicked(), e.getSlot(), e.getClick(), e.getCurrentItem());
					}
					e.setCancelled(true);
				}
			}
			@EventHandler
			public void onGuiClose(InventoryCloseEvent e) {
				if (openGuis.containsKey(e.getPlayer().getUniqueId())) {
					closeGui((Player) e.getPlayer());
				}
			}
		}, Bankraft2.getInstance());
	}
	
	public void refreshGuis() {
		for (Entry<UUID, OpenGui> gui : openGuis.entrySet()) {
			openGui(Bankraft2.getInstance().getServer().getPlayer(gui.getKey()), gui.getValue().gui);
		}
	}
	
	public void openGui(Player player, IGui gui) {
		if (openGuis.containsKey(player.getUniqueId())) {
			closeGui(player);
		}
		openGuis.put(player.getUniqueId(), new OpenGui(gui, createAndShowGui(player, gui)));
		gui.onOpen(openGuis.get(player.getUniqueId()).inventory, player);
	}
	
	public void closeGui(Player player) {
		if (openGuis.containsKey(player.getUniqueId())) {
			openGuis.get(player.getUniqueId()).gui.onClose(openGuis.get(player.getUniqueId()).inventory, player);
			openGuis.remove(player.getUniqueId());
		}
	}
	
	private Inventory createAndShowGui(Player player, IGui gui) {
		Inventory inventory = Bukkit.createInventory(player, gui.getRows() * 9, Util.color(gui.getName()));
		player.openInventory(inventory);
		return inventory;
	}
	
	public static ItemStack createItem(String materialLanguage, String nameLang) {
		Material material = Material.valueOf(Bankraft2.getInstance().getConfig().getString("gui." + materialLanguage));
		if (material == null) {
			return null;
		}
		ItemStack stack = new ItemStack(material, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Util.color(nameLang));
		stack.setItemMeta(meta);
		return stack;
	}
	
}