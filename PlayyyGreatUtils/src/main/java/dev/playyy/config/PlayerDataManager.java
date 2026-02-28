package dev.playyy.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager {

    private final File dataFile;
    private final Gson gson;
    private Map<String, String> playerRanks;

    public PlayerDataManager(File dataFolder) {
        this.dataFile = new File(dataFolder, "players.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.playerRanks = new HashMap<>();
    }

    public void load() {
        if (!dataFile.exists()) {
            save();
            return;
        }

        try (FileReader reader = new FileReader(dataFile)) {
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> loaded = gson.fromJson(reader, type);

            if (loaded != null) {
                this.playerRanks = loaded;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(playerRanks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasPlayer(String uuid) {
        return playerRanks.containsKey(uuid);
    }


    public String getPlayerRank(String uuid) {
        return playerRanks.getOrDefault(uuid, "default");
    }

    public void setPlayerRank(String uuid, String rankId) {
        playerRanks.put(uuid, rankId);
        save();
    }
}