package com.example.writer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDataWriter {

    private static final Logger logger = LogManager.getLogger(UserDataWriter.class);

    public void writeUserData(List<User> users, String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(filePath), users);
            logger.info("User data successfully written to file: {}", filePath);
        } catch (IOException e) {
            logger.error("Error writing user data to file: {}", filePath, e);
        }
    }
}
