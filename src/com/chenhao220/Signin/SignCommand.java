package com.chenhao220.Signin;

import java.io.File;
import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.util.Calendar;

public class SignCommand implements CommandExecutor{
	private Signin sr;
	public SignCommand(Signin arg){
		this.sr= arg;
	}
	public boolean onCommand(CommandSender sender,Command cmd,String Label,String[] args) {
		if(cmd.getName().equalsIgnoreCase("signin")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				if(args.length ==1){
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
				}
				if(args.length ==0){
					YamlConfiguration log = sr.getLog();
					File logf = sr.getFile();
					Economy eco = sr.getEconomy();
					try{
						log.load(logf);
						boolean b = checkPlayer(p);
						if(b){
							p.sendMessage("§6[§c签到§6]§c你已经签到");
							return true;
						}else{
							Calendar c = Calendar.getInstance();
							String month = c.get(Calendar.MONTH)+"";
							String day = c.get(Calendar.DATE)+"";
							String time = month+"-"+day;
							int a = log.getInt(p.getName()+".day");
							log.set(p.getName()+".day",a+1);
							log.set(p.getName()+".signdate",time);
							log.save(logf);
							eco.depositPlayer(p,sr.getConfig().getDouble("money"));
							p.sendMessage("§6[§c签到§6]§a你已获得签到"+log.getInt(p.getName()+".day")+"天的奖励");
							p.sendMessage("§6[§c签到§6]§a金钱:"+sr.getConfig().getDouble("money"));
							return true;
						}
					}catch(Exception e){
						p.sendMessage("§c[Signin]你从未签到，现在已创建你的签到档案");
						sr.getLogger().log(Level.SEVERE,"无法读取文件:"+e.getMessage());
					}
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
}
