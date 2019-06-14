package com.zadanie.roman_musiiovskyi.util.mappers;

import com.zadanie.roman_musiiovskyi.models.security.Role;
import com.zadanie.roman_musiiovskyi.util.dtos.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    RoleDTO roleToRoleDTO(Role role);
    Role roleDTOToRole(RoleDTO roleDTO);
}
