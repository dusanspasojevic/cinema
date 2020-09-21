package com.example.cinema.services;

import com.example.cinema.dto.TicketDTO;
import com.example.cinema.dto.VoteDTO;
import com.example.cinema.models.Movie;
import com.example.cinema.models.Ticket;
import com.example.cinema.models.User;
import com.example.cinema.models.Vote;
import com.example.cinema.repositories.MovieRepository;
import com.example.cinema.repositories.TicketRepository;
import com.example.cinema.repositories.UserRepository;
import com.example.cinema.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public boolean rateMovie(VoteDTO request)  {
        User u = userRepository.findOneByUsername(request.getSpectator());
        if (u == null)
            return false;
        Movie m = movieRepository.findOneByTitle(request.getMovie());
        if(m == null)
            return false;
        List<Vote> votes = voteRepository.findAll();
        int id = votes.size() + 1;
        Vote vote  = new Vote();
        vote.setId(id);
        vote.setMovie(m);
        vote.setSpectator(u);
        vote.setVote(request.getVote());
        voteRepository.save(vote);

        return true;
    }

}
