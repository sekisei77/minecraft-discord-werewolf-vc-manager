package com.sekisei77.jinroVc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class botPluginConnection {
    static Player get_player_from_discord_id(String discord_id){
        Player player = Bukkit.getPlayer(get_UUID_from_discordID(discord_id));
        return player;
    }

    private static UUID get_UUID_from_discordID(String discord_id){
        UUID uuid = null;
        for (Map.Entry<String , String> entry : system.player_id_list.entrySet()) {
            if (entry.getValue().equals(discord_id)) {
                uuid = UUID.fromString(entry.getKey()); // 値が一致したらキーを返す
            }
        }
        return uuid;
    }

    static String get_discord_id_from_player(Player player){
        return system.player_id_list.get(player.getUniqueId().toString());
    }

    static void register(Player player,String discord_id){
        system.player_id_list.put(player.getUniqueId().toString(),discord_id);
        try {
            system.write_json();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
