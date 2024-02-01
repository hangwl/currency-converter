package com.example.util;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.model.User;

public class WalletUpdater {

    private static final Logger logger = LogManager.getLogger(WalletUpdater.class);

    private List<User> users;
    private CurrencyConverter currencyConverter;

    public WalletUpdater(List<User> users, CurrencyConverter currencyConverter) {
        this.users = users;
        this.currencyConverter = currencyConverter;
    }

    public void updateWallet(String userName, String fromCurrency, String toCurrency, double amount) {
        try {
            Optional<User> userOptional = users.stream()
                    .filter(user -> user.getName().equals(userName))
                    .findFirst();

            if (!userOptional.isPresent()) {
                throw new IllegalArgumentException("User not found: " + userName);
            }

            User user = userOptional.get();
            Double fromCurrencyBalance = user.getWallet().getOrDefault(fromCurrency, 0.0);

            double convertedAmount = currencyConverter.convert(fromCurrency, toCurrency, amount);

            if (fromCurrencyBalance < amount) {
                throw new IllegalArgumentException("Insufficient balance in " + fromCurrency);
            }

            user.getWallet().put(fromCurrency, fromCurrencyBalance - amount);
            user.getWallet().merge(toCurrency, convertedAmount, Double::sum);

            logger.info(
                    "Wallet updated for user: {}. Converted {} {} to {} {}",
                    userName, amount, fromCurrency, convertedAmount, toCurrency);
        } catch (UnknownCurrencyException e) {
            logger.error(
                    "Unknown currency code encountered in transaction for user: {}. Transaction details: {} {} to {}, Reason: {}",
                    userName, amount, fromCurrency, toCurrency, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(
                    "Error updating wallet for user: {}. Transaction details: {} {} to {}, Reason: {}",
                    userName, amount, fromCurrency, toCurrency, e.getMessage());
        }
    }
}