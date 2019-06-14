package com.zadanie.roman_musiiovskyi.util.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AdditiveDTO {

    private Long id;
    @NotEmpty
    @Size(min = 2, max = 36)
    private String name;

    @JsonProperty("url")
    private String url;
}
