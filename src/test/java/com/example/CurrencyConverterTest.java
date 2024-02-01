package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Currency;
import com.example.util.CurrencyConverter;

public class CurrencyConverterTest {

    private CurrencyConverter currencyConverter;
    private Map<String, Currency> sampleFxRates;

    @BeforeEach
    void setUp() {
        sampleFxRates = new HashMap<>();

        sampleFxRates.put("EUR", new Currency("EUR", "EUR", "978", "Euro", 0.92, "", 1.0 / 0.92));

        currencyConverter = new CurrencyConverter(sampleFxRates);
    }

    @Test
    void testConvertCurrency() {
        double rateUSDToEUR = 0.92;
        double amountInUSD = 100;
        double expectedAmountInEUR = amountInUSD * rateUSDToEUR;

        double convertedAmount = currencyConverter.convert("USD", "EUR", amountInUSD);
        assertEquals(expectedAmountInEUR, convertedAmount, 0.01, "Conversion from USD to EUR should be correct");
    }

    @Test
    void testInvalidCurrencyCode() {
        double amount = 100;
        assertThrows(IllegalArgumentException.class, () -> {
            currencyConverter.convert("XXX", "USD", amount);
        }, "Should throw exception for invalid from-currency code");

        assertThrows(IllegalArgumentException.class, () -> {
            currencyConverter.convert("EUR", "XXX", amount);
        }, "Should throw exception for invalid to-currency code");
    }

    @Test
    void testZeroOrNegativeAmount() {
        double negativeAmount = -100;
        double zeroAmount = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            currencyConverter.convert("EUR", "USD", negativeAmount);
        }, "Should throw exception for negative amounts");

        assertEquals(0, currencyConverter.convert("EUR", "USD", zeroAmount),
                "Conversion of zero amount should be zero");
    }

}
