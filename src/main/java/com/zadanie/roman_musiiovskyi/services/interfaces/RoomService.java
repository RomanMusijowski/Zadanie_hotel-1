package com.zadanie.roman_musiiovskyi.services.interfaces;

import com.zadanie.roman_musiiovskyi.models.Room;
import com.zadanie.roman_musiiovskyi.services.CRUDService;
import com.zadanie.roman_musiiovskyi.util.dtos.RoomDTO;

public interface RoomService extends CRUDService<RoomDTO> {
    RoomDTO saveAndReturnDTO(Room room);
}
