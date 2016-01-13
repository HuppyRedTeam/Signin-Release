package com.chenhao220.Signin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_8_R2.Item;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SignCommand implements CommandExecutor{
	private Signin sr;
	private Inventory inv;
	public SignCommand(Signin arg){
		this.sr= arg;
	}
	public SignCommand() {
		
	}
	public boolean onCommand(CommandSender sender,Command cmd,String Label,String[] args) {
		if(cmd.getName().equalsIgnoreCase("signin")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				if(args.length ==1){
					YamlConfiguration log = sr.getLog();
					File logf = sr.getFile();
					Economy eco = sr.getEconomy();
					if(args[0].equalsIgnoreCase("in")){
					try{
						log.load(logf);
						boolean b = checkPlayer(p);
						if(b){
							p.sendMessage("§6[§c签到§6]§c你已经签到");
							return true;
						}else{
							List<Integer> a = sr.getConfig().getIntegerList("day");
							Integer[] days = a.toArray(new Integer[a.size()]);
							for(int i=0;i<days.length;i++){
								if((log.getInt(p.getName()+".day"))==days[i]){
									Calendar c = Calendar.getInstance();
									String month = c.get(c.MONTH)+"";
									String day = c.get(c.DATE)+"";
									String time = month+"-"+day;
									Double money = sr.getConfig().getDouble(days[i]+".money");
									String str = sr.getConfig().getString(+days[i]+".item");
									String[] item = str.split(" ");
									int ItemID = Integer.parseInt(item[0]);
									int ItemAmount = Integer.parseInt(item[1]);
									ItemStack item2 = new ItemStack(ItemID,ItemAmount);
									p.getInventory().addItem(item2);
									eco.depositPlayer(p,money);
									int t = log.getInt(p.getName()+".day");
									log.set(p.getName()+".day",t+1);
									log.set(p.getName()+".signdate",time);
									log.save(logf);
									p.sendMessage("§6[§c签到§6]§a你已获得签到"+(log.getInt(p.getName()+".day")-1)+"天的奖励：");
									p.sendMessage("§6[§c签到§6]§a金钱:§e"+money+"§a物品§e"+ItemID+"x"+ItemAmount);
									return true;
								}
							}
							Calendar c = Calendar.getInstance();
							String month = c.get(c.MONTH)+"";
							String day = c.get(c.DATE)+"";
							String time = month+"-"+day;
							Double money = sr.getConfig().getDouble("default.money");
							String str = sr.getConfig().getString("default.item");
							String[] item = str.split(" ");
							int ItemID = Integer.parseInt(item[0]);
							int ItemAmount = Integer.parseInt(item[1]);
							ItemStack item2 = new ItemStack(ItemID,ItemAmount);
							p.getInventory().addItem(item2);
							eco.depositPlayer(p,money);
							int t = log.getInt(p.getName()+".day");
							log.set(p.getName()+".day",t+1);
							log.set(p.getName()+".signdate",time);
							log.save(logf);
							p.sendMessage("§6[§c签到§6]§a你已获得签到"+(log.getInt(p.getName()+".day")-1)+"天的奖励：");
							p.sendMessage("§6[§c签到§6]§a金钱:§e"+money+"§a物品§e"+ItemID+"x"+ItemAmount);
							return true;
						}
					}catch(Exception e){
						p.sendMessage("§c[Signin]你从未签到，现在已创建你的签到档案");
						sr.getLogger().log(Level.SEVERE,"无法读取文件:"+e.getMessage());
						sr.getLogger().log(Level.SEVERE,e.getCause()+"");
						return true;
					}
					}
				if(args[0].equalsIgnoreCase("reload")){
				if(p.hasPermission("signin.reload")){
					sr.reloadConfig();
					p.sendMessage("§6[§c签到§6]§a配置文件已重载");
					return true;
				}else{
					p.sendMessage("§6[§c签到§6]§c你没有此命令的权限");
					return true;
				}
				}
				if(args[0].equalsIgnoreCase("text")){
					p.sendMessage("这是一个测试指令");
					p.sendMessage("测试完成");
					return true;
				}
				}
				if(args.length ==0){
					YamlConfiguration log = sr.getLog();
					File logf = sr.getFile();
					Economy eco = sr.getEconomy();
					Inventory inv = sr.getServer().createInventory(null, 27,"§6§l[§c签到系统§6]§e个人菜单");
					for(int i =0;i<=8;i++){
						byte data = 3;
						ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1,data);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName("=");
						item.setItemMeta(meta);
						inv.setItem(i,item);
					}
					for(int i =18;i<=26;i++){
						byte data = 3;
						ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1,data);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName("=");
						item.setItemMeta(meta);
						inv.setItem(i,item);
					}
					ItemStack signin = new ItemStack(Material.BOOK_AND_QUILL,1);
					ItemMeta meta = signin.getItemMeta();
					meta.setDisplayName("§c点我立即签到~");
					List<String> lore1 = new ArrayList<String>();
					lore1.add("§a你已签到:§b"+log.getInt(p.getName()+".day")+"§a天");
					if(checkPlayer(p)){
						lore1.add("§c签到不可用！");
					}else{
						lore1.add("§a签到可用");
					}
					meta.setLore(lore1);
					signin.setItemMeta(meta);
					inv.setItem(13, signin);
					sr.getServer().getPluginManager().registerEvents(new ClickListener(sr),sr);
					p.openInventory(inv);
				}
			}else{
				sender.sendMessage("§6[§c签到§6]§c此插件无法在后台使用！");
				return true;
			}
		}
		return true;
	}
	public boolean checkPlayer(Player p){
		YamlConfiguration log = sr.getLog();
		File logf = sr.getFile();
		Calendar c = Calendar.getInstance();
		String month = c.get(c.MONTH)+"";
		String day = c.get(c.DATE)+"";
		String time = month+"-"+day;
		try{
			log.load(logf);
			String signtime = log.getString(p.getName()+".signdate");
			if(signtime.equals(time)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			p.sendMessage("§c[Signin]你从未签到，现在已创建你的签到档案");
			sr.getLogger().log(Level.SEVERE,"无法读取文件:"+e.getMessage());
		}
		return false;
	}
	public Inventory getInv(){
		return this.inv;
	}
}
