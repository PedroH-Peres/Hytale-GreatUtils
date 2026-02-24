package dev.playyy.config;

import dev.playyy.models.Rank;

import java.util.HashMap;
import java.util.Map;

public class RankConfig {

    private Map<String, Rank> ranks;

    // Inicializa o mapa para evitar null pointers
    public RankConfig() {
        this.ranks = new HashMap<>();
    }

    // Getter
    public Map<String, Rank> getRanks() {
        return ranks;
    }

    // Setter necessário para injetar os dados no createDefaultConfig()
    public void setRanks(Map<String, Rank> ranks) {
        this.ranks = ranks;
    }

    // Método utilitário para buscar um rank rápido pelo ID
    public Rank getRankById(String rankId) {
        if (ranks != null && rankId != null) {
            return ranks.get(rankId.toLowerCase());
        }
        return null;
    }
}
