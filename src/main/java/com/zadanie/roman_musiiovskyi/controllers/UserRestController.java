package com.zadanie.roman_musiiovskyi.controllers;

import com.zadanie.roman_musiiovskyi.models.User;
import com.zadanie.roman_musiiovskyi.services.interfaces.UserService;
import com.zadanie.roman_musiiovskyi.util.dtos.UserDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.UserListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(UserRestController.BASE_URL)
public class UserRestController {

    public static final String BASE_URL = "/users";

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserListDTO getListOfUser(){
        return new UserListDTO(userService.listAll());
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Long id){
        return userService.getById(id);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNewUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            Map<String , String> errorMap = new HashMap<>();

            for(FieldError error :bindingResult.getFieldErrors()){
                return new ResponseEntity<Map<String , String>>(errorMap, HttpStatus.BAD_REQUEST);
            }
        }

        UserDTO newUser = userService.createNew(userDTO);
        return new ResponseEntity<UserDTO>(newUser, HttpStatus.CREATED);



    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return userService.putByDTO(id, userDTO);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public UserDTO patchUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return userService.patchByDTO(id, userDTO);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }






    @GetMapping({"/student/{id}"})
    @ResponseStatus(HttpStatus.OK)
//        @PreAuthorize("#username == authentication.principal.username")
    public UserDTO getByIdForUser(@PathVariable Long id, Authentication authentication){
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);


        if (user.getId() == id) {
            return userService.getById(id);
        }
        return null;
    }

    @PutMapping({"/editUser/{id}"})
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("#username == authentication.principal.username")
    public UserDTO updateUserForUser(@PathVariable Long id, @RequestBody UserDTO userDTO
            , Authentication authentication) {

        String userName = authentication.getName();
        User user = userService.findByUserName(userName);


        if (user.getId() == id) {
            return userService.putByDTO(user.getId(), userDTO);
        }

        return null;
    }

    @PatchMapping({"/editUser/{id}"})
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("#username == authentication.principal.username")
    public UserDTO patchUserForUser(@PathVariable Long id, @RequestBody UserDTO userDTO
            , Authentication authentication) {

        String userName = authentication.getName();
        User user = userService.findByUserName(userName);


        if (user.getId() == id) {
            return userService.patchByDTO(user.getId(), userDTO);
        }

        return null;
    }
}
