package com.victormoraes;

import java.util.List;
import java.util.Optional;

public class TransactionProcessor {

    public Double processTransactions(List<String> transactionLines) {

        return transactionLines.stream()
                .map(transaction -> {
                    try {
                        return Optional.of(new Transaction(transaction));
                    } catch (Exception e) {
                        System.out.printf("Invalid transaction line: %s", e.getMessage());
                        return Optional.empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(optional -> (Transaction) optional.get())
                .mapToDouble(Transaction::getExchangedValue)
                .sum();
    }
}