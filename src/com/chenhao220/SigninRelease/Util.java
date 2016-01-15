package com.chenhao220.SigninRelease;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
	public static Inventory getnormalInv(Player p){
		File logf = sr.getFile();
		YamlConfiguration log = sr.getLog();
		Inventory inv = sr.getServer().createInventory(null,45,"§l§6[§c签到系统§6]§a个人签到菜单");
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		int maxday = c.getActualMaximum(Calendar.DATE);
		try {
			log.load(logf);
			List<Integer> a = log.getIntegerList(p.getName()+".signdate");
			Integer[] days = a.toArray(new Integer[a.size()]);
			for(int i=0;i<day;i++){
				for(int o=0;o<days.length;o++){
					if(i==days[o]){
						ItemStack a1 = Item.getbeensignin(days[o],sr.getConfig().getInt("price"));
						inv.setItem(i-1,a1);
					}else{
						ItemStack a1 = Item.getunsignin(i+1,sr.getConfig().getInt("price"));
						inv.setItem(i,a1);
					}
				}
			}
			if(Util.checkPlayer(p)){
				ItemStack a1 = Item.getsigninactive(day,sr.getConfig().getInt("price"));
				inv.setItem(day-1,a1);
			}else{
				ItemStack a1 = Item.getbeensignin(day,sr.getConfig().getInt("price"));
				inv.setItem(day-1,a1);
			}
		} catch (IOException| InvalidConfigurationException e) {
			e.printStackTrace();
		}
		for(int i=day;i<=maxday-1;i++){
			ItemStack a = Item.getnormal(i+1,sr.getConfig().getInt("price"));
			inv.setItem(i, a);
		}
		ItemStack shop = Item.getshop();
		inv.setItem(44,shop);
		return inv;
	}
    public static boolean checkItem(ItemStack item,ItemStack otheritem){
    	if(item.getItemMeta().getDisplayName().equals(otheritem.getItemMeta().getDisplayName())){
    		return true;
    	}else{
    	return false;
    	}
    }
    public static void signin(Player p){
		File logf = sr.getFile();
		YamlConfiguration log = sr.getLog();
		try {
			log.load(logf);
			Calendar c = Calendar.getInstance();
			List<Integer> day = log.getIntegerList(p.getName()+".signdate");
			int a = c.get(Calendar.DATE);
			int b = c.get(Calendar.MONTH)+1;
			int month = log.getInt(p.getName()+".signmonth");
			if(month!=b){
				log.set(p.getName()+".signmoth",b);
			}
			day.add(a);
			log.set(p.getName()+".signdate", day);
		    int d = log.getInt(p.getName()+".credit");
		    log.set(p.getName()+".credit", d+1);
		    p.sendMessage("§6[§c签到系统§6]§a1签到卷已添加到你的签到账户，你目前有:§c"+(d+1)+"§a个签到卷");
			log.save(logf);
		} catch (IOException| InvalidConfigurationException e) {
			sr.getLogger().log(Level.SEVERE, "错误发生了！"+e.getMessage());
		}
    }
    public static Inventory getshopInv(Player p){
		YamlConfiguration log = sr.getLog();
		File logf = sr.getFile();
		try{
			log.load(logf);
	    	Inventory inv = sr.getServer().createInventory(null,45,"§4[签到卷商店]§0你目前有:§4"+log.getInt(p.getName()+".credit")+"§0签到卷");
	    	for(int i=1;i<=44;i++){
	    		if(sr.getConfig().get("shop."+i+".displayitem")!=null){
	    			ItemStack a = new ItemStack(sr.getConfig().getInt("shop."+i+".displayitem"),1);
	    			ItemMeta me = a.getItemMeta();
	    			String name = sr.getConfig().getString("shop."+i+".displayname");
	    			name.replace(name,'§'+name);
	    			me.setLore(sr.getConfig().getStringList("shop."+i+".lore"));
	    			a.setItemMeta(me);
	    			inv.setItem(i-1,a);
	    		}else{
	    			i = 45;
	    		}
	    	}
	    	return inv;
		}catch(IOException| InvalidConfigurationException e){
			sr.getLogger().log(Level.SEVERE, "错误发生了！"+e.getMessage());
		}
		return null;
    }
}
