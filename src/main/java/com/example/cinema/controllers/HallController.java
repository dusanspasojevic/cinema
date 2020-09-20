package com.example.cinema.controllers;

import com.example.cinema.services.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cinema.dto.HallDTO;
import com.example.cinema.models.MovieHall;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("api/hall")
public class HallController {

    @Autowired
    private HallService hallService;

    @PostMapping
    public ResponseEntity<?> createHall(@RequestBody HallDTO request, HttpSession session) throws Exception{
        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("MANAGER"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(hallService.createHall(request),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editHall(@RequestBody HallDTO request,HttpSession session) throws Exception{

        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("MANAGER"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(hallService.editHall(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllHalls(HttpSession session){

        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("MANAGER"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(hallService.getAllHalls(), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHall(@PathVariable Long id, HttpSession session){
        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("MANAGER"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        hallService.deleteHall(id);

        return new ResponseEntity<>("", HttpStatus.OK);

    }

    @GetMapping("/{id}/cinema")
    public ResponseEntity<?>  getAllHallsByCinema(@PathVariable Long id, HttpSession session){
        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("MANAGER"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(hallService.getAllHallsByCinema(id), HttpStatus.OK);
    }
}
