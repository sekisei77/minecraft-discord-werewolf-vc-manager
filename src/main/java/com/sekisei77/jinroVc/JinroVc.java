package com.sekisei77.jinroVc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class JinroVc extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            system.setup();
        } catch (IOException e) {
            Bukkit.broadcastMessage("プラグイン起動エラーです: " + e);
            throw new RuntimeException(e);
        }
        Bukkit.getServer().getPluginManager().registerEvents(new main(), this);
        getCommand("register").setExecutor(new commands());
        getCommand("vc_return").setExecutor(new commands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            jinroBot.stop();
        } catch (Exception e) {

        }
        while (jinroBot.bot_runnning()){

        }
    }
}
