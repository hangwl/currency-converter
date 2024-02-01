package com.example.util;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.model.Currency;

public class CurrencyConverter {

    private static final Logger logger = LogManager.getLogger(CurrencyConverter.class);

    private Map<String, Currency> fxRates;
    private static final String BASE_CURRENCY = "USD";

    public CurrencyConverter(Map<String, Currency> fxRates) {
        this.fxRates = fxRates;
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {

        fromCurrency = fromCurrency.toUpperCase();
        toCurrency = toCurrency.toUpperCase();

        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        if (fromCurrency.equals(BASE_CURRENCY) && toCurrency.equals(BASE_CURRENCY)) {
            return amount;
        }

        if (!fromCurrency.equals(BASE_CURRENCY) && !fxRates.containsKey(fromCurrency)) {
            throw new UnknownCurrencyException("Unknown 'from' currency code: " + fromCurrency);
        }
        if (!toCurrency.equals(BASE_CURRENCY) && !fxRates.containsKey(toCurrency)) {
            throw new UnknownCurrencyException("Unknown 'to' currency code: " + toCurrency);
        }

        double fromRate = fromCurrency.equals(BASE_CURRENCY) ? 1.0 : fxRates.get(fromCurrency).getRate();
        double toRate = toCurrency.equals(BASE_CURRENCY) ? 1.0 : fxRates.get(toCurrency).getRate();

        double amountInBaseCurrency = amount / fromRate;
        double convertedAmount = amountInBaseCurrency * toRate;

        logger.debug("Converting {} {} to {} {}", amount, fromCurrency, convertedAmount, toCurrency);

        return convertedAmount;
    }
}

class UnknownCurrencyException extends IllegalArgumentException {
    public UnknownCurrencyException(String message) {
        super(message);
    }
}