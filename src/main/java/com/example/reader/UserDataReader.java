package com.example.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDataReader {

    private static final Logger logger = LogManager.getLogger(UserDataReader.class);

    private String filePath;

    public UserDataReader(String filePath) {
        this.filePath = filePath;
    }

    public List<User> readUserData() {

        ObjectMapper mapper = new ObjectMapper();
        List<User> users = null;

        try {
            File file = new File(filePath);

            users = mapper.readValue(file, new TypeReference<List<User>>() {
            });
            logger.info("User data successfully read from file: {}", filePath);
        } catch (IOException e) {
            logger.error("Error reading user data from file: {}", filePath, e);
        }

        return users;

    }

}
