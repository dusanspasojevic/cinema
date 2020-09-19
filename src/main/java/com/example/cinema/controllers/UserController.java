package com.example.cinema.controllers;

import com.example.cinema.models.User;
import com.example.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/User")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam(required = true) String username,
                                       @RequestParam(required = true) String password, HttpSession session) {
        Map<String, String> res = new HashMap<>();

        if (session.getAttribute("username") != null)
        {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }

        try {
            User user = userService.getUser(username, password);
            if (user.isActive()) {
                res.put("status", "ok");
                session.setAttribute("username", username);
                session.setAttribute("role", user.getRole());
            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            res.put("error", e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getInfo(HttpSession session) {
        Map<String, String> res = new HashMap<>();

        res.put("username", (String) session.getAttribute("username"));
        res.put("role", (String) session.getAttribute("role"));

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
