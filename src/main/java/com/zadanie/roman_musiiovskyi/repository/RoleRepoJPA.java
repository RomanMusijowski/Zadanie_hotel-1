package com.zadanie.roman_musiiovskyi.repository;

import com.zadanie.roman_musiiovskyi.models.Additive;
import com.zadanie.roman_musiiovskyi.models.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepoJPA extends JpaRepository<Role,Long> {
}
