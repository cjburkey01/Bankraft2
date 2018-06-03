package com.cjburkey.bankraft2.bank;

import java.util.UUID;
import org.bukkit.entity.Player;
import com.cjburkey.bankraft2.Bankraft2;

public class Account {
	
	public final UUID player;
	public final String name;
	private double balance = 0.0f;
	public final long creation;
	private long lastWithdraw = -1L;
	private long lastDeposit = -1L;
	
	public Account(UUID player, String name) {
		this.player = player;
		this.name = name;
		creation = Bankraft2.getInstance().getBank().getBankTime();
	}
	
	public void deposit(double amt) {
		setBalance(balance + amt);
		lastDeposit = Bankraft2.getInstance().getBank().getBankTime();
	}
	
	public boolean withdraw(double amt) {
		if (amt <= balance) {
			setBalance(balance - amt);
			lastWithdraw = Bankraft2.getInstance().getBank().getBankTime();
			return true;
		}
		return false;
	}
	
	public void setBalance(double balance) {
		this.balance = Math.max(0.0f, balance);
		Bankraft2.getInstance().getBank().save();
	}
	
	public double getBalance() {
		return balance;
	}
	
	public long getLastWithdrawTime() {
		return lastWithdraw;
	}
	
	public long getLastDepositTime() {
		return lastDeposit;
	}
	
	public long getElapsedSinceWithdraw() {
		return Bankraft2.getInstance().getBank().getBankTime() - lastWithdraw;
	}
	
	public long getElapsedSinceDeposit() {
		return Bankraft2.getInstance().getBank().getBankTime() - lastDeposit;
	}
	
	public long getElapsedSinceCreation() {
		return Bankraft2.getInstance().getBank().getBankTime() - creation;
	}
	
	public Player getPlayer() {
		return Bankraft2.getInstance().getServer().getPlayer(player);
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) ((prime * result + Double.doubleToLongBits(balance)) % 839405);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Account other = (Account) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (player == null) {
			if (other.player != null) {
				return false;
			}
		} else if (!player.equals(other.player)) {
			return false;
		}
		return true;
	}
	
}