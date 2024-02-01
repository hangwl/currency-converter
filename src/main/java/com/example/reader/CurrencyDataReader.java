package com.example.reader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.model.Currency;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CurrencyDataReader {

    private static final Logger logger = LogManager.getLogger(CurrencyDataReader.class);

    private String filePath;

    public CurrencyDataReader(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, Currency> readFxRates() {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Currency> currencyMap = new HashMap<>();

        try {
            File file = new File(filePath);
            Map<String, Currency> tempMap = mapper.readValue(file, new TypeReference<Map<String, Currency>>() {});
            tempMap.forEach((key, value) -> currencyMap.put(key.toUpperCase(), value));
            logger.info("FX rates successfully read from file: {}", filePath);
        } catch (IOException e) {
            logger.error("Error reading FX rates from file: {}", filePath, e);
        }
        
        return currencyMap;
    }
    
}
