package com.example.cinema.services;

import com.example.cinema.dto.UserDTO;
import com.example.cinema.models.User;
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
            UserDTO response = new UserDTO();
            response.setBirthDate(m.getBirthDate());
            response.setEmail(m.getEmail());
            response.setFirstName(m.getFirstName());
            response.setLastName(m.getLastName());
            response.setPassword(m.getPassword());
            response.setPhoneNumber(m.getPhoneNumber());
            response.setUsername(m.getUsername());
            responses.add(response);
        }

        return responses;
    }



    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

    public void createManager(User request) throws Exception{
        List<User> allUsers = userRepository.findAll();
        for(User u: allUsers){
            if(u.isActive()) {
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
            if(u.isActive()) {
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
        user.setRole("SPECTATOR");
        userRepository.save(user);
    }
}
