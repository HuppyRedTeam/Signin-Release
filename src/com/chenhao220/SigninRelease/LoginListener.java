package com.chenhao220.SigninRelease;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import be.maximvdw.mctitle.Title;

public class LoginListener implements Listener{
	private Load sr;
	public LoginListener(Load arg){
		this.sr=arg;
	}
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event){
		Player p = event.getPlayer();
		if(Util.checkPlayer(p)){
			Title title = new Title("§c欢迎§e"+p.getName(),"§a你今天还未签到，输入/signin进行签到",2,5,2);
			title.send(p);
			if(p.isOp()){
				p.sendMessage("§6[§c签到系统§6]§c关注插件最新版本请访问:http://www.hhxcmc.com:8080/job/Signin-Release");
			}
		}else{
			return;
		}
	}
}
