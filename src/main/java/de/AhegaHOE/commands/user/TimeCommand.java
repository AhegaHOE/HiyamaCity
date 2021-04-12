package de.AhegaHOE.commands.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TimeCommand implements CommandExecutor {

	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:dd:MM:yyyy");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				String h = getFormattedDate(String
						.valueOf(Integer.parseInt((format.format(Calendar.getInstance().getTime()).substring(0, 2)))));
				String min = getFormattedDate(String
						.valueOf(Integer.parseInt(format.format(Calendar.getInstance().getTime()).substring(3, 5))));
				String sec = getFormattedDate(String
						.valueOf(Integer.parseInt(format.format(Calendar.getInstance().getTime()).substring(6, 8))));
				String d = getFormattedDate(String
						.valueOf(Integer.parseInt(format.format(Calendar.getInstance().getTime()).substring(9, 11))));
				String m = getFormattedDate(String
						.valueOf(Integer.parseInt(format.format(Calendar.getInstance().getTime()).substring(12, 14))));
				String y = getFormattedDate(String
						.valueOf(Integer.parseInt(format.format(Calendar.getInstance().getTime()).substring(15, 19))));
				String tag = "";
				int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
				switch (day) {
				case Calendar.MONDAY:
					tag = "Montag";
					break;
				case Calendar.TUESDAY:
					tag = "Dienstag";
					break;
				case Calendar.WEDNESDAY:
					tag = "Mittwoch";
					break;
				case Calendar.THURSDAY:
					tag = "Donnerstag";
					break;
				case Calendar.FRIDAY:
					tag = "Freitag";
					break;
				case Calendar.SATURDAY:
					tag = "Samstag";
					break;
				case Calendar.SUNDAY:
					tag = "Sonntag";
					break;
				}
				String monat = "";
				int month = Calendar.getInstance().get(Calendar.MONTH);
				switch (month) {
				case Calendar.JANUARY:
					monat = "Januar";
					break;
				case Calendar.FEBRUARY:
					monat = "Februar";
					break;
				case Calendar.MARCH:
					monat = "März";
					break;
				case Calendar.APRIL:
					monat = "April";
					break;
				case Calendar.MAY:
					monat = "Mai";
					break;
				case Calendar.JUNE:
					monat = "Juni";
					break;
				case Calendar.JULY:
					monat = "Juli";
					break;
				case Calendar.AUGUST:
					monat = "August";
					break;
				case Calendar.SEPTEMBER:
					monat = "September";
					break;
				case Calendar.OCTOBER:
					monat = "Oktober";
					break;
				case Calendar.NOVEMBER:
					monat = "November";
					break;
				case Calendar.DECEMBER:
					monat = "Dezember";
					break;
				}

				// TODO: NACHRICHT FÜR DEN SPIELER IN DER JEWEILIGEN SPRACHE EINFÜGEN

				p.sendMessage(ChatColor.GRAY + "Es ist " + ChatColor.BLUE + tag + ChatColor.GRAY + ", der "
						+ ChatColor.BLUE + d + ". " + monat + " " + y + ChatColor.GRAY + " um " + ChatColor.BLUE + h
						+ ":" + min + ":" + sec + ChatColor.GRAY + " Uhr.");
			}
			return false;
		}
		return false;

	}

	private String getFormattedDate(String string) {
		String newString = (string.length() > 1 ? string : "0" + string);
		return newString;
	}

}
