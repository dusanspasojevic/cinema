package com.example.cinema.repositories;

import com.example.cinema.models.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Long> {

    Projection findOneById(Long id);
}