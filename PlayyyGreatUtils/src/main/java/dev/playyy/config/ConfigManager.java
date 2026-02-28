package dev.playyy.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.playyy.models.ItemCost;
import dev.playyy.models.Rank;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {



    private final File dataFolder;
    private final File configFile;
    private final Gson gson;

    private RankConfig cachedConfig;

    public ConfigManager(File dataFolder) {
        this.dataFolder = dataFolder;
        this.configFile = new File(dataFolder, "ranks.json");

        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void loadConfig() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        if (!configFile.exists()) {
            System.out.println("[HyStar] ranks.json não encontrado. Gerando configurações padrão...");
            createDefaultConfig();
        }

        readFromFile();
    }

    private void createDefaultConfig() {
        RankConfig defaultConfig = new RankConfig();
        Map<String, Rank> defaultRanks = new HashMap<>();

        Rank default_ = new Rank(
                "default",
                "Default",
                0.0,
                Arrays.asList(),
                "default",
                "explorador"
        );

        Rank explorador = new Rank(
                "explorador",
                "Explorador",
                1000.0,
                Arrays.asList(new ItemCost("Ingredient_Bar_Gold", 10)),
                "rank.explorador",
                "conquistador"
        );

        Rank conquistador = new Rank(
                "conquistador",
                "Conquistador",
                5000.0,
                Arrays.asList(new ItemCost("Ingredient_Bar_Iron", 50), new ItemCost("Ingredient_Bar_Gold", 10)),
                "rank.conquistador",
                null // Último rank
        );
        defaultRanks.put("default", default_);
        defaultRanks.put("explorador", explorador);
        defaultRanks.put("conquistador", conquistador);
        defaultConfig.setRanks(defaultRanks);
        defaultConfig.setDefaultRank("default");
        saveToFile(defaultConfig);
    }

    private void readFromFile() {
        try (FileReader reader = new FileReader(configFile)) {
            cachedConfig = gson.fromJson(reader, RankConfig.class);
            System.out.println("[HyStar] Ranks carregados com sucesso!");
        } catch (IOException e) {
            System.err.println("[HyStar] Erro crítico ao ler ranks.json: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveToFile(RankConfig config) {
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            System.err.println("[HyStar] Erro ao salvar ranks.json: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public RankConfig getConfig() {
        return cachedConfig;
    }

}
