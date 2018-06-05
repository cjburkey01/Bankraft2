package com.cjburkey.bankraft2;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Util {
	
	private static final Logger loggerDoer = Logger.getLogger("Minecraft");
	
	public static void log(Object msg) {
		loggerDoer.info(prepLog(msg));
	}
	
	public static void err(Object msg) {
		loggerDoer.severe(prepLog(msg));
	}
	
	public static String color(String in) {
		return ChatColor.translateAlternateColorCodes('&', in);
	}
	
	public static void msg(CommandSender to, String msg) {
		to.sendMessage(color(msg));
	}
	
	public static String getLang(String name, Object... data) {
		name = "language." + name;
		String str = Bankraft2.getInstance().getConfig().getString(name, null);
		if (str == null) {
			return name;
		}
		for (Object dat : data) {
			if (!str.contains("{}")) {
				break;
			}
			String d = (dat == null ) ? "null" : dat.toString();
			str = str.replaceFirst(Pattern.quote("{}"), Matcher.quoteReplacement((d == null) ? "null" : d));
		}
		return str;
	}
	
	public static void msgLang(CommandSender to, String name, Object... data) {
		msg(to, getLang(name, data));
	}
	
	private static String prepLog(Object msg) {
		String out = (msg == null) ? "null" : msg.toString();
		return String.format("[%s] %s", Bankraft2.getInstance().getDescription().getPrefix(), color(out));
	}
	
}