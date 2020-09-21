package com.example.cinema.controllers;

import com.example.cinema.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getWatchedMovies(@PathVariable String id, HttpSession session){
        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("SPECTATOR"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        try {
            return new ResponseEntity<>(ticketService.getWatchedMovies(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/reserved/{id}")
    public ResponseEntity<?> getReservedTickets(@PathVariable String id, HttpSession session){
        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("SPECTATOR"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        try {
            return new ResponseEntity<>(ticketService.getReservedTickets(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }

    }
}
