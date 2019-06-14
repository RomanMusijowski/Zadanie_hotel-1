package com.zadanie.roman_musiiovskyi.util.mappers;

import com.zadanie.roman_musiiovskyi.models.User;
import com.zadanie.roman_musiiovskyi.util.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);

}
