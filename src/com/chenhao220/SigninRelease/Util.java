package com.chenhao220.SigninRelease;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Util {
	private static Load sr;
	public Util(Load arg){
		this.sr=arg;
	}
	public Util(){
		
	}
	public static boolean checkPlayer(Player p){
		YamlConfiguration log = sr.getLog();
		File logf = sr.getFile();
		Calendar c = Calendar.getInstance();
		try{
			log.load(logf);
			List<Integer> a = log.getIntegerList(p.getName()+".signdate");
			Integer[] days = a.toArray(new Integer[a.size()]);
			for(int i =0;i<days.length;i++){
				int day = days[i];
				if(c.get(Calendar.DATE)==day){
					return false;
				}
			}
		}catch(Exception e){
		}
		return true;
	}
	public static Inventory getnormalInv(){
		Inventory inv = sr.getServer().createInventory(null,45,"§l§6[§c签到系统§6]§a个人签到菜单");
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		int maxday = c.getActualMaximum(Calendar.DATE);
		for(int i=0;i<day;i++){
			ItemStack a = new Item().getunsignin(i+1,sr.getConfig().getInt("price"));
			inv.setItem(i,a);
		}
		for(int i=day;i<=maxday;i++){
			ItemStack a = new Item().getnormal(i+1,sr.getConfig().getInt("price"));
			inv.setItem(i, a);
		}
		ItemStack today = new Item().getsigninactive(day,sr.getConfig().getInt("price"));
		inv.setItem(day-1,today);
		ItemStack shop = new Item().getshop();
		inv.setItem(44,shop);
		return inv;
	}
}
