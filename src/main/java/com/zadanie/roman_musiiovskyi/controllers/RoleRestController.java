package com.zadanie.roman_musiiovskyi.controllers;

import com.zadanie.roman_musiiovskyi.services.interfaces.RoleService;
import com.zadanie.roman_musiiovskyi.util.dtos.RoleDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.RoleListDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.RoomDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.RoomListDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(RoleRestController.BASE_URL)
public class RoleRestController {

    public static final String BASE_URL = "/roles";

    private static final Logger logger = LoggerFactory.getLogger(RoomRestController.class);
    private final RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RoleListDTO getListOfRole(){
        return new RoleListDTO(roleService.listAll());
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO getRoleById(@PathVariable Long id){
        return roleService.getById(id);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNewRole(@Valid @RequestBody RoleDTO roleDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            Map<String , String> errorMap = new HashMap<>();

            for(FieldError error :bindingResult.getFieldErrors()){
                return new ResponseEntity<Map<String , String>>(errorMap, HttpStatus.BAD_REQUEST);
            }
        }

        RoleDTO newRole= roleService.createNew(roleDTO);
        return new ResponseEntity<RoleDTO>(newRole, HttpStatus.CREATED);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO){
        return roleService.putByDTO(id, roleDTO);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO patchRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO){
        return roleService.patchByDTO(id, roleDTO);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteRole(@PathVariable Long id){
        roleService.deleteById(id);
    }
}
