package com.cjburkey.bankraft2;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Util {
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static void log(Object msg) {
		log.info(prepMsg(msg));
	}
	
	public static void err(Object msg) {
		log.severe(prepMsg(msg));
	}
	
	public static String color(String in) {
		return ChatColor.translateAlternateColorCodes('&', in);
	}
	
	public static void msg(CommandSender to, String msg) {
		to.sendMessage(color(msg));
	}
	
	public static String getLang(String name) {
		name = "language." + name;
		String str = Bankraft2.getInstance().getConfig().getString(name, null);
		return (str == null) ? name : str;
	}
	
	public static void msgLang(CommandSender to, String name) {
		msg(to, getLang(name));
	}
	
	private static String prepMsg(Object msg) {
		String out = (msg == null) ? "null" : msg.toString();
		return String.format("[%s] %s", Bankraft2.getInstance().getDescription().getPrefix(), color(out));
	}
	
}