package com.example.cinema.controllers;

import com.example.cinema.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cinema.dto.CinemaDTO;
import com.example.cinema.models.Cinema;

import java.util.List;

@RestController
@RequestMapping("api/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @PostMapping
    public Cinema createCinema(@RequestBody CinemaDTO request) throws Exception{
        return cinemaService.createCinema(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCinema(@RequestBody CinemaDTO request) throws Exception{

         return new ResponseEntity<>(cinemaService.editCinema(request), HttpStatus.OK);
    }

    @GetMapping
    public List<CinemaDTO> getAllCinemas(){
        return cinemaService.getAllCinemas();
    }


    @DeleteMapping("/{id}")
    public void deleteCinema(@PathVariable Long id){
        cinemaService.deleteCinema(id);
    }

    @GetMapping("/{id}/manager")
    public List<CinemaDTO> getAllCinemasByManager(@PathVariable String id){
        return cinemaService.getAllCinemasByManager(id);
    }
}
