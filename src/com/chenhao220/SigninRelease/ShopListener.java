package com.chenhao220.SigninRelease;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ShopListener implements Listener{
	private Load sr;
	public ShopListener(Load arg){
		this.sr = arg;
	}
	@EventHandler
	public void onShopClick(InventoryClickEvent event){
		Player p = sr.getServer().getPlayer(event.getWhoClicked().getName());
		if(event.getCurrentItem()==null){
			return;
		}
		int slot = event.getRawSlot();
		Util.reward(slot, p);
		event.setCancelled(true);
	}
	@EventHandler
	public void onCloseEvent(InventoryCloseEvent event){
    	InventoryClickEvent.getHandlerList().unregister(sr);
    	InventoryCloseEvent.getHandlerList().unregister(sr);
	}
}
