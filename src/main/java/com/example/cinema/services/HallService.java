package com.example.cinema.services;

import com.example.cinema.models.Cinema;
import com.example.cinema.models.MovieHall;
import com.example.cinema.repositories.CinemaRepository;
import com.example.cinema.repositories.HallRepository;
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

    public HallDTO createHall(HallDTO request) throws Exception{
        List<MovieHall> halls = hallRepository.findAll();
        for(MovieHall h: halls){
            if(h.getLabel().equals(request.getLabel())) {
                throw new Exception("Choosen name already exists!");
            }
        }
        MovieHall hall = new MovieHall();
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
        hallRepository.deleteById(id);
    }

    public List<HallDTO> getAllHalls(){
        List<MovieHall> all = hallRepository.findAll();
        List<HallDTO> responses = new ArrayList<>();
        for(MovieHall h: all){
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
        List<MovieHall> all = hallRepository.findAll();
        List<MovieHall> searchedHalls = new ArrayList<>();
        for(MovieHall h: all){
            if(h.getCinema().getId() == id) {
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
