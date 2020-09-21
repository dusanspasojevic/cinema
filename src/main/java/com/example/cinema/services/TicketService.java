package com.example.cinema.services;

import com.example.cinema.dto.TicketDTO;
import com.example.cinema.models.Movie;
import com.example.cinema.models.Ticket;
import com.example.cinema.models.User;
import com.example.cinema.models.Vote;
import com.example.cinema.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class TicketService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public List<TicketDTO> getWatchedMovies(String username) throws Exception {
        User user = this.userRepository.getOne(username);
        if (user == null)
            throw new Exception("Username is not valid!");
        System.out.println(username);
        List<Ticket> reservedByUser = ticketRepository.findByStatusAndUser("BOUGHT", username);
        List<TicketDTO> responses = new ArrayList<>();
        Date now = new Date();
        System.out.println(now);

        for (Ticket t: reservedByUser) {
            if (t.isDeleted())
                continue;
            if (t.getProjection().getDateTime().after(now))
                continue;
            TicketDTO response = new TicketDTO();
            response.setId(t.getId());
            response.setSpectatorId(t.getSpectator().getUsername());
            response.setDate(t.getProjection().getDateTime());
            Movie movie = t.getProjection().getMovie();
            response.setMovieTitle(movie.getTitle());
            response.setMovieDuration(movie.getDuration());
            response.setMovieId(movie.getTitle());
            response.setCinemaName(t.getProjection().getHall().getCinema().getName());

            Vote vote = voteRepository.findByMovieAndUser(movie.getTitle(), username);
            if (vote != null)
                response.setVote(vote.getVote());
            else
                response.setVote(0);

            responses.add(response);
        }

        return responses;
    }

}
