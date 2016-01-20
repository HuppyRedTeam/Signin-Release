package com.chenhao220.SigninRelease;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
    public static ItemStack normal = new ItemStack(Material.WOOL,1);
    public static ItemStack unsignin = new ItemStack(Material.WOOL,1);
    public static ItemStack unusesignin = new ItemStack(Material.WOOL,1);
    public static ItemStack beensignin = new ItemStack(Material.WOOL,1);
    public static ItemStack signinactive = new ItemStack(Material.BOOK_AND_QUILL,1);
    public static ItemStack shop = new ItemStack(Material.CHEST,1);
    public static ItemStack list = new ItemStack(Material.PAPER,1);
    static{
    	normal.setDurability((short)7);
    	unsignin.setDurability((short)14);
    	beensignin.setDurability((short)5);
    }

    public static ItemStack getnormal(int day,int credit){
    	ItemMeta m_normal = normal.getItemMeta();
    	m_normal.setDisplayName("§6[§c签到§6]§c不可用的签到");
    	List<String> lore = new ArrayList<String>();
    	lore.add("§e本月第:§a"+day+"§e天签到,签到奖励:");
    	lore.add("§a签到卷:§a"+credit+"§a张");
    	m_normal.setLore(lore);
    	normal.setItemMeta(m_normal);
    	return Item.normal;
    }
    public static ItemStack getunsignin(int day,int credit){
    	ItemMeta meta = unsignin.getItemMeta();
    	meta.setDisplayName("§6[§c签到§6]§c错过的签到");
    	List<String> lore = new ArrayList<String>();
    	lore.add("§e本月第:§a"+day+"§e天签到,签到奖励:");
    	lore.add("§a签到卷:§a"+credit+"§a张");
    	meta.setLore(lore);
    	unsignin.setItemMeta(meta);
    	return Item.unsignin;
    }
    public static ItemStack getsigninactive(int day,int credit){
    	ItemMeta meta = signinactive.getItemMeta();
    	meta.setDisplayName("§6[§c签到§6]§a现在可用的签到");
    	List<String> lore = new ArrayList<String>();
    	lore.add("§e本月第:§a"+day+"§e天签到,签到奖励:");
    	lore.add("§a签到卷:§a"+credit+"§a张");
    	meta.setLore(lore);
    	signinactive.setItemMeta(meta);
    	return Item.signinactive;
    }
    public static ItemStack getbeensignin(int day,int credit){
    	ItemMeta meta = beensignin.getItemMeta();
    	meta.setDisplayName("§6[§c签到§6]§a已完成的签到");
    	List<String> lore = new ArrayList<String>();
    	lore.add("§e本月第:§a"+day+"§e天签到");
    	meta.setLore(lore);
    	beensignin.setItemMeta(meta);
    	return Item.beensignin;
    }
    public static ItemStack getshop(){
    	shop = new ItemStack(Material.CHEST,1);
    	ItemMeta meta = shop.getItemMeta();
    	meta.setDisplayName("§6[§c签到§6]§e进入签到卷商店");
    	shop.setItemMeta(meta);
    	return Item.shop;
    }
    public static ItemStack getList(List<String> lore){
    	ItemMeta meta = list.getItemMeta();
    	meta.setDisplayName("§6[§c签到信息§6]");
    	meta.setLore(lore);
    	list.setItemMeta(meta);
    	return Item.list;
    }

}