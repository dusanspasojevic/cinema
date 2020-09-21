package com.example.cinema.services;

import com.example.cinema.models.Cinema;
import com.example.cinema.models.MovieHall;
import com.example.cinema.models.Projection;
import com.example.cinema.repositories.CinemaRepository;
import com.example.cinema.repositories.HallRepository;
import com.example.cinema.repositories.ProjectionRepository;
import com.example.cinema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cinema.dto.HallDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class HallService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    public HallDTO createHall(HallDTO request) throws Exception{
        List<MovieHall> halls = hallRepository.findAll();
        for(MovieHall h: halls){
            if(h.getLabel().equals(request.getLabel()) && !h.isDeleted()) {
                throw new Exception("Choosen name already exists!");
            }
        }
        MovieHall hall = new MovieHall();
        long id = 1;
        if(!halls.isEmpty()){
            id = halls.size() + 1;
        }
        hall.setId(id);
        hall.setLabel(request.getLabel());
        hall.setCapacity(request.getCapacity());
        Cinema cinema = cinemaRepository.findOneById(request.getCinema());
        hall.setCinema(cinema);
        hallRepository.save(hall);

        cinema.getHalls().add(hall);
        cinemaRepository.save(cinema);
        HallDTO response = new HallDTO();
        response.setLabel(hall.getLabel());
        response.setCapacity(hall.getCapacity());
        response.setCinema(hall.getCinema().getId());
        response.setCinemaName(hall.getCinema().getName());
        response.setId(hall.getId());

        return response;
    }

    public HallDTO editHall(HallDTO request) throws Exception{
        MovieHall hall = hallRepository.findOneById(request.getId());
        hall.setLabel(request.getLabel());
        hall.setCapacity(request.getCapacity());
        hallRepository.save(hall);

        HallDTO response = new HallDTO();
        response.setLabel(hall.getLabel());
        response.setCapacity(hall.getCapacity());

        return response;
    }

    public void deleteHall(long id) {
        MovieHall hall = hallRepository.findOneById(id);
        if (hall == null)
            return;
        Cinema c = hall.getCinema();
       c.getHalls().remove(c);
       cinemaRepository.save(c);

       hall.setDeleted(true);
       hallRepository.save(hall);

        for (Projection p: hall.getProjections()) {
            p.setDeleted(true);
            projectionRepository.save(p);
        }
    }

    public List<HallDTO> getAllHalls(){
        List<MovieHall> all = hallRepository.findAll();
        List<HallDTO> responses = new ArrayList<>();
        for(MovieHall h: all){
            if (h.isDeleted())
                continue;
            HallDTO response = new HallDTO();
            response.setLabel(h.getLabel());
            response.setCapacity(h.getCapacity());
            response.setCinema(h.getCinema().getId());
            response.setCinemaName(h.getCinema().getName());
            responses.add(response);
        }
        return responses;
    }

    public List<HallDTO> getAllHallsByCinema(Long id){
        List<MovieHall> all = hallRepository.findByCinema(id);
        List<MovieHall> searchedHalls = new ArrayList<>();
        for(MovieHall h: all){
            if(!h.isDeleted()) {
                searchedHalls.add(h);
            }
        }
        List<HallDTO> responses = new ArrayList<>();
        for(MovieHall h: searchedHalls){
            HallDTO response = new HallDTO();
            response.setId(h.getId());
            response.setLabel(h.getLabel());
            response.setCapacity(h.getCapacity());
            response.setCinema(h.getCinema().getId());
            response.setCinemaName(h.getCinema().getName());
            responses.add(response);
        }
        return responses;
    }
}
