package com.example.cinema.controllers;

import com.example.cinema.dto.VoteDTO;
import com.example.cinema.models.Vote;
import com.example.cinema.services.HallService;
import com.example.cinema.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/rate")
    public ResponseEntity<?> rateMovie(@RequestBody VoteDTO request, HttpSession session) throws Exception{
        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("SPECTATOR"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        if (movieService.rateMovie(request))
            return new ResponseEntity<>("", HttpStatus.OK);
        else
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAllMovies(HttpSession session) {
        String role = (String) session.getAttribute("role");

        if (role != null)
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(movieService.getAll(), HttpStatus.OK);
    }
}
