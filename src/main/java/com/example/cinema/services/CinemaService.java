package com.example.cinema.services;

import com.example.cinema.models.*;
import com.example.cinema.repositories.CinemaRepository;
import com.example.cinema.repositories.HallRepository;
import com.example.cinema.repositories.ProjectionRepository;
import com.example.cinema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.cinema.dto.CinemaDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    public Cinema createCinema(CinemaDTO request) throws Exception{
        List<Cinema> cinemas = cinemaRepository.findAll();
        for(Cinema c: cinemas){
            if(c.getName().equals(request.getName()) && !c.isDeleted() ) {
                throw new Exception("Choosen name already exists!");
            }
        }
        String str[] = request.getManagersID().split(",");
        List<String> managerList = new ArrayList<String>();
        managerList = Arrays.asList(str);


        if(managerList.size() == 0){
            throw new Exception("Managers not defined.");
        }
        Cinema cinema = new Cinema();
        long id = 1;
        if(!cinemas.isEmpty()){
            id = cinemas.size() + 1;
        }
        cinema.setId(id);
        cinema.setAddress(request.getAddress());
        cinema.setEmail(request.getEmail());
        cinema.setName(request.getName());
        cinema.setPhone(request.getPhone());

        List<User> managersToSave = new ArrayList<>();
        User manager;
        for (String managerId: managerList) {
            manager = userRepository.findOneByUsername(managerId);
            cinema.getManagers().add(manager);
            manager.getCinemas().add(cinema);
            managersToSave.add(manager);
        }
        Cinema saved = cinemaRepository.save(cinema);

        for (User m: managersToSave)
            userRepository.save(m);


        Cinema response = new Cinema();
        response.setPhone(saved.getPhone());
        response.setName(saved.getName());
        response.setId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setAddress(saved.getAddress());

        return response;
    }

    public CinemaDTO editCinema(CinemaDTO request) throws Exception{
        Cinema cinema = cinemaRepository.findOneById(request.getId());
        cinema.setPhone(request.getPhone());
        cinema.setAddress(request.getAddress());
        cinema.setName(request.getName());
        cinema.setEmail(request.getEmail());
        User manager;
        List<User> managersToSave = new ArrayList<>();
        for (String id: request.getManagersID().split(",")) {
            manager = userRepository.findOneByUsername(id);
            if (!cinema.getManagers().contains(manager)) {
                cinema.getManagers().add(manager);
                manager.getCinemas().add(cinema);
                managersToSave.add(manager);
            }
        }

        cinemaRepository.save(cinema);

        for (User m : managersToSave)
            userRepository.save(m);

        CinemaDTO response = new CinemaDTO();
        response.setPhone(cinema.getPhone());
        response.setName(cinema.getName());
        response.setId(cinema.getId());
        response.setEmail(cinema.getEmail());
        response.setAddress(cinema.getAddress());


        return response;
    }

    public void deleteCinema(Long id) {
        Cinema c = cinemaRepository.getOne(id);

        for (User m: c.getManagers()){
            m.getCinemas().remove(c);
            userRepository.save(m);
        }
        List<MovieHall> halls = hallRepository.findByCinema(id);
        for (MovieHall m : halls) {
            m.setDeleted(true);
            List<Projection> projections = projectionRepository.findByHall(m.getId());
            for (Projection p: projections) {
                p.setDeleted(true);
                projectionRepository.save(p);
            }
            hallRepository.save(m);
        }
        c.setDeleted(true);
        cinemaRepository.save(c);
    }

    public List<CinemaDTO> getAllCinemas(){
        List<Cinema> allCinemas = cinemaRepository.findAll();
        List<CinemaDTO> responses = new ArrayList<>();
        for(Cinema c: allCinemas){
            if (c.isDeleted())
                continue;
            CinemaDTO response = new CinemaDTO();
            response.setPhone(c.getPhone());
            response.setName(c.getName());
            response.setId(c.getId());
            response.setEmail(c.getEmail());
            response.setAddress(c.getAddress());
            List<String> managersNames = new ArrayList<>();
            for (User u: c.getManagers()){
                managersNames.add(u.getUsername());
            }
            response.setManagersID(String.join(",", managersNames));
            responses.add(response);
        }
        return responses;
    }

    public List<CinemaDTO> getAllCinemasByManager(String id){
        List<Cinema> allCinemas = cinemaRepository.findAll();
        List<Cinema> searchedCinemas = new ArrayList<>();
        User manager = userRepository.findOneByUsername(id);
        for(Cinema c: allCinemas){
            if (c.isDeleted())
                continue;
            if(c.getManagers().contains(manager)){
                searchedCinemas.add(c);
            }
        }
        List<CinemaDTO> responses = new ArrayList<>();
        for(Cinema c: searchedCinemas){
            CinemaDTO response = new CinemaDTO();
            response.setPhone(c.getPhone());
            response.setName(c.getName());
            response.setId(c.getId());
            response.setEmail(c.getEmail());
            response.setAddress(c.getAddress());
            List<String> managersNames = new ArrayList<>();
            for (User u: c.getManagers()){
                managersNames.add(u.getUsername());
            }
            response.setManagersID(String.join(",", managersNames));
            responses.add(response);
        }
        return responses;
    }
}
