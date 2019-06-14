package com.zadanie.roman_musiiovskyi.services.serviceJPA;

import com.zadanie.roman_musiiovskyi.controllers.UserRestController;
import com.zadanie.roman_musiiovskyi.models.User;
import com.zadanie.roman_musiiovskyi.models.security.Role;
import com.zadanie.roman_musiiovskyi.security.services.EncryptionService;
import com.zadanie.roman_musiiovskyi.services.interfaces.RoleService;
import com.zadanie.roman_musiiovskyi.services.interfaces.UserService;
import com.zadanie.roman_musiiovskyi.services.exeptions.ResourceNotFoundException;
import com.zadanie.roman_musiiovskyi.repository.UserRepoJPA;
import com.zadanie.roman_musiiovskyi.util.dtos.UserDTO;
import com.zadanie.roman_musiiovskyi.util.mappers.RoleMapper;
import com.zadanie.roman_musiiovskyi.util.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceJPA implements UserService {

    private  EncryptionService encryptionService;
    private  UserRepoJPA userRepoJPA;
    private  RoleService roleService;
    private  UserMapper userMapper;
    private  RoleMapper roleMapper;

    @Autowired
    public UserServiceJPA(EncryptionService encryptionService, UserRepoJPA userRepoJPA
            , RoleService roleService, UserMapper userMapper, RoleMapper roleMapper) {
        this.encryptionService = encryptionService;
        this.userRepoJPA = userRepoJPA;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUserName(String userName) {
        return userRepoJPA.findByUserName(userName)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with userId " + userName));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> listAll() {
        return userRepoJPA
                .findAll()
                .stream()
                .map(user -> {
                    UserDTO userDTO = userMapper.userToUserDTO(user);
                    userDTO.setUserUrl(getUserUrl(user.getId()));
                    return userDTO;
                })
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public UserDTO getById(Long id) {
        return userRepoJPA.findById(id)
                .map(userMapper::userToUserDTO)
                .map(userDTO -> {
                    userDTO.setUserUrl(getUserUrl(id));
                    return userDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }


    @Override
    public UserDTO createNew(UserDTO userDTO) {
        //get list of users objects
        List<Role> roles = roleService.listAll()
                .stream()
                .map(roleDTO -> {
                    Role role = roleMapper.roleDTOToRole(roleDTO);
                    return role;
                }).collect(Collectors.toList());


        User newUser = userMapper.userDTOToUser(userDTO);
        if (newUser.getId() == null){
            roles.forEach(role -> {

                //Assign user to ADMIN role
                if(role.getRole().equalsIgnoreCase("ADMIN")){
                    if (newUser.getUserName().equals("admin@gmail.com")) {
                        newUser.addRole(role);
                    }
                }

                //Assign user to STUDENT role
                if(role.getRole().equals("USER")){
                    newUser.addRole(role);
                }
            });
        }

        return saveAndReturnDTO(newUser);
    }


    public UserDTO saveAndReturnDTO(User user) {

        if(user.getPassword() != null){
            user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
        }
        User savedUser = userRepoJPA.save(user);

        UserDTO returnDto = userMapper.userToUserDTO(savedUser);
        returnDto.setUserUrl(getUserUrl(savedUser.getId()));

        return returnDto;
    }

    @Override
    public UserDTO putByDTO(Long id, UserDTO userDTO) {

        User user = userMapper.userDTOToUser(userDTO);
        user.setId(id);

        return saveAndReturnDTO(user);
    }

    @Override
    public UserDTO patchByDTO(Long id, UserDTO userDTO) {
        return userRepoJPA.findById(id).map(user -> {

            if(userDTO.getName() != null){
                user.setName(userDTO.getName());
            }

            if(userDTO.getSurName() != null){
                user.setSurName(userDTO.getSurName());
            }

            if(userDTO.getUserName() != null){
                user.setUserName(userDTO.getUserName());
            }
            System.out.println("USER!!!!!!!!!!!!!!!!");
            System.out.println(user.toString());
            UserDTO returnDto = userMapper.userToUserDTO(userRepoJPA.save(user));

            returnDto.setUserUrl(getUserUrl(id));
            return returnDto;

        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        userRepoJPA.deleteById(id);
    }

    private String getUserUrl(Long id) {
        return UserRestController.BASE_URL + "/" + id;
    }
}
