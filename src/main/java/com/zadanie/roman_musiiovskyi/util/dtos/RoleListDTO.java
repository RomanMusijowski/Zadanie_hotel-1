package com.zadanie.roman_musiiovskyi.util.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleListDTO {
    List<RoleDTO> dtos;
}
