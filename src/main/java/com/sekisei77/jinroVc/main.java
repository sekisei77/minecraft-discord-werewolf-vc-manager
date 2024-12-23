package com.sekisei77.jinroVc;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;
import java.util.logging.Level;

public class main implements Listener {
    @EventHandler
    static void onPlayerDeathEvent(PlayerDeathEvent event){
        System.out.println(system.settings);
        if (system.player_id_list.get(event.getEntity().getUniqueId().toString())!=null) {
            jinroBot.when_dead(botPluginConnection.get_discord_id_from_player(Objects.requireNonNull(event.getEntity().getPlayer())));
            if(Boolean.parseBoolean(system.settings.getProperty("send_dead_msg"))){
                jinroBot.send_dead_msg(event.getEntity().getPlayerListName());
            }
        }
    }
}
