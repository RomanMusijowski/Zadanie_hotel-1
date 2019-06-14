package com.zadanie.roman_musiiovskyi.services.interfaces;

import com.zadanie.roman_musiiovskyi.models.User;
import com.zadanie.roman_musiiovskyi.services.CRUDService;
import com.zadanie.roman_musiiovskyi.util.dtos.UserDTO;

public interface UserService extends CRUDService<UserDTO> {
    UserDTO saveAndReturnDTO(User user);
    User findByUserName(String userName);
}
