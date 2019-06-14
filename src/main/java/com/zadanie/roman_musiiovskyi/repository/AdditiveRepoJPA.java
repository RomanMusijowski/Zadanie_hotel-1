package com.zadanie.roman_musiiovskyi.repository;

import com.zadanie.roman_musiiovskyi.models.Additive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditiveRepoJPA extends JpaRepository<Additive,Long> {
}
