package com.example.cinema.controllers;

import com.example.cinema.services.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cinema.dto.HallDTO;
import com.example.cinema.models.MovieHall;

import java.util.List;

@RestController
@RequestMapping("api/hall")
public class HallController {

    @Autowired
    private HallService hallService;

    @PostMapping
    public HallDTO createHall(@RequestBody HallDTO request) throws Exception{
        return hallService.createHall(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editHall(@RequestBody HallDTO request) throws Exception{

        return new ResponseEntity<>(hallService.editHall(request), HttpStatus.OK);
    }

    @GetMapping
    public List<HallDTO> getAllHalls(){
        return hallService.getAllHalls();
    }


    @DeleteMapping("/{id}")
    public void deleteHall(@PathVariable Long id){
        hallService.deleteHall(id);
    }

    @GetMapping("/{id}/cinema")
    public List<HallDTO> getAllHallsByCinema(@PathVariable Long id){
        return hallService.getAllHallsByCinema(id);
    }
}
