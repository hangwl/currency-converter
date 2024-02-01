package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.model.User;
import com.example.util.CurrencyConverter;
import com.example.util.WalletUpdater;

public class WalletUpdaterTest {

    private WalletUpdater walletUpdater;
    private List<User> users;
    private CurrencyConverter mockCurrencyConverter;

    @BeforeEach
    void setUp() {
        users = createSampleUsers();

        mockCurrencyConverter = Mockito.mock(CurrencyConverter.class);
        
        when(mockCurrencyConverter.convert(anyString(), anyString(), anyDouble()))
            .thenAnswer(invocation -> {
                String fromCurrency = invocation.getArgument(0);
                String toCurrency = invocation.getArgument(1);
                Double amount = invocation.getArgument(2);

                if (fromCurrency.equals("USD") && toCurrency.equals("EUR")) {
                    return amount * 0.92;
                } else if (fromCurrency.equals("EUR") && toCurrency.equals("USD")) {
                    return amount * 1/0.92;
                }
                return amount;
            });

        walletUpdater = new WalletUpdater(users, mockCurrencyConverter);
    }

    private List<User> createSampleUsers() {
        List<User> users = new ArrayList<>();
    
        Map<String, Double> alicesWallet = new HashMap<>();
        alicesWallet.put("EUR", 150.0);
        alicesWallet.put("GBP", 80.0);
        User alice = new User("Alice", alicesWallet);
    
        Map<String, Double> bobsWallet = new HashMap<>();
        bobsWallet.put("USD", 100.0);
        bobsWallet.put("CAD", 120.0);
        User bob = new User("Bob", bobsWallet);
    
        Map<String, Double> charliesWallet = new HashMap<>();
        charliesWallet.put("USD", 200.0);
        charliesWallet.put("JPY", 30000.0);
        User charlie = new User("Charlie", charliesWallet);
    
        users.add(alice);
        users.add(bob);
        users.add(charlie);
    
        return users;
    }

    private Optional<User> getUserByName(String name) {
        return users.stream()
                    .filter(user -> user.getName().equals(name))
                    .findFirst();
    }

    @Test
    void testUpdateWalletOnValidTransaction() {

        String userName = "Bob";
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        double amount = 50;

        walletUpdater.updateWallet(userName, fromCurrency, toCurrency, amount);

        Optional<User> updatedUser = getUserByName(userName);

        assertNotNull(updatedUser, "User should exist");
        assertTrue(updatedUser.get().getWallet().containsKey(fromCurrency), "Wallet should contain fromCurrency");
        assertTrue(updatedUser.get().getWallet().containsKey(toCurrency), "Wallet should contain toCurrency");

        // Assert specific updated balance values
    }

    @Test
    void testUpdateWalletOnInvalidTransaction() {
        String userName = "Alice";
        String fromCurrency = "JPY";
        String toCurrency = "USD";
        double amount = 1000;
    
        Optional<User> userBefore = getUserByName(userName);
        assertTrue(userBefore.isPresent(), "User should exist before the transaction");
    
        double originalBalance = userBefore.get().getWallet().getOrDefault(fromCurrency, 0.0);
    
        walletUpdater.updateWallet(userName, fromCurrency, toCurrency, amount);
    
        Optional<User> userAfter = getUserByName(userName);
        assertTrue(userAfter.isPresent(), "User should still exist after the transaction");
        double balanceAfter = userAfter.get().getWallet().getOrDefault(fromCurrency, 0.0);
    
        assertEquals(originalBalance, balanceAfter, "User's balance should remain unchanged after an invalid transaction");
    }
}
