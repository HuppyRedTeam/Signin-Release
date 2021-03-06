package com.chenhao220.SigninRelease;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BasicCommand implements CommandExecutor{
	private Load sr;
    public BasicCommand(Load arg){
    	this.sr=arg;
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label,String[] args) {
		if(cmd.getName().equalsIgnoreCase("signin")){
			if(args.length==0){
				if(sender instanceof Player){
					sr.getServer().getPluginManager().registerEvents(new InventoryListener(sr), sr);
					Player p = (Player)sender;
					Util.checkMonth(p);
					p.openInventory(Util.getnormalInv(p));
					return true;
				}else{
					sender.sendMessage("§6[§c签到系统§6]§c你无法在后台使用此插件");
					return true;
				}
			}
			if(args.length==1){
				if(args[0].equalsIgnoreCase("reload")){
					sr.reloadConfig();
					sender.sendMessage("§6[§c签到系统§6]§c插件已重载！");
					return true;
				}
				sender.sendMessage("§6[§c签到系统§6]§c不存在的命令！");
			}
			if(args.length==3){
				if(args[0].equalsIgnoreCase("givesupply")){
					Player p = sr.getServer().getPlayer(args[1]);
					int supply = Integer.parseInt(args[2]);
					Util.giveSupplyCredit(p,supply);
					sender.sendMessage("§6[§c签到系统§6]§a你已成功向玩家"+p.getName()+"账户添加"+supply+"张补签卷");
					p.sendMessage("§6[§c签到§6]§a你已收到"+supply+"张补签卷");
					return true;
				}
				sender.sendMessage("§6[§c签到系统§6]§c不存在的命令！");
			}
		}
		return true;
	}
   
}
