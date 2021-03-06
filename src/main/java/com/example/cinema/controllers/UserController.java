package com.example.cinema.controllers;

import com.example.cinema.dto.UserDTO;
import com.example.cinema.models.User;
import com.example.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/User")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) throws Exception{
        Map<String, String> res = new HashMap<>();
        userService.register(request);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> register(@PathVariable String id, HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("ADMIN"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        if (userService.activate(id))
            return new ResponseEntity<>("", HttpStatus.OK);
        else
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

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

                res.put("username", (String) session.getAttribute("username"));
                res.put("role", (String) session.getAttribute("role"));

            } else {
                return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            res.put("error", e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/managers")
    public ResponseEntity<?> createManager(@RequestBody User request, HttpSession session) throws Exception{
        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("ADMIN"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        Map<String, String> res = new HashMap<>();
        userService.createManager(request);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getInfo(HttpSession session) {
        Map<String, String> res = new HashMap<>();

       String username = (String) session.getAttribute("username");

        try {
            UserDTO user = userService.getUserInfo(username);

            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/managers")
    public ResponseEntity<?> getAllManagers(HttpSession session) {
        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("ADMIN"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(this.userService.getAllManagers(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable String id, HttpSession session){
        String role = (String) session.getAttribute("role");

        if (role != null && !role.equals("ADMIN"))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        if (this.userService.deleteUser(id))
            return new ResponseEntity<>("", HttpStatus.OK);
        else
            return  new ResponseEntity<>("", HttpStatus.FAILED_DEPENDENCY);
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        if (session.getAttribute("username") == null)
        {
            return new RedirectView("/index.html");
        }

        session.invalidate();
        return new RedirectView("/index.html");
    }


}
