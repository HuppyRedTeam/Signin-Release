package com.chenhao220.Signin;

import java.io.File;
import java.util.Calendar;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import be.maximvdw.mctitle.*;
public class LoginListener implements Listener{
	private Signin sr;
	public LoginListener(Signin args){
	   this.sr = args;
	}
   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event){
	   Player p = event.getPlayer();
	  if(sr.getConfig().getBoolean("notice")){
		  try{
		     YamlConfiguration log = sr.getLog();
		     File logf = sr.getFile();
		     log.load(logf);
		     boolean b = checkPlayer(p);
		     if(!b){
		    	 Title title = new Title("§c欢迎§6"+p.getName(),"§a你今天还未签到，输入§c/signin来签到",3,10,3);
		    	 title.send(p);
		     }else{
		    	 return;
		     }
		  }catch(Exception e){
			  
		  }
	  }
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
			sr.getLogger().log(Level.SEVERE,"无法读取文件:"+e.getMessage());
		}
		return false;
	}
}
