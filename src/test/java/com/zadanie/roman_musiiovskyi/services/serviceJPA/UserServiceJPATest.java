package com.zadanie.roman_musiiovskyi.services.serviceJPA;

import com.zadanie.roman_musiiovskyi.models.User;
import com.zadanie.roman_musiiovskyi.repository.RoleRepoJPA;
import com.zadanie.roman_musiiovskyi.repository.UserRepoJPA;
import com.zadanie.roman_musiiovskyi.security.services.EncryptionService;
import com.zadanie.roman_musiiovskyi.services.interfaces.RoleService;
import com.zadanie.roman_musiiovskyi.services.interfaces.UserService;
import com.zadanie.roman_musiiovskyi.util.dtos.RoleDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.UserDTO;
import com.zadanie.roman_musiiovskyi.util.mappers.RoleMapper;
import com.zadanie.roman_musiiovskyi.util.mappers.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserServiceJPATest {

    @Mock
    UserRepoJPA userRepositoryImp;
    @Mock
    RoleRepoJPA roleRepositoryImp;

    UserMapper userMapper = UserMapper.INSTANCE;
    RoleMapper roleMapper = RoleMapper.INSTANCE;


    RoleService roleService;
    UserService userService;
    EncryptionService encryptionService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        roleService = new RoleServiceJPA(roleRepositoryImp, RoleMapper.INSTANCE);

        userService = new UserServiceJPA(encryptionService, userRepositoryImp
                , roleService, userMapper, roleMapper);
    }

    @Test
    public void findByUserName() {
        //given
        User user = new User();
        user.setId(1l);
        user.setUserName("userName");
        user.setName("Michale");
        user.setSurName("Weston");

        when(userRepositoryImp.findByUserName(anyString())).thenReturn(java.util.Optional.ofNullable(user));

        //when
        User userDTO = userService.findByUserName("userName");

        assertEquals("Michale", userDTO.getName());
    }

    @Test
    public void listAll() {
        //given
        User user1 = new User();
        user1.setId(1l);
        user1.setName("Michale");
        user1.setSurName("Weston");

        User user2 = new User();
        user2.setId(2l);
        user2.setName("Sam");
        user2.setSurName("Axe");

        when(userRepositoryImp.findAll()).thenReturn(Arrays.asList(user1, user2));

        //when
        List<UserDTO> customerDTOS = userService.listAll();

        //then
        assertEquals(2, customerDTOS.size());
    }

    @Test
    public void getById() {
        //given
        User user = new User();
        user.setId(1l);
        user.setName("Michale");
        user.setSurName("Weston");

        when(userRepositoryImp.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));

        //when
        UserDTO userDTO = userService.getById(1L);

        assertEquals("Michale", userDTO.getName());
    }

    @Test
    public void createNew() {
        loadRoles();
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Jim");

        User savedUser = new User();
        savedUser.setName(userDTO.getName());
        savedUser.setSurName(userDTO.getSurName());
        savedUser.setId(1l);

        when(userRepositoryImp.save(any(User.class))).thenReturn(savedUser);

        //when
        UserDTO savedDto = userService.createNew(userDTO);

        //then
        assertEquals(userDTO.getName(), savedDto.getName());
    }

    @Test
    public void putByDTO() {
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Jim");

        User savedUser = new User();
        savedUser.setName(userDTO.getName());
        savedUser.setSurName(userDTO.getSurName());
        savedUser.setId(1l);

        when(userRepositoryImp.save(any(User.class))).thenReturn(savedUser);

        //when
        UserDTO savedDto = userService.putByDTO(1L, userDTO);

        //then
        assertEquals(userDTO.getName(), savedDto.getName());
    }

    @Test
    public void deleteById() {
        Long id = 1L;

        userRepositoryImp.deleteById(id);

        verify(userRepositoryImp, times(1)).deleteById(anyLong());
    }

    public void loadRoles(){
        RoleDTO studen = new RoleDTO();
        studen.setRole("STUDENT");
        roleService.createNew(studen);
    }
}