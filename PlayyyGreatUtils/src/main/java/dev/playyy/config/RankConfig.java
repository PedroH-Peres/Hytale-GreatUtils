package dev.playyy.config;

import dev.playyy.models.Rank;

import java.util.HashMap;
import java.util.Map;

public class RankConfig {

    private Map<String, Rank> ranks;
    private String defaultRank;
    public RankConfig() {
        this.ranks = new HashMap<>();
    }

    public String getDefaultRank() { return defaultRank; }
    public void setDefaultRank(String defaultRank) { this.defaultRank = defaultRank; }

    public Map<String, Rank> getRanks() {
        return ranks;
    }

    public void setRanks(Map<String, Rank> ranks) {
        this.ranks = ranks;
    }

    public Rank getRankById(String rankId) {
        if (ranks != null && rankId != null) {
            return ranks.get(rankId.toLowerCase());
        }
        return null;
    }
}
