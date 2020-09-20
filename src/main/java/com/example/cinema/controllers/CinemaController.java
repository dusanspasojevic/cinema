package com.example.cinema.controllers;

import com.example.cinema.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping
    public void editCinema(@RequestBody Cinema request) throws Exception{
        cinemaService.editCinema(request);
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
    public List<Cinema> getAllCinemasByManager(@PathVariable String id){
        return cinemaService.getAllCinemasByManager(id);
    }
}
