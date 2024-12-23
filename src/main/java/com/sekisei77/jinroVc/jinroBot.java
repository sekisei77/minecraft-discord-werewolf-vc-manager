package com.sekisei77.jinroVc;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.List;

public class jinroBot {
    private static JDA jda = null;
    private static String BOT_TOKEN;
    private static String GUILD_ID;
    static void start(){
        try {
            BOT_TOKEN = system.settings.getProperty("token");
            GUILD_ID = system.settings.getProperty("guild-id");
            jda = JDABuilder.createDefault(BOT_TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,GatewayIntent.GUILD_VOICE_STATES,GatewayIntent.GUILD_EXPRESSIONS,GatewayIntent.SCHEDULED_EVENTS)
                    .setRawEventsEnabled(true)
                    .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.VOICE)
                    .setActivity(Activity.watching("人狼の試合"))
                    .build();
            jda.awaitReady();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void stop(){
        jda.shutdownNow();
        jda=null;
    }

    static void send_dead_msg(String player_name){
        Guild guild = jda.getGuildById(GUILD_ID);
        VoiceChannel channel = guild.getVoiceChannelById(system.settings.getProperty("spirit-vc-id"));
        if (channel != null) {
            channel.sendMessage(player_name+"が死亡しました...").queue();
        }
    }

    static void when_dead(String discord_id){
        if(Boolean.parseBoolean(system.settings.getProperty("move"))) {
            Guild guild = jda.getGuildById(GUILD_ID);
            // 移動させたいメンバーを取得
            Member member = guild.getMemberById(discord_id);
            VoiceChannel targetChannel = guild.getVoiceChannelById(system.settings.getProperty("spirit-vc-id"));

            if (member != null && targetChannel != null) {
                guild.moveVoiceMember(member, targetChannel).queue();
            } else {
                System.out.println("メンバーまたはボイスチャンネルが見つかりません");
            }
        }
    }

    static void return_main(){
        if(Boolean.parseBoolean(system.settings.getProperty("move"))) {
            Guild guild = jda.getGuildById(GUILD_ID);
            VoiceChannel spiritChannel = guild.getVoiceChannelById(system.settings.getProperty("spirit-vc-id"));
            VoiceChannel targetChannel = guild.getVoiceChannelById(system.settings.getProperty("main-vc-id"));

            List<Member> members = spiritChannel.getMembers();

            // メンバーの名前をテキストチャンネルに書き出す
            for (Member member : members) {
                guild.moveVoiceMember(member, targetChannel).queue();
            }
        }
    }
    static boolean bot_runnning(){
        if (jda!=null){
            return true;
        }
        return false;
    }
}
