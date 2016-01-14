package com.chenhao220.SigninRelease;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
    public ItemStack normal;
    public ItemStack unsignin;
    public ItemStack unusesignin;
    public ItemStack beensignin;
    public ItemStack signinactive;
    public ItemStack shop;
    
    public ItemStack getnormal(int day,int credit){
    	byte id = 7;
    	normal = new ItemStack(Material.WOOL,1,id);
    	ItemMeta m_normal = normal.getItemMeta();
    	m_normal.setDisplayName("§6[§c签到§6]§c不可用的签到");
    	List<String> lore = new ArrayList<String>();
    	lore.add("§e本月第:§a"+day+"§e天签到,签到奖励:");
    	lore.add("§a签到卷:§a"+credit+"§a张");
    	m_normal.setLore(lore);
    	normal.setItemMeta(m_normal);
    	return this.normal;
    }
    public ItemStack getunsignin(int day,int credit){
    	byte id = 14;
    	unsignin = new ItemStack(Material.WOOL,1,id);
    	ItemMeta meta = unsignin.getItemMeta();
    	meta.setDisplayName("§6[§c签到§6]§c错过的签到");
    	List<String> lore = new ArrayList<String>();
    	lore.add("§e本月第:§a"+day+"§e天签到,签到奖励:");
    	lore.add("§a签到卷:§a"+credit+"§a张");
    	meta.setLore(lore);
    	unsignin.setItemMeta(meta);
    	return this.unsignin;
    }
    public ItemStack getsigninactive(int day,int credit){
    	byte id = 1;
    	signinactive = new ItemStack(Material.WOOL,1,id);
    	ItemMeta meta = signinactive.getItemMeta();
    	meta.setDisplayName("§6[§c签到§6]§a现在可用的签到");
    	List<String> lore = new ArrayList<String>();
    	lore.add("§e本月第:§a"+day+"§e天签到,签到奖励:");
    	lore.add("§a签到卷:§a"+credit+"§a张");
    	meta.setLore(lore);
    	signinactive.setItemMeta(meta);
    	return this.signinactive;
    }
    public ItemStack getbeensignin(int day,int credit){
    	byte id = 5;
    	beensignin = new ItemStack(Material.WOOL,1,id);
    	ItemMeta meta = beensignin.getItemMeta();
    	meta.setDisplayName("§6[§c签到§6]§e已完成的签到");
    	List<String> lore = new ArrayList<String>();
    	lore.add("§e本月第:§a"+day+"§e天签到");
    	meta.setLore(lore);
    	beensignin.setItemMeta(meta);
    	return this.beensignin;
    }
    public ItemStack getshop(){
    	shop = new ItemStack(Material.CHEST,1);
    	ItemMeta meta = shop.getItemMeta();
    	meta.setDisplayName("§6[§c签到§6]§e进入签到卷商店");
    	shop.setItemMeta(meta);
    	return this.shop;
    }
    
}