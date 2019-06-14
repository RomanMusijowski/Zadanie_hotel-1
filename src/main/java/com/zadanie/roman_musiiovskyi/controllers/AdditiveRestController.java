package com.zadanie.roman_musiiovskyi.controllers;

import com.zadanie.roman_musiiovskyi.services.interfaces.AdditiveService;
import com.zadanie.roman_musiiovskyi.util.dtos.AdditiveDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.AdditiveListDTO;
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
@RequestMapping(AdditiveRestController.BASE_URL)
public class AdditiveRestController {

    public static final String BASE_URL = "/addtitives";

    private static final Logger logger = LoggerFactory.getLogger(AdditiveRestController.class);
    private final AdditiveService additiveService;

    @Autowired
    public AdditiveRestController(AdditiveService additiveService) {
        this.additiveService = additiveService;
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AdditiveListDTO getListOfAdditive(){
        return new AdditiveListDTO(additiveService.listAll());
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public AdditiveDTO getAdditiveById(@PathVariable Long id){
        return additiveService.getById(id);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNewAdditive(@Valid @RequestBody AdditiveDTO additiveDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            Map<String , String> errorMap = new HashMap<>();

            for(FieldError error :bindingResult.getFieldErrors()){
                return new ResponseEntity<Map<String , String>>(errorMap, HttpStatus.BAD_REQUEST);
            }
        }

        AdditiveDTO additiveDTO1= additiveService.createNew(additiveDTO);
        return new ResponseEntity<AdditiveDTO>(additiveDTO1, HttpStatus.CREATED);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public AdditiveDTO updateAdditive(@PathVariable Long id, @RequestBody AdditiveDTO additiveDTO){
        return additiveService.putByDTO(id, additiveDTO);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public AdditiveDTO patchAdditive(@PathVariable Long id, @RequestBody AdditiveDTO additiveDTO){
        return additiveService.patchByDTO(id, additiveDTO);
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteAdditive(@PathVariable Long id){
        additiveService.deleteById(id);
    }
}
