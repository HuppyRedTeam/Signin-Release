package com.chenhao220.SigninRelease;

import java.io.File;
import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Load extends JavaPlugin{
	private File logf = new File(this.getDataFolder()+File.separator+"PlayerSign.yml");
	private YamlConfiguration log = new YamlConfiguration();
	private Economy eco = null;
    public void onEnable(){
 	   if(!new File(getDataFolder()+File.separator+"config.yml").exists()){
		   this.getDataFolder().mkdir();
		   this.getLogger().info("正在创建config.yml");
	   }
	   this.saveDefaultConfig();
	   if(!logf.exists()){
		   try{
			   logf.createNewFile();
		   }catch(Exception e){
			   this.getLogger().log(Level.SEVERE,"PlayerSign.yml文件读取失败");
			   this.getLogger().log(Level.SEVERE,"错误原因"+e.getMessage()+"，将自动关闭插件");
			   this.getPluginLoader().disablePlugin(this);
		   }
	   }
	   if(!setupEconomy()){
		   this.getLogger().info("Vault插件加载失败，可能是因为你没有安装Vault插件或没有Vault支持的经济插件");
		   this.getLogger().info("如果你已安装Vault且无其他异常，你可以尝试更新Vault");
		   this.getLogger().log(Level.SEVERE,"无法找到支持库，将卸载插件！");
		   this.getPluginLoader().disablePlugin(this);
	   }
	   this.getCommand("signin").setExecutor(new BasicCommand(this));
	   this.getServer().getPluginManager().registerEvents(new LoginListener(this), this);
	   this.getServer().getPluginManager().registerEvents(new Supply(this), this);
	   this.getLogger().info("插件已加载");
	   this.getLogger().info("版本:2.0 Beta");
	   new Util(this);
    }
    public void onDisable(){
    	try{
    		log.save(logf);
    	}catch(Exception e){
    		this.getLogger().log(Level.SEVERE,"保存Log文件出错，请向作者报告以下错误信息");
    		this.getLogger().log(Level.SEVERE,e.getMessage());
    	}
    	this.getLogger().info("插件已卸载");
    }
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            eco = economyProvider.getProvider();
        }

        return (eco != null);
    }
    public Economy getEconomy(){
    	return this.eco;
    }
    public YamlConfiguration getLog(){
    	return this.log;
    }
    public File getFile(){
    	return this.logf;
    }
}
