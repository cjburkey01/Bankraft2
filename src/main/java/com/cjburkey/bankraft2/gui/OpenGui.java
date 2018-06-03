package com.cjburkey.bankraft2.gui;

import org.bukkit.inventory.Inventory;

public class OpenGui {
	
	public final IGui gui;
	public final Inventory inventory;
	
	public OpenGui(IGui gui, Inventory inventory) {
		this.gui = gui;
		this.inventory = inventory;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gui == null) ? 0 : gui.hashCode());
		result = prime * result + ((inventory == null) ? 0 : inventory.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenGui other = (OpenGui) obj;
		if (gui == null) {
			if (other.gui != null)
				return false;
		} else if (!gui.equals(other.gui))
			return false;
		if (inventory == null) {
			if (other.inventory != null)
				return false;
		} else if (!inventory.equals(other.inventory))
			return false;
		return true;
	}
	
}