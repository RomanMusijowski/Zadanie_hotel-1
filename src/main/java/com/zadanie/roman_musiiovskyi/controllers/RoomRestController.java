package com.zadanie.roman_musiiovskyi.controllers;

import com.zadanie.roman_musiiovskyi.models.User;
import com.zadanie.roman_musiiovskyi.services.interfaces.RoomService;
import com.zadanie.roman_musiiovskyi.util.dtos.RoomDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.RoomListDTO;

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
@RequestMapping(RoomRestController.BASE_URL)
public class RoomRestController {

    public static final String BASE_URL = "/rooms";

    private static final Logger logger = LoggerFactory.getLogger(RoomRestController.class);
    private final RoomService roomService;

    @Autowired
    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }



    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RoomListDTO getListOfRoom(){
        return new RoomListDTO(roomService.listAll());
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public RoomDTO getRoomById(@PathVariable Long id){
        return roomService.getById(id);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNewRoom(@Valid @RequestBody RoomDTO roomDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            Map<String , String> errorMap = new HashMap<>();

            for(FieldError error :bindingResult.getFieldErrors()){
                return new ResponseEntity<Map<String , String>>(errorMap, HttpStatus.BAD_REQUEST);
            }
        }

        RoomDTO newRoom= roomService.createNew(roomDTO);
        return new ResponseEntity<RoomDTO>(newRoom, HttpStatus.CREATED);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public RoomDTO updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO){
        return roomService.putByDTO(id, roomDTO);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public RoomDTO patchRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO){
        return roomService.patchByDTO(id, roomDTO);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteRoom(@PathVariable Long id){
        roomService.deleteById(id);
    }
}
