package com.victormoraes;

import java.util.List;
import java.util.Objects;

public class Transaction {

    private static final List<String> allowedCurrencies = List.of("BRL", "USD", "EUR");

    public Transaction(String transactionLine) {

        String[] transactionParts = transactionLine.split(":");

        if (Transaction.isMalformedTransaction(transactionLine))
            throw new IllegalArgumentException("Invalid transaction line");

        if (Transaction.isInvalidTransactionId(transactionParts[0]))
            throw new IllegalArgumentException("Invalid transaction id");

        if (Transaction.hasInvalidValue(transactionParts[1]))
            throw new IllegalArgumentException("Invalid transaction value");

        if (Transaction.isInvalidCurrency(transactionParts[2]))
            throw new IllegalArgumentException("Invalid transaction currency");

        this.value = Double.valueOf(transactionParts[1]);
        this.id = transactionParts[0];
        this.currency = transactionParts[2];
    }

    private String id;
    private Double value;
    private String currency;
    private static String[] transactionValues;

    public boolean isInvalidTransactionId() {

        return !this.id.startsWith("TXN_");
    }

    public static boolean isInvalidTransactionId(String transactionId) {

        return !transactionId.startsWith("TXN_");
    }

    public boolean hasInvalidValue() {

        return value < 0.0 ? true : false;
    }

    public static boolean hasInvalidValue(String value) {

        try {
            Double doubleValue = Double.valueOf(value);
            return doubleValue < 0.0 ? true : false;
        } catch (NumberFormatException e) {
            return true;
        }

    }

    public static boolean isInvalidCurrency(String currency) {
        return !allowedCurrencies.contains(currency);
    }

    public static boolean isMalformedTransaction(String transactionLine) {

        // null
        if (Objects.isNull(transactionLine))
            return true;
        // Invalid format
        transactionValues = transactionLine.split(":");
        
        if (!(transactionValues.length == 3))
            return true;

        // Empty String
        if (transactionLine.isBlank())
            return true;

        // Invalid ID
        if (isInvalidTransactionId(transactionValues[0]))
            return true;

        // Invalid Value
        if (hasInvalidValue(transactionValues[1]))
            return true;

        // Empty Values

        return false;
    }

    public Double getExchangedValue() {

        switch (this.currency) {
            case "USD":
                return this.value;
            case "BRL":
                return this.value * 0.2;
            case "EUR":
                return this.value * 1.1;
            default:
                break;
        }

        return 0.0;
    }

    public String getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

}
