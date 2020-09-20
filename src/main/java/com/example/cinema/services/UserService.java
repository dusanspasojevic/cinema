package com.example.cinema.services;

import com.example.cinema.models.User;
import com.example.cinema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<User> getAllManagers() {
        return this.userRepository.findByRole("MANAGER");
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }
}
