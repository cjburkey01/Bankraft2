package com.cjburkey.bankraft2;

import org.bukkit.plugin.java.JavaPlugin;
import com.cjburkey.bankraft2.bank.BankHandler;
import com.cjburkey.bankraft2.cmd.BankCommandHandler;
import com.cjburkey.bankraft2.cmds.SubCommandCreate;
import com.cjburkey.bankraft2.cmds.SubCommandDelete;
import com.cjburkey.bankraft2.cmds.SubCommandDeposit;
import com.cjburkey.bankraft2.cmds.SubCommandGui;
import com.cjburkey.bankraft2.cmds.SubCommandHelp;
import com.cjburkey.bankraft2.cmds.SubCommandInfo;
import com.cjburkey.bankraft2.cmds.SubCommandList;
import com.cjburkey.bankraft2.cmds.SubCommandReload;
import com.cjburkey.bankraft2.cmds.SubCommandWithdraw;
import com.cjburkey.bankraft2.gui.GuiHandler;

public class Bankraft2 extends JavaPlugin {
	
	private static Bankraft2 instance;
	
	public final Econ economy = new Econ();
	private BankHandler bank;
	private GuiHandler guiHandler;
	
	private int timeSinceInterest = 0;
	private int timeSinceGuiUpdate = 0;
	
	public Bankraft2() {
		instance = this;
	}
	
	public void onEnable() {
		if (!economy.setupEconomy(this)) {
			Util.log("Failed to initialize Bankraft2, Vault could not be initialized.");
			Util.log("Please ensure that an economy plugin such as Essentials is installed with Vault.");
			disable();
			return;
		}
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		
		guiHandler = new GuiHandler();
		
		getCommand("bank").setExecutor(new BankCommandHandler());
		
		BankCommandHandler.commandHandler.addSubCommand(new SubCommandCreate());
		BankCommandHandler.commandHandler.addSubCommand(new SubCommandDelete());
		BankCommandHandler.commandHandler.addSubCommand(new SubCommandDeposit());
		BankCommandHandler.commandHandler.addSubCommand(new SubCommandGui());
		BankCommandHandler.commandHandler.addSubCommand(new SubCommandHelp());
		BankCommandHandler.commandHandler.addSubCommand(new SubCommandInfo());
		BankCommandHandler.commandHandler.addSubCommand(new SubCommandList());
		BankCommandHandler.commandHandler.addSubCommand(new SubCommandReload());
		BankCommandHandler.commandHandler.addSubCommand(new SubCommandWithdraw());
		
		load();
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
			timeSinceInterest ++;
			timeSinceGuiUpdate ++;
			if (timeSinceInterest >= getConfig().getInt("interest.interestInterval")) {
				timeSinceInterest = 0;
				bank.handleInterest();
			}
			if (timeSinceGuiUpdate >= getConfig().getInt("gui.updateRate")) {
				timeSinceGuiUpdate = 0;
				guiHandler.refreshGuis();
			}
			bank.perSecond();
		}, 20, 20);
	}
	
	public void load() {
		if (bank == null) {
			Util.log("Loading bank data");
		} else {
			Util.log("Reloading bank data");
		}
		bank = BankHandler.loadBankHandler();
	}
	
	private void disable() {
		getServer().getPluginManager().disablePlugin(this);
	}
	
	public BankHandler getBank() {
		return bank;
	}
	
	public GuiHandler getGuiHandler() {
		return guiHandler;
	}
	
	public static Bankraft2 getInstance() {
		return instance;
	}
	
}