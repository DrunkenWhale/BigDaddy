package com.example.bigdaddy.util;


import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

public class PasswordEncryptor {

    private static final String randomString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @SneakyThrows
    public static String generatorPasswordHash(String password){
        String salt = getRandomSalt();
        return getPasswordHash(password, salt);
    }

    @SneakyThrows
    public static String generatorPasswordHash(String password,String salt){
        return getPasswordHash(password, salt);
    }

    @SneakyThrows
    public static boolean checkPasswordHash(String password, String passwordHash){
        String[] arr = passwordHash.split("\\$");
        String salt = arr[0];
        return passwordHash.equals(generatorPasswordHash(password,salt));
    }

    private static String getRandomSalt(){
        Random random = new Random();
        StringBuilder salt = new StringBuilder();
        for (int i=1;i<=7;++i){
            salt.append(randomString.charAt(random.nextInt(62)));
        }
        return String.valueOf(salt);
    }

    @SneakyThrows
    private static String getPasswordHash(String password, String salt){
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        for (int i=1;i<=37 ;++i){
            messageDigest.update((salt+password).getBytes(StandardCharsets.UTF_8));
            password = EncodeHex.encode(messageDigest.digest());
        }
        return salt + "$" + password;
    }
}
