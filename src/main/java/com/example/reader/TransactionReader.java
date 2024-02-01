package com.example.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.model.Transaction;

public class TransactionReader {

    private static final Logger logger = LogManager.getLogger(TransactionReader.class);

    private String filePath;

    public TransactionReader(String filePath) {
        this.filePath = filePath;
    }

    public List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    Transaction transaction = parseTransactionLine(line);
                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                } catch (IllegalArgumentException e) {
                    logger.error("Error parsing transaction line: {}", e.getMessage());
                }
            }
            logger.info("Transactions successfully read from file: {}", filePath);

        } catch (IOException e) {
            logger.error("Error reading transactions from file: {}", filePath, e);
        }
        return transactions;
    }

    private Transaction parseTransactionLine(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Transaction does not have exactly 4 arguments: " + line);
        }

        String name = parts[0];
        String fromCurrency = parts[1];
        String toCurrency = parts[2];
        double amount;

        try {
            amount = Double.parseDouble(parts[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format in line: " + line, e);
        }

        return new Transaction(name, fromCurrency, toCurrency, amount);
    }
}
