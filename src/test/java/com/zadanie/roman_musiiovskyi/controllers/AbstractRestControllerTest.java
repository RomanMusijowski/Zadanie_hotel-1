package com.zadanie.roman_musiiovskyi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.*;

public class AbstractRestControllerTest {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}