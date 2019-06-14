package com.zadanie.roman_musiiovskyi.repository;


import com.zadanie.roman_musiiovskyi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepoJPA extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);
}
