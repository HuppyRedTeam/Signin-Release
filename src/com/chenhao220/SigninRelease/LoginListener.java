package com.chenhao220.SigninRelease;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener{
	private Load sr;
	public LoginListener(Load arg){
		this.sr=arg;
	}
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event){
		Player p = event.getPlayer();
	}
}
