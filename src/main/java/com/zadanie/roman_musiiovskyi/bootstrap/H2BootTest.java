package com.zadanie.roman_musiiovskyi.bootstrap;

import com.zadanie.roman_musiiovskyi.models.Room;
import com.zadanie.roman_musiiovskyi.services.interfaces.AdditiveService;
import com.zadanie.roman_musiiovskyi.services.interfaces.RoleService;
import com.zadanie.roman_musiiovskyi.services.interfaces.RoomService;
import com.zadanie.roman_musiiovskyi.services.interfaces.UserService;
import com.zadanie.roman_musiiovskyi.util.dtos.AdditiveDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.RoleDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.RoomDTO;
import com.zadanie.roman_musiiovskyi.util.dtos.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class H2BootTest implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(H2BootTest.class);

    private RoleService roleService;
    private UserService userService;
    private RoomService roomService;
    private AdditiveService additiveService;

    @Autowired
    public H2BootTest(RoleService roleService, UserService userService,RoomService roomService, AdditiveService additiveService) {
        this.roleService = roleService;
        this.userService = userService;
        this.roomService = roomService;
        this.additiveService = additiveService;
    }

    private void loadRoles(){
        logger.info("Load roles");

        RoleDTO admin = new RoleDTO();
        admin.setRole("ADMIN");
        roleService.createNew(admin);

        RoleDTO studen = new RoleDTO();
        studen.setRole("USER");
        roleService.createNew(studen);
    }

    private void loadUsers(){
        logger.info("Load users");

        UserDTO admin = new UserDTO();
        admin.setUserName("admin@gmail.com");
        admin.setName("Admin");
        admin.setSurName("Admin");
        admin.setPassword("admin");

        userService.createNew(admin);

        UserDTO user = new UserDTO();
        user.setUserName("pgnus@gmail.com");
        user.setName("Piotr");
        user.setSurName("Gnus");
        user.setPassword("petro");

        userService.createNew(user);

        UserDTO user2 = new UserDTO();
        user2.setUserName("rushla@gmail.com");
        user2.setName("Roman");
        user2.setSurName("Kushla");
        user2.setPassword("roman");

        userService.createNew(user2);

        UserDTO user3 = new UserDTO();
        user3.setUserName("plabuda@gmail.com");
        user3.setName("Pavlo");
        user3.setSurName("Labuda");
        user3.setPassword("pavlo");

        userService.createNew(user3);
    }

    public void loadRooms(){
        logger.info("Load rooms");

        RoomDTO room = new RoomDTO();
        room.setType("SANDART");
        roomService.createNew(room);

        RoomDTO room1 = new RoomDTO();
        room1.setType("PREMIUM");
        roomService.createNew(room1);

        RoomDTO room2 = new RoomDTO();
        room2.setType("LUX");
        roomService.createNew(room2);

    }


    public void loadAdd(){
        logger.info("Load rooms");

        AdditiveDTO additiveDTO = new AdditiveDTO();
        additiveDTO.setName("BAR");
        additiveService.createNew(additiveDTO);

        AdditiveDTO additiveDTO1= new AdditiveDTO();
        additiveDTO1.setName("LODOWKA");
        additiveService.createNew(additiveDTO1);

        AdditiveDTO additiveDTO2 = new AdditiveDTO();
        additiveDTO2.setName("OBSLUDA++");
        additiveService.createNew(additiveDTO2);

    }

    public void sout(){
        List<UserDTO> userDTOS = userService.listAll();
        userDTOS.forEach(userDTO -> {
            System.out.println(userDTO.toString());
        });

        List<RoomDTO> roomDTOS = roomService.listAll();
        roomDTOS.forEach(roomDTO -> {
            System.out.println(roomDTO.toString());
        });
        List<AdditiveDTO> additiveDTOS = additiveService.listAll();
        additiveDTOS.forEach(additiveDTO -> {
            System.out.println(additiveDTO.toString());
        });


    }

    @Override
    public void run(String... args) throws Exception {
        loadRoles();
        loadUsers();
        loadRooms();
        loadAdd();
        sout();
    }


}
