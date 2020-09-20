package com.example.cinema.repositories;

import com.example.cinema.models.Vote;
import com.example.cinema.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, String> {

    List<Vote> findByMovie(Movie movie);
}