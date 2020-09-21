package com.example.cinema.services;

import com.example.cinema.dto.UserDTO;
import com.example.cinema.models.Cinema;
import com.example.cinema.models.User;
import com.example.cinema.repositories.CinemaRepository;
import com.example.cinema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    public User getUser(String username, String password) throws Exception {
        User user = this.userRepository.getOne(username);

        if (user == null)
            throw new Exception("Username is not valid!");

        if (password != null && !user.getPassword().equals(password))
            throw new Exception("Password is not valid!");

        return user;
    }

    public List<UserDTO> getAllManagers() {
        List<User> managers =  this.userRepository.findByRole("MANAGER");

        List<UserDTO> responses = new ArrayList<>();
        for (User m: managers) {
            if(m.isDeleted())
                continue;
            UserDTO response = new UserDTO();
            response.setBirthDate(m.getBirthDate());
            response.setEmail(m.getEmail());
            response.setFirstName(m.getFirstName());
            response.setLastName(m.getLastName());
            response.setPassword(m.getPassword());
            response.setPhoneNumber(m.getPhoneNumber());
            response.setUsername(m.getUsername());
            response.setActive(m.getActive());
            responses.add(response);
        }

        return responses;
    }

    public boolean activate(String id) {
        User user = userRepository.findOneByUsername(id);
        if (user == null)
            return false;
        user.setActive(true);
        userRepository.save(user);

        return true;
    }

    public boolean deleteUser(String id){
        User user = userRepository.findOneByUsername(id);
        if (user == null)
            return false;
        if (user.getRole().equalsIgnoreCase("MANAGER")) {
            List<Cinema> cinemas = user.getCinemas();
            for (Cinema c : cinemas) {
                if (c.isDeleted())
                    continue;
                if (c.getManagers().size() == 1) {
                    return false;
                }
                c.getManagers().remove(user);
                cinemaRepository.save(c);
            }
        }
        user.setDeleted(true);
        userRepository.save(user);
        return true;
    }

    public void createManager(User request) throws Exception{
        List<User> allUsers = userRepository.findAll();
        for(User u: allUsers){
            if(u.isActive() && !u.isDeleted()) {
                if (u.getUsername().equals(request.getUsername())) {
                    throw new Exception("Username already exists!");
                }
            }
        }
        LocalDate now = LocalDate.now();
        if(request.getBirthDate() == null){
            throw new Exception("Date is not valid.");
        }
        User user = new User();
        user.setActive(true);
        user.setBirthDate(request.getBirthDate());
        user.setFirstName(request.getFirstName());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole("MANAGER");
        userRepository.save(user);
    }

    public void register(User request) throws Exception{
        List<User> allUsers = userRepository.findAll();
        for(User u: allUsers){
            if(u.isActive() && !u.isDeleted()) {
                if (u.getUsername().equals(request.getUsername())) {
                    throw new Exception("Username already exists!");
                }
            }
        }
        LocalDate now = LocalDate.now();
        if(request.getBirthDate() == null){
            throw new Exception("Date is not valid.");
        }
        User user = new User();
        user.setBirthDate(request.getBirthDate());
        user.setFirstName(request.getFirstName());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        if (user.getRole().equalsIgnoreCase("MANAGER")) {
            user.setActive(false);
        } else {
            user.setActive(true);
        }
        userRepository.save(user);
    }
}
