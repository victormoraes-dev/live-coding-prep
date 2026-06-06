package com.victormoraes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class TransactionProcessorTest {

    private final TransactionProcessor processor = new TransactionProcessor();

    @Test
    void givenValidTransactions_whenProcessing_thenShouldReturnTheSum() {

        List<String> transactions = List.of(
                "TXN_001:100.00:BRL",
                "TXN_002:50.00:USD",
                "TXN_999:20.00:EUR",
                "TXN_12345:10.5:BRL");

        Double sum = processor.processTransactions(transactions);

        Assertions.assertEquals(94.1, sum);
    }

    @Test
    void givenMalformedTransactions_whenProcessing_thenShouldReturnTheZeroSum() throws URISyntaxException, IOException {

        Path path = Paths.get(getClass().getResource("/malformed-transactions.txt").toURI());
        List<String> transactions = Files.readAllLines(path);

        Double sum = processor.processTransactions(transactions);
        Assertions.assertEquals(0.0, sum);
    }

    @Test
    void givenValidTransactionsFromFile_whenProcessing_thenShouldReturnTheSum() throws URISyntaxException, IOException {

        Path path = Paths.get(getClass().getResource("/valid-transactions.txt").toURI());
        List<String> transactions = Files.readAllLines(path);

        Double sum = processor.processTransactions(transactions);
        Assertions.assertEquals(94.1, sum);
    }

    @Test
    void givenMixTransactionsFromFile_whenProcessing_thenShouldReturnTheSum() throws URISyntaxException, IOException {

        Path path = Paths.get(getClass().getResource("/mix-valid-and-malformed-transactions.txt").toURI());
        List<String> transactions = Files.readAllLines(path);

        Double sum = processor.processTransactions(transactions);
        Assertions.assertEquals(120.0, sum);
    }

    static List<String> getMalformedTransactionLines() {
        return List.of(
                "INVALID_DATA",
                "TXN_001:abc:USD",
                "TXN_002:100.00",
                ":::",
                "null");
    }

    @Test
    void givenInvalidIdTransaction_whenValidating_thenShouldThrowIllegalArgumentException() {

        String invalidTransaction = "ABC_001:100.00:BRL";

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Transaction(invalidTransaction));
    }

    @Test
    void givenValidIdTransaction_whenValidating_thenShouldReturnFalse() {

        String validTransaction = "TXN_001:100.00:BRL";
        Transaction transaction = new Transaction(validTransaction);
        Assertions.assertEquals(false, transaction.isInvalidTransactionId());
    }

    @Test
    void givenNegativeValue_whenValidating_thenShouldReturnFalse() {

        String invalidTransaction = "TXN_001:-100.00:BRL";
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Transaction(invalidTransaction));
    }

    @Test
    void givenPositiveValue_whenValidating_thenShouldReturnFalse() {

        String validTransaction = "TXN_001:100.00:BRL";
        Transaction transaction = new Transaction(validTransaction);

        Assertions.assertEquals(false, transaction.hasInvalidValue());
    }

    static Stream<String> malformedTransactions() {

        return Stream.of(
                "INVALID_DATA",
                "TXN_001:abc:USD",
                "TXN_003:-50.00:USD",
                "TXN_004:100.00:GBP");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/malformed-transactions.txt")
    void givenMalformedTransaction_whenProcessing_thenShouldReturnTrue(String transactionLine) {

        Assertions.assertTrue(Transaction.isMalformedTransaction(transactionLine));
    }
}
