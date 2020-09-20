package com.example.cinema.services;

import com.example.cinema.dto.ProjectionDTO;
import com.example.cinema.dto.ProjectionSearchDTO;
import com.example.cinema.models.Movie;
import com.example.cinema.models.Projection;
import com.example.cinema.models.MovieHall;
import com.example.cinema.models.Vote;
import com.example.cinema.models.Ticket;
import com.example.cinema.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Service
public class ProjectionService {

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private HallRepository theaterRepository;

    public List<ProjectionDTO> getAllProjections(){
        List<Projection> allProjections = projectionRepository.findAll();
        List<ProjectionDTO> responses = new ArrayList<>();
        for(Projection p: allProjections){
            ProjectionDTO response = new ProjectionDTO();
            response.setId(p.getId());
            response.setPrice(p.getPrice());
            response.setTime(p.getDateTime());
            response.setNotReservedSeats(p.getNotReservedSeats());
            response.setHallId(p.getHall().getId());
            response.setHallName(p.getHall().getLabel());
            response.setCinemaName(p.getHall().getCinema().getName());
            response.setMovieTitle(p.getMovie().getTitle());
            response.setDuration(p.getMovie().getDuration());
            response.setGenre(p.getMovie().getGenre());
            response.setDesc(p.getMovie().getDescription());
            double sum = 0;
            List<Vote> votes = voteRepository.findByMovie(p.getMovie());
            for(Vote v: votes){
                sum += v.getVote();
            }
            if (votes.size() > 0)
                response.setVote(sum / votes.size());
            else
                response.setVote(sum);
            responses.add(response);
        }
        return responses;
    }
}
