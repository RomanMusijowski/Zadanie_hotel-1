package com.zadanie.roman_musiiovskyi.services.interfaces;

import com.zadanie.roman_musiiovskyi.models.security.Role;
import com.zadanie.roman_musiiovskyi.services.CRUDService;
import com.zadanie.roman_musiiovskyi.util.dtos.RoleDTO;

public interface RoleService extends CRUDService<RoleDTO> {
    RoleDTO saveAndReturnDTO(Role role);
}
