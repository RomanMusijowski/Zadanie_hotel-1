package com.zadanie.roman_musiiovskyi.services.serviceJPA;

import com.zadanie.roman_musiiovskyi.controllers.RoomRestController;
import com.zadanie.roman_musiiovskyi.models.Room;
import com.zadanie.roman_musiiovskyi.repository.RoomRepoJPA;
import com.zadanie.roman_musiiovskyi.services.interfaces.RoomService;
import com.zadanie.roman_musiiovskyi.services.exeptions.ResourceNotFoundException;
import com.zadanie.roman_musiiovskyi.util.dtos.RoomDTO;
import com.zadanie.roman_musiiovskyi.util.mappers.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomServiceJPA implements RoomService {

    private RoomRepoJPA roomRepoJPA;
    private RoomMapper roomMapper;

    @Autowired
    public RoomServiceJPA(RoomRepoJPA roomRepoJPA, RoomMapper roleMapper) {
        this.roomRepoJPA = roomRepoJPA;
        this.roomMapper = roleMapper;
    }


    @Override
    public RoomDTO saveAndReturnDTO(Room room) {
        Room savedRoom = roomRepoJPA.save(room);

        RoomDTO returnDTO = roomMapper.roomToRoomDTO(savedRoom);
        returnDTO.setUrl(getRoomUrl(savedRoom.getId()));

        return returnDTO;
    }

    @Override
    public List<RoomDTO> listAll() {

        return roomRepoJPA
                .findAll()
                .stream()
                .map(room -> {
                    RoomDTO roomDTO = roomMapper.roomToRoomDTO(room);
                    roomDTO.setUrl(getRoomUrl(room.getId()));
                    return roomDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO getById(Long id) {
        return roomRepoJPA.findById(id)
                .map(roomMapper::roomToRoomDTO)
                .map(roomDTO -> {
                    roomDTO.setUrl(getRoomUrl(id));
                    return roomDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public RoomDTO createNew(RoomDTO object) {
        return saveAndReturnDTO(roomMapper.roomDTOToRoom(object));
    }

    @Override
    public RoomDTO putByDTO(Long id, RoomDTO object) {
        Room room = roomMapper.roomDTOToRoom(object);
        room.setId(id);

        return saveAndReturnDTO(room);
    }

    @Override
    public RoomDTO patchByDTO(Long id, RoomDTO object) {
        return roomRepoJPA.findById(id).map(room -> {
            if (object.getType() != null){
                room.setType(object.getType());
            }

            RoomDTO roomDTO=roomMapper.roomToRoomDTO(roomRepoJPA.save(room));
            return roomDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        roomRepoJPA.deleteById(id);
    }

    private String getRoomUrl(Long id) {
        return RoomRestController.BASE_URL + "/" + id;
    }
}
