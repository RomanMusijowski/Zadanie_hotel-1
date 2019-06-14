package com.zadanie.roman_musiiovskyi.controllers;

import com.zadanie.roman_musiiovskyi.services.exeptions.ResourceNotFoundException;
import com.zadanie.roman_musiiovskyi.services.interfaces.UserService;
import com.zadanie.roman_musiiovskyi.util.dtos.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerTest extends AbstractRestControllerTest{
    @Mock
    UserService userService;

    @InjectMocks
    UserRestController userController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getListOfUsers() throws Exception {

        //given
        UserDTO user1 = new UserDTO();
        user1.setName("Michale");
        user1.setSurName("Weston");

        UserDTO user2 = new UserDTO();
        user2.setName("Sam");
        user2.setSurName("Axe");

        when(userService.listAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get(UserRestController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(2)));
    }

    @Test
    public void getUserById() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setName("Michale");
        user1.setSurName("Weston");

        when(userService.getById(anyLong())).thenReturn(user1);

        //when
        mockMvc.perform(get(UserRestController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Michale")));
    }

    @Test
    public void createNewUser() throws Exception {
        //given
        UserDTO user = new UserDTO();
        user.setUserName("test@gmail.com");
        user.setName("Fred");
        user.setSurName("Flintstone");
        user.setPassword("newUser");
        user.setEncryptedPassword("newUser");
        user.setPassword("newUser");


        UserDTO returnDTO = new UserDTO();
        returnDTO.setUserName(user.getUserName());
        returnDTO.setName(user.getName());
        returnDTO.setSurName(user.getSurName());
        returnDTO.setPassword(user.getPassword());


        when(userService.createNew(any(UserDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post(UserRestController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName", equalTo("test@gmail.com")));
    }

    @Test
    public void updateUser() throws Exception {
        //given
        UserDTO user = new UserDTO();
        user.setName("Fred");
        user.setSurName("Flintstone");

        UserDTO returnDTO = new UserDTO();
        returnDTO.setName(user.getName());
        returnDTO.setSurName(user.getSurName());

        when(userService.putByDTO(anyLong(), any(UserDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(UserRestController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Fred")))
                .andExpect(jsonPath("$.surName", equalTo("Flintstone")));

    }

    @Test
    public void patchUser() throws Exception {

        //given
        UserDTO customer = new UserDTO();
        customer.setName("Fred");

        UserDTO returnDTO = new UserDTO();
        returnDTO.setName(customer.getName());
        returnDTO.setSurName("Flintstone");

        when(userService.patchByDTO(anyLong(), any(UserDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(UserRestController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Fred")))
                .andExpect(jsonPath("$.surName", equalTo("Flintstone")));
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete(UserRestController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).deleteById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(userService.getById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(UserRestController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}