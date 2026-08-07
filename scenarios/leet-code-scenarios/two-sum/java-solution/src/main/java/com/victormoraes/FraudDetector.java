package com.victormoraes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FraudDetector {

    Map<String, List<Integer>> userTransactionsInWindow;

    public boolean hasSuspiciousPair(List<Transaction> transactions, int targetAmount, long windowSizeInSeconds) {

        // Define the window
        // Maximum value - windowSizeInSeconds = Minimum value
        long maxTimestamp = Long.MIN_VALUE;

        for (Transaction transaction : transactions) {
            if (Objects.nonNull(transaction) && transaction.getTimestamp() > maxTimestamp)
                maxTimestamp = transaction.getTimestamp();
        }

        long windowBegin = maxTimestamp - windowSizeInSeconds * 1000L;

        for (Transaction transaction : transactions) {

            // verify if the transaction is within the window
            if (transaction.getTimestamp() >= windowBegin && transaction.getTimestamp() <= maxTimestamp) {

                if (!userTransactionsInWindow.containsKey(transaction.getUserId())) {
                    userTransactionsInWindow.computeIfAbsent(transaction.getUserId(), k -> new ArrayList<>())
                            .add(transaction.getAmount());
                } else {
                    userTransactionsInWindow.get(transaction.getUserId()).add(transaction.getAmount());
                }

            }
        }

        // Verify duplicated transactions

        Map<String, List<Integer>> result = userTransactionsInWindow
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<Integer> list = entry.getValue();

                            // Count occurrences of each integer
                            Map<Integer, Long> counts = list.stream()
                                    .collect(Collectors.groupingBy(
                                            Function.identity(),
                                            Collectors.counting()));

                            // Keep only those that appear exactly once
                            return list.stream()
                                    .filter(i -> counts.get(i) == 1)
                                    .collect(Collectors.toList());
                        }));

        return false;
    }
}
