package com.example.cinema.repositories;

import com.example.cinema.models.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Cinema findOneById(Long id);

}
