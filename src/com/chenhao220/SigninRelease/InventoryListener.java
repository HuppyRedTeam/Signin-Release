package com.chenhao220.SigninRelease;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener{
    private Load sr;	
    public InventoryListener(Load arg){
	   this.sr=arg;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
    	Player p = sr.getServer().getPlayer(event.getWhoClicked().getName());
    	if(event.getCurrentItem()==null){
    		return;
    	}
    	if(Util.checkItem(event.getCurrentItem(), Item.unsignin)){
    		p.sendMessage("§6[§c签到系统§6]§c你已错过此次签到！");
    		p.closeInventory();
    	}
    	if(Util.checkItem(event.getCurrentItem(), Item.normal)){
    		p.sendMessage("§6[§c签到系统§6]§c此次签到还未可用！");
    		p.closeInventory();
    	}
    	if(Util.checkItem(event.getCurrentItem(), Item.beensignin)){
    		p.sendMessage("§6[§c签到系统§6]§a你已经完成过这个签到了！");
    		p.closeInventory();
    	}
    	if(Util.checkItem(event.getCurrentItem(), Item.signinactive)){
    		//签到代码
    		Util.signin(p);
    		p.sendMessage("§6[§c签到系统§6]§a签到成功！");
    		p.closeInventory();
    	}
    	if(Util.checkItem(event.getCurrentItem(), Item.shop)){
    		p.openInventory(Util.getshopInv(p));
    	}
    	event.setCancelled(true);
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
    	InventoryClickEvent.getHandlerList().unregister(sr);
    	InventoryCloseEvent.getHandlerList().unregister(sr);
    }
}
