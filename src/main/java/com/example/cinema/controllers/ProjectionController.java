package com.example.cinema.controllers;

import com.example.cinema.dto.NewProjectionDTO;
import com.example.cinema.dto.ProjectionDTO;
import com.example.cinema.services.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/projection")
public class ProjectionController {

    @Autowired
    private ProjectionService projectionService;

    @GetMapping
    public List<ProjectionDTO> getAllProjections(){
        return projectionService.getAllProjections();
    }

    @GetMapping("/search")
    public List<ProjectionDTO> find(HttpServletRequest request) throws MalformedURLException, UnsupportedEncodingException {
        return projectionService.search(request);

    }
    @PostMapping("/reserve/{id}")
    public ResponseEntity<?> reserveProjection(@PathVariable int id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String username = (String) session.getAttribute("username");

        if (role != null && !role.equals("SPECTATOR"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        if (projectionService.reserve(id, username))
            return new ResponseEntity<>("", HttpStatus.OK);
        else
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

    }

    @PostMapping
    public  ResponseEntity<?> createProjection(@RequestBody NewProjectionDTO request, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String username = (String) session.getAttribute("username");

        if (role != null && !role.equals("MANAGER"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        try {
            return new ResponseEntity<>(projectionService.create(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/cinema/{id}")
    public ResponseEntity<?> getAllProjectionsByCinema(@PathVariable long id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        String username = (String) session.getAttribute("username");

        if (role != null && !role.equals("MANAGER"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);


        return new ResponseEntity<>(projectionService.getAllProjectionsByCinema(id), HttpStatus.OK);

    }


}
