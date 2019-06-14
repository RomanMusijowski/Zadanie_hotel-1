package com.zadanie.roman_musiiovskyi.security.services;

public interface EncryptionService{
    String encryptString(String input);
    boolean checkPassword(String plainPassword, String encryptedPassword);
}
