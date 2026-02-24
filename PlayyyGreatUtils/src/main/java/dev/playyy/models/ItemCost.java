package dev.playyy.models;

public class ItemCost {
    private String itemId;
    private int amount;

    public ItemCost(String itemId, int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    public String getItemId() { return itemId; }
    public int getAmount() { return amount; }
}
