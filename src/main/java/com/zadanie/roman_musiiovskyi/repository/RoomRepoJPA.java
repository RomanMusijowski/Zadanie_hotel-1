package com.zadanie.roman_musiiovskyi.repository;

import com.zadanie.roman_musiiovskyi.models.Room;
import com.zadanie.roman_musiiovskyi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepoJPA extends JpaRepository<Room,Long> {
}
