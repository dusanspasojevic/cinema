package com.example.cinema.repositories;

import com.example.cinema.models.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Integer> {

    Projection findOneById(int id);

    @Query(
            value = "SELECT * FROM projections p WHERE p.hall_id = ?1",
            nativeQuery = true)
    List<Projection> findByHall(long id);

}
