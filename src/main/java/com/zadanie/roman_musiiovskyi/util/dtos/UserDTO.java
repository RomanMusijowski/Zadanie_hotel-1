package com.zadanie.roman_musiiovskyi.util.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @Email
    @NotEmpty
    @Size(min = 8)
    private String userName;
    @NotEmpty
    @Size(min = 2, max = 36)
    private String name;
    @NotEmpty
    @Size(min = 2, max = 36)
    private String surName;
    @Size(min = 6, max = 50)
    private String password;
    @Size(min = 6, max = 50)
    private String encryptedPassword;
    @JsonProperty("user_url")
    private String userUrl;

}
