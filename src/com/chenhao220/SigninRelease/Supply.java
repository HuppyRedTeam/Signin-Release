package com.chenhao220.SigninRelease;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class Supply implements Listener{
    private Load sr;
    public Supply(Load arg){
    	this.sr = arg;
    }
    @EventHandler
    public void onSupplyEvent(PlayerInteractEvent event){
		if(Util.checkItem(event.getItem(),Item.supply)){
			Player p = event.getPlayer();
			p.sendMessage("§e§l请输入你要补签的日期(本月第n天签到):");
			Inventory inv = p.getInventory();
			inv.remove(Item.supply);
			return;
		}else{
			Player p = event.getPlayer();
			p.sendMessage(event.getItem().getItemMeta().getDisplayName());
			p.sendMessage(Item.supply.getItemMeta().getDisplayName());
			return;
		}
    }
}
