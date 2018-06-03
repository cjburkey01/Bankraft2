package com.cjburkey.bankraft2.bank;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import com.cjburkey.bankraft2.BankIO;
import com.cjburkey.bankraft2.Bankraft2;
import com.cjburkey.bankraft2.Util;

public class BankHandler {
	
	private final Set<Account> accounts = new HashSet<Account>();
	private long time = 0;
	
	private BankHandler() {
	}
	
	public Account[] getAccounts() {
		return accounts.toArray(new Account[0]);
	}
	
	public Account[] getAccounts(UUID player) {
		Set<Account> accounts = new HashSet<Account>();
		for (Account account : this.accounts) {
			if (account.player.equals(player)) {
				accounts.add(account);
			}
		}
		return accounts.toArray(new Account[0]);
	}
	
	public Account getAccount(UUID player, String name) {
		for (Account account : accounts) {
			if (account.player.equals(player) && account.name.equals(name)) {
				return account;
			}
		}
		return null;
	}
	
	public boolean deleteAccount(UUID player, String name) {
		for (Account account : accounts) {
			if (account.player.equals(player) && account.name.equals(name)) {
				accounts.remove(account);
				Bankraft2.getInstance().economy.addMoney(player, account.getBalance());
				save();
				return true;
			}
		}
		return false;
	}
	
	public double getTotalBalance(UUID player) {
		Account[] accounts = getAccounts(player);
		double total = 0.0d;
		for (Account account : accounts) {
			total += account.getBalance();
		}
		return total;
	}
	
	public BankCreateStatus createAccount(UUID player, String name) {
		if (getAccount(player, name) != null) {
			return BankCreateStatus.EXISTS;
		}
		if (getAccounts(player).length >= Bankraft2.getInstance().getConfig().getInt("bank.accountLimit")) {
			return BankCreateStatus.TOO_MANY_ACCOUNTS;
		}
		accounts.add(new Account(player, name));
		save();
		return BankCreateStatus.SUCCESS;
	}
	
	public void perSecond() {
		time += 20;
	}
	
	public long getBankTime() {
		return time;
	}
	
	public void handleInterest() {
		for (Account account : accounts) {
			if (account.getPlayer() == null && Bankraft2.getInstance().getConfig().getBoolean("interest.requireOnline")) {
				continue;
			}
			account.setBalance(account.getBalance() * (1.0d + Bankraft2.getInstance().getConfig().getDouble("interest.interestAmount")));
		}
	}
	
	// Saving and loading handling
	
	public void save() {
		BankIO.writeFile(BankIO.getDataFile(), BankIO.getGson().toJson(this));
	}
	
	public static BankHandler loadBankHandler() {
		String file = BankIO.readFile(BankIO.getDataFile());
		if (file != null) {
			try {
				BankHandler handler = BankIO.getGson().fromJson(file, BankHandler.class);
				if (handler == null) {
					throw new Exception("The bank account file could not be parsed");
				}
				return handler;
			} catch (Exception e) {
				Util.log("Failed to load bank accounts: " + e.getMessage());
				e.printStackTrace();
			}
		}
		Util.log("Creating a new bank data handler");
		BankHandler nbh = new BankHandler();
		nbh.save();
		return nbh;
	}
	
}