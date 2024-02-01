package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.example.model.User;
import com.example.writer.UserDataWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDataWriterTest {

    private UserDataWriter userDataWriter;
    private List<User> users;

    @BeforeEach
    void setUp() {
        users = createSampleUsers();
        userDataWriter = new UserDataWriter();
    }

    private List<User> createSampleUsers() {

        List<User> users = new ArrayList<>();

        Map<String, Double> alicesWallet = new HashMap<>();
        alicesWallet.put("eur", 1500.0);
        alicesWallet.put("gbp", 800.0);
        User alice = new User("Alice", alicesWallet);
    
        Map<String, Double> bobsWallet = new HashMap<>();
        bobsWallet.put("usd", 1000.0);
        bobsWallet.put("cad", 1200.0);
        User bob = new User("Bob", bobsWallet);
    
        Map<String, Double> charlesWallet = new HashMap<>();
        charlesWallet.put("usd", 2000.0);
        charlesWallet.put("jpy", 300000.0);
        User charles = new User("John", charlesWallet);
    
        users.add(alice);
        users.add(bob);
        users.add(charles);
    
        return users;
        
    }
    
    @Test
    void testWriteUserData(@TempDir Path tempDir) throws IOException {
        
        Path tempFile = tempDir.resolve("users_test.json");
        userDataWriter.writeUserData(users, tempFile.toString());

        assertTrue(Files.exists(tempFile), "User data file should exist after writing");

        ObjectMapper mapper = new ObjectMapper();
        List<User> usersFromFile = mapper.readValue(tempFile.toFile(), new TypeReference<List<User>>() {});

        assertEquals(users, usersFromFile, "The list of users read from the file should match the original list");

    }

}
