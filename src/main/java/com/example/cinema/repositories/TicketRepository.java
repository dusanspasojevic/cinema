package com.example.cinema.repositories;

import com.example.cinema.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findOneById(Long id);

    @Query(
            value = "SELECT * FROM tickets t WHERE t.status = ?1 and t.spectator_id = ?2",
            nativeQuery = true)
    List<Ticket> findByStatusAndUser(String status, String user);

}
