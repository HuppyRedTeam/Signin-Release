package com.chenhao220.SigninRelease;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
@SuppressWarnings("deprecation")
public class Util {
	private static Load sr;
	@SuppressWarnings("static-access")
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
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		int maxday = c.getActualMaximum(Calendar.DATE);
		int month = c.get(Calendar.MONTH)+1;
		Inventory inv = sr.getServer().createInventory(null,45,"§c§l[§6每日签到§c]§c§3今日: §c"+month+"月"+day+"日");
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
		inv.setItem(43,shop);
		List<String> listlore = new ArrayList<String>();
		listlore.add("§a你连续签到了:§c"+Util.seriesSignin(p)+"§a天");
		listlore.add("§a你本月一共签到了:§c"+(log.getIntegerList(p.getName()+".signdate").size())+"§a天");
		ItemStack list = Item.getList(listlore);
		inv.setItem(44,list);
		return inv;
	}
    public static boolean checkItem(ItemStack item,ItemStack otheritem){
    	if(item.getItemMeta().getDisplayName()==null){
    		return false;
    	}
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
	    			String dp = sr.getConfig().getString("shop."+i+".displayname");
	    			String name = ChatColor.translateAlternateColorCodes('&', dp);
	    			me.setDisplayName(name);
	    			List<String> lr = sr.getConfig().getStringList("shop."+i+".lore");
	    			String[] lr2 = lr.toArray(new String[lr.size()]);
	    			List<String> lore = new ArrayList<String>();
	    			for(int c=0;c<lr2.length;c++){
	    				String rb = lr2[c];
	    				String lr_ = ChatColor.translateAlternateColorCodes('&', rb);
	    				lore.add(lr_);
	    			}
	    			me.setLore(lore);
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
    public static void checkMonth(Player p){
    	Calendar c = Calendar.getInstance();
		YamlConfiguration log = sr.getLog();
		File logf = sr.getFile();
		try {
			log.load(logf);
			int signmonth = log.getInt(p.getName()+".signmonth");
			int month = c.get(Calendar.MONTH)+1;
			if(signmonth==month){
				return;
			}else{
				log.set(p.getName()+".signday", null);
				log.set(p.getName()+".signmonth", month);
				log.save(logf);
			}
		} catch (IOException| InvalidConfigurationException e) {
			sr.getLogger().log(Level.SEVERE,"读取文件错误！");
		}
    }
	public static void reward(int slot,Player p){
		YamlConfiguration log = sr.getLog();
		File logf = sr.getFile();
		Economy eco = sr.getEconomy();
		try {
			log.load(logf);
			if(sr.getConfig().get("shop."+(slot+1)+".item")!=null){
				if(log.getInt(p.getName()+".credit")>=sr.getConfig().getInt("shop."+(slot+1)+".credit")){
					List<String> lr = sr.getConfig().getStringList("shop."+(slot+1)+".item");
					int money = sr.getConfig().getInt("shop."+(slot+1)+".money");
					if(lr.size()==0){
						int credit = log.getInt(p.getName()+".credit")-sr.getConfig().getInt("shop."+(slot+1)+".credit");
						log.set(p.getName()+".credit",credit);
						p.sendMessage("§6[§c签到系统§6]§a已扣除"+sr.getConfig().getInt("shop."+(slot+1)+".credit")+"张签到卷");
						eco.depositPlayer(p,money);
						p.sendMessage("§6[§c签到系统§6]§a已获得奖励"+money+"金钱");
						log.save(logf);
					}else{
						String[] m = lr.toArray(new String[lr.size()]);
						for(int i=0;i<m.length;i++){
							String a = m[i];
							String[] b = a.split(" ");
							int itemID = Integer.parseInt(b[0]);
							int itemAmount = Integer.parseInt(b[1]);
							ItemStack item = new ItemStack(itemID,itemAmount);
							p.getInventory().addItem(item);
						}
						int credit = log.getInt(p.getName()+".credit")-sr.getConfig().getInt("shop."+(slot+1)+".credit");
						log.set(p.getName()+".credit",credit);
						log.save(logf);
						p.sendMessage("§6[§c签到系统§6]§a已扣除"+sr.getConfig().getInt("shop."+(slot+1)+".credit")+"张签到卷");
						eco.depositPlayer(p,money);
						p.sendMessage("§6[§c签到系统§6]§a已获得奖励"+money+"金钱");
						p.sendMessage("§6[§c签到系统§6]§a已获得物品奖励");
					}
				}else{
					p.sendMessage("§6[§c签到系统§6]§c你没有足够的签到卷！");
					return;
				}
			}else{
				return;
			}
		} catch (IOException| InvalidConfigurationException e) {
			sr.getLogger().log(Level.SEVERE,"读取文件错误！");
		}
    }
	public static int seriesSignin(Player p){
		File logf = sr.getFile();
		YamlConfiguration log = sr.getLog();
		try {
			log.load(logf);
			int combo = 1;
			List<Integer> a = log.getIntegerList(p.getName()+".signdate");
			Integer[] day = a.toArray(new Integer[a.size()]);
			for(int i=1;i<day.length-1;i++){
				if(day[i+1]-day[i]==1){
					combo++;
				}else{
					combo=1;
				}
			}
			return combo;
		} catch (IOException| InvalidConfigurationException e) {
			e.printStackTrace();
			return 1;
		}
	}
}
