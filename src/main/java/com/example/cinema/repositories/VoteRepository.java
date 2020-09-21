package com.example.cinema.repositories;

import com.example.cinema.models.Vote;
import com.example.cinema.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, String> {

    List<Vote> findByMovie(Movie movie);

    @Query(
            value = "SELECT * FROM votes v WHERE v.movie_id = ?1 and v.spectator_id = ?2",
            nativeQuery = true)
    Vote findByMovieAndUser(String movieId, String userId);
}