package com.mosbach.demo.data.impl;

import com.mosbach.demo.data.api.UserManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PropertyFileUserManagerImpl implements UserManager {

    String fileName;

    static PropertyFileUserManagerImpl propertyFileUserManager = null;

    private PropertyFileUserManagerImpl(String fileName) {
        this.fileName = fileName;
    }

    static public PropertyFileUserManagerImpl getPropertyFileUserManagerImpl(String fileName) {
        if (propertyFileUserManager == null)
            propertyFileUserManager = new PropertyFileUserManagerImpl(fileName);
        return propertyFileUserManager;
    }


    @Override
    public String logUserOn(String emailFromLogon, String passwordFromLogon) {

        Properties properties = new Properties();
        Random random = new Random();
        String userEmail = "";
        String userPassword = "";
        String userToken = "";
        boolean userIsLoggedOn = false;
        boolean found = false;

        try {
            properties.load(Files.newInputStream(Paths.get(fileName)));
            int i = 1;
            while (properties.containsKey("User." + i + ".email")) {
                userEmail = properties.getProperty("User." + i + ".email");
                userPassword = properties.getProperty("User." + i + ".password");
                if (userPassword.equals(passwordFromLogon) && userEmail.equals(emailFromLogon)) {
                    userToken = "" + System.nanoTime() + random.nextInt();
                    properties.setProperty("User." + i + ".token", userToken);
                    properties.setProperty("User." + i + ".isLoggedOn", "true");
                    found = true;
                }
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (found) {
            try {
                properties.store(Files.newOutputStream(Paths.get(fileName)), "Writing all users to properties file");
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            return userToken;
        }
        else {
            return "NOT-FOUND";
        }
    }


    @Override
    public String getUserFromToken(String tokenFromLogon) {

        Properties properties = new Properties();
        String userPassword = "";
        String userEmail = "";
        String userToken = "";
        boolean userIsLoggedOn = false;

        try {
            properties.load(Files.newInputStream(Paths.get(fileName)));
            int i = 1;
            while (properties.containsKey("User." + i + ".email")) {
                userPassword = properties.getProperty("User." + i + ".password");
                userToken = properties.getProperty("User." + i + ".token");
                userEmail = properties.getProperty("User." + i + ".email");
                userIsLoggedOn = Boolean.parseBoolean(properties.getProperty("User." + i + ".isLoggedOn"));
                if (userToken.equals(tokenFromLogon) && userIsLoggedOn ) {
                    return userEmail;
                }
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userToken;
    }

}
