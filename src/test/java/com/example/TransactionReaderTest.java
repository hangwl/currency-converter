package com.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Transaction;
import com.example.reader.TransactionReader;

public class TransactionReaderTest {

    private TransactionReader transactionReader;

    @BeforeEach
    void setUp() {
        transactionReader = new TransactionReader("src/main/resources/transactions.txt");
    }

    @Test
    void testReadTransactions() {
        List<Transaction> transactions = transactionReader.readTransactions();

        assertNotNull(transactions, "List of transactions should not be null");

        assertFalse(transactions.isEmpty(), "List of transactions should not be empty");
    }

    @Test
    void testValidTransactionFormat() {
        List<Transaction> transactions = transactionReader.readTransactions();

        for (Transaction transaction : transactions) {
            assertNotNull(transaction.getName(), "Transaction name should not be null");
            assertNotNull(transaction.getFromCurrency(), "From currency should not be null");
            assertNotNull(transaction.getToCurrency(), "To currency should not be null");
            assertTrue(transaction.getAmount() > 0, "Transaction amount should be positive");
        }
    }
}
