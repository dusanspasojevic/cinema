package com.example.cinema.repositories;

import com.example.cinema.models.MovieHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HallRepository  extends JpaRepository<MovieHall, Long> {

    MovieHall findOneById(long id);

    @Query(
            value = "SELECT * FROM moviehalls h WHERE h.cinema_id = ?1",
            nativeQuery = true)
    List<MovieHall> findByCinema(long id);
}
