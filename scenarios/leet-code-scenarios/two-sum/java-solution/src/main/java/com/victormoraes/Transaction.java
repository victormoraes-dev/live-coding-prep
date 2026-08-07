package com.victormoraes;

public class Transaction {
    private String userId; // who made the transaction
    private int amount; // transaction amount in cents (positive integer)
    private long timestamp; // epoch seconds

    public Transaction(String userId, int amount, long timestamp) {
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public int getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }
}