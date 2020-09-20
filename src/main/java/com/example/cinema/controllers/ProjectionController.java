package com.example.cinema.controllers;

import com.example.cinema.dto.ProjectionDTO;
import com.example.cinema.services.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

}
