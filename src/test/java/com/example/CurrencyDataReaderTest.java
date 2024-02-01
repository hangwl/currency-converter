package com.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Currency;
import com.example.reader.CurrencyDataReader;

public class CurrencyDataReaderTest {

    private CurrencyDataReader currencyDataReader;

    @BeforeEach
    void setUp() {
        currencyDataReader = new CurrencyDataReader("src/main/resources/fx_rates.json");
    }

    @Test
    void testReadFxRates() {
        Map<String, Currency> fxRates = currencyDataReader.readFxRates();

        assertNotNull(fxRates, "FX Rates map should not be null");

        for (Map.Entry<String, Currency> entry : fxRates.entrySet()) {
            Currency currency = entry.getValue();
            assertTrue(currency.getRate() > 0, "Rate for " + currency.getCode() + " should be positive");
        }
    }

}
