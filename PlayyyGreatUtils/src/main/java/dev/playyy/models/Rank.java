package dev.playyy.models;

import java.util.List;

public class Rank {
    private String id;
    private String name;
    private double coinCost;
    private List<ItemCost> itemCosts;
    private String permissionGroup;
    private String nextRank;

    public Rank(String id, String name, double coinCost, List<ItemCost> itemCosts, String permissionGroup, String nextRank) {
        this.id = id;
        this.name = name;
        this.coinCost = coinCost;
        this.itemCosts = itemCosts;
        this.permissionGroup = permissionGroup;
        this.nextRank = nextRank;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getCoinCost() { return coinCost; }
    public List<ItemCost> getItemCosts() { return itemCosts; }
    public String getPermissionGroup() { return permissionGroup; }
    public String getNextRank() { return nextRank; }

    public boolean isMaxRank() {
        return nextRank == null || nextRank.trim().isEmpty();
    }

    public boolean requiresItems() {
        return itemCosts != null && !itemCosts.isEmpty();
    }
}