package com.zadanie.roman_musiiovskyi.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

public class PasswordEncoderProviderTest {
    BCryptPasswordEncoder encoder;

    @Before
    public void setUp() throws Exception {
        encoder = new BCryptPasswordEncoder(16);
    }

    @Test
    public void passwordEncoder() {
        String result = encoder.encode("myPassword");
        assertTrue(encoder.matches("myPassword", result));
    }
}