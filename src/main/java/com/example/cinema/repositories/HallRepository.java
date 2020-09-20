package com.example.cinema.repositories;

import com.example.cinema.models.MovieHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository  extends JpaRepository<MovieHall, Long> {

    MovieHall findOneById(long id);
}
