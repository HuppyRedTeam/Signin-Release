package com.chenhao220.Signin;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import be.maximvdw.mctitle.Title;

public class ClickListener implements Listener{
	private Signin sr;
	public ClickListener(Signin args){
	   this.sr = args;
	}
   @EventHandler
   public void onInventoryClick(InventoryClickEvent event){
	   
   }
}
