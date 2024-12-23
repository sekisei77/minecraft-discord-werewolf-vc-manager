package com.sekisei77.jinroVc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.logging.Level;

public class commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean result = false;
        if (!(sender instanceof Player)) {
            sender.sendMessage("このコマンドはプレイヤーのみが実行できます。");
            return true;
        }

        Player player = (Player) sender;
        String cmdName = command.getName().toLowerCase();
        String id;
        if (Arrays.toString(args) == null || Arrays.toString(args) == "[]") {
            id = null;
        } else {
            try {
                id = args[0];
            } catch (NumberFormatException e) {
                id = null;
            }
        }

        switch (cmdName){
            case "register":
                if (id!=null) {
                    Bukkit.getLogger().log(Level.INFO,"discord id: "+id);
                    botPluginConnection.register(player,id);
                    player.sendMessage("登録に成功しました!");
                }else{
                    sender.sendMessage("DiscordのIDを記述してください");
                }
                result = true;
                break;
            case "vc_return":
                if (player.isOp()){
                    jinroBot.return_main();
                }else {
                    sender.sendMessage("あなたに権限がありません。");
                }
                result = true;
                break;
        }
        return result;
    }
}
