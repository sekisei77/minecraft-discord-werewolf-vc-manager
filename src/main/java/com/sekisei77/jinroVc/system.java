package com.sekisei77.jinroVc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

public class system {
    private static String save_folder_path = "plugins/jinro-vc";
    private static String main_file_path = save_folder_path+"/setting.properties";
    private static String player_list_file_path = save_folder_path+"/list.json";

    static Properties settings;
    static Map<String ,String> player_id_list;//uuid,discord

    static void setup() throws IOException {
        File main_file = new File(main_file_path);
        File player_list_file = new File(player_list_file_path);

        Files.createDirectories(Path.of(save_folder_path));

        settings = new Properties();
        if (!main_file.exists()){
            main_file.createNewFile();
            settings.setProperty("move", String.valueOf(true));
            settings.setProperty("send_dead_msg", String.valueOf(true));
            settings.setProperty("main-vc-id","");
            settings.setProperty("spirit-vc-id","");
            settings.setProperty("token","");
            settings.setProperty("guild-id","");

            write_settings();
        }
        if (!player_list_file.exists()){
            player_list_file.createNewFile();
            player_id_list = new HashMap<>();
            write_json();
        }

        load_json();
        load_settings();

        try {
            jinroBot.start();
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING,"jinro-vc/setting.properties にbotのトークンとその他設定を書き込んでください");
        }
    }

    static void write_settings() throws IOException {
        File main_file = new File(main_file_path);
        if (!main_file.exists()){
            main_file.createNewFile();
        }
        try (FileOutputStream outputStream = new FileOutputStream(main_file_path)) {
            settings.store(outputStream, "Map Data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void write_json() throws IOException {
        File player_list_file = new File(player_list_file_path);
        if (!player_list_file.exists()){
            player_list_file.createNewFile();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(player_list_file_path)) {
            gson.toJson(player_id_list, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void load_json(){
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, String>>() {}.getType();

        try (FileReader reader = new FileReader(player_list_file_path)) {
            player_id_list = gson.fromJson(reader, mapType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void load_settings(){
        try (FileInputStream inputStream = new FileInputStream(main_file_path)) {
            settings.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
