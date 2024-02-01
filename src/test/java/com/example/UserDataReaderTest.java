package com.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.User;
import com.example.reader.UserDataReader;

public class UserDataReaderTest {

    private UserDataReader userDataReader;

    @BeforeEach
    void setUp() {
        userDataReader = new UserDataReader("src/main/resources/users.json");
    }

    @Test
    void testReadUserData() {
        List<User> users = userDataReader.readUserData();

        // Test that the list of users is not null
        assertNotNull(users, "List of users should not be null");

        // Test that the list of users is not empty
        assertFalse(users.isEmpty(), "List of users should not be empty");
    }

    @Test
    void testPositiveWalletValues() {
        List<User> users = userDataReader.readUserData();

        for (User user : users) {
            Map<String, Double> wallet = user.getWallet();
            for (Map.Entry<String, Double> entry : wallet.entrySet()) {
                assertTrue(entry.getValue() >= 0, "Wallet balance for " + entry.getKey() + " should be positive");
            }
        }
    }

}
