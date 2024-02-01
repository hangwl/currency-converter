package com.example;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.model.Currency;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.reader.CurrencyDataReader;
import com.example.reader.TransactionReader;
import com.example.reader.UserDataReader;
import com.example.util.CurrencyConverter;
import com.example.util.WalletUpdater;
import com.example.writer.UserDataWriter;

public class CurrencyConverterApplication {

    private static final Logger logger = LogManager.getLogger(CurrencyConverterApplication.class);

    public static void main(String[] args) {

        logger.info("Starting Currency Converter Application");

        CurrencyDataReader currencyDataReader = new CurrencyDataReader("src/main/resources/fx_rates.json");
        Map<String, Currency> fxRates = currencyDataReader.readFxRates();

        UserDataReader userDataReader = new UserDataReader("src/main/resources/users.json");
        List<User> users = userDataReader.readUserData();

        CurrencyConverter currencyConverter = new CurrencyConverter(fxRates);

        TransactionReader transactionReader = new TransactionReader("src/main/resources/transactions.txt");
        List<Transaction> transactions = transactionReader.readTransactions();

        WalletUpdater walletUpdater = new WalletUpdater(users, currencyConverter);
        for (Transaction transaction : transactions) {
            try {
                walletUpdater.updateWallet(
                        transaction.getName(),
                        transaction.getFromCurrency(),
                        transaction.getToCurrency(),
                        transaction.getAmount());
            } catch (Exception e) {
                logger.error("Error processing transaction for {}: {}", transaction.getName(), e.getMessage());
            }
        }

        UserDataWriter userDataWriter = new UserDataWriter();
        userDataWriter.writeUserData(users, "src/main/resources/users_updated.json");

        logger.info("Currency Converter Application finished successfully");

    }

}