package com.example.cinema.controllers;

import com.example.cinema.dto.ProjectionDTO;
import com.example.cinema.dto.ProjectionSearchDTO;
import com.example.cinema.services.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
