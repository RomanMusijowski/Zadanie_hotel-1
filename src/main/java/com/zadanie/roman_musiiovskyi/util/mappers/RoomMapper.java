package com.zadanie.roman_musiiovskyi.util.mappers;

import com.zadanie.roman_musiiovskyi.models.Room;
import com.zadanie.roman_musiiovskyi.models.User;
import com.zadanie.roman_musiiovskyi.util.dtos.RoomDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);
    RoomDTO roomToRoomDTO(Room room);
    Room roomDTOToRoom(RoomDTO roomDTO);
}
