package com.chenhao220.Signin;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Signin extends JavaPlugin{
	private File logf = new File(this.getDataFolder()+File.separator+"log.yml");
	private YamlConfiguration log = new YamlConfiguration();
    public void onEnable(){
 	   if(!new File(getDataFolder()+File.separator+"config.yml").exists()){
		   this.getDataFolder().mkdir();
		   this.getLogger().info("正在创建config.yml");
	   }
	   this.saveDefaultConfig();
	   if(!logf.exists()){
		   try{
			   log.set("File","log.yml");
			   log.save(logf);
		   }catch(Exception e){
			   this.getLogger().log(Level.SEVERE,"log.yml文件读取失败");
			   this.getLogger().log(Level.SEVERE,"错误原因"+e.getMessage()+"，将自动关闭插件");
			   this.getPluginLoader().disablePlugin(this);
		   }
	   }
	   this.getLogger().info("插件已加载");
	   this.getLogger().info("版本:1.0 Release");
	   this.getLogger().info("获取最新版本请访问 Http://www.hhxcmc.com:8080/job/signin");
    }
    public void onDisable(){
    	this.getLogger().info("插件已卸载");
    }
}
