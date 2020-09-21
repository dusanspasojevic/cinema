package com.example.cinema.services;

import com.example.cinema.dto.NewProjectionDTO;
import com.example.cinema.dto.ProjectionDTO;
import com.example.cinema.models.*;
import com.example.cinema.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

@Service
public class ProjectionService {

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private HallRepository theaterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<ProjectionDTO> getAllProjections(){
        List<Projection> allProjections = projectionRepository.findAll();
        List<ProjectionDTO> responses = new ArrayList<>();
        for(Projection p: allProjections){
            if (p.isDeleted())
                continue;;
            ProjectionDTO response = new ProjectionDTO();
            response.setId(p.getId());
            response.setPrice(p.getPrice());
            response.setTime(p.getDateTime());
            response.setNotReservedSeats(p.getNotReservedSeats());
            response.setHallId(p.getHall().getId());
            response.setHallName(p.getHall().getLabel());
            response.setCinemaName(p.getHall().getCinema().getName());
            response.setMovieTitle(p.getMovie().getTitle());
            response.setDuration(p.getMovie().getDuration());
            response.setGenre(p.getMovie().getGenre());
            response.setDesc(p.getMovie().getDescription());
            response.setNotReservedSeats(p.getNotReservedSeats());
            double sum = 0;
            List<Vote> votes = voteRepository.findByMovie(p.getMovie());
            for(Vote v: votes){
                sum += v.getVote();
            }
            if (votes.size() > 0)
                response.setVote(sum / votes.size());
            else
                response.setVote(sum);
            responses.add(response);
        }
        return responses;
    }

    public List<ProjectionDTO> search(HttpServletRequest request) throws MalformedURLException, UnsupportedEncodingException {
        List<Projection> allProjections = projectionRepository.findAll();
        List<Projection> filtered = projectionRepository.findAll();

        Map<String,String> query = splitQuery(new URL("http://localhost:8090/api/projection/search?"+ request.getQueryString()));

        for(Projection p: allProjections) {
            if (query.containsKey("title")) {
                if (!query.get("title").equalsIgnoreCase(p.getMovie().getTitle())) {
                    filtered.remove(p);
                    continue;
                }}
            if (query.containsKey("desc")){

                if (!query.get("desc").equalsIgnoreCase(p.getMovie().getDescription())) {
                    filtered.remove(p);
                    continue;
                }}
            if (query.containsKey("genre")){

                if (!query.get("genre").equalsIgnoreCase(p.getMovie().getGenre())) {
                    filtered.remove(p);
                    continue;
                }}
            if (query.containsKey("price")) {

                if (Integer.parseInt(query.get("price")) != p.getPrice()) {
                    System.out.println("ovdee");
                    filtered.remove(p);
                    continue;
                }
            }

            if (query.containsKey("vote")) {
                int sum = 0;
                double vote = 0;
                List<Vote> votes = voteRepository.findByMovie(p.getMovie());
                for(Vote v: votes){
                    sum += v.getVote();
                }
                if (votes.size() > 0)
                    vote = sum / votes.size();
                if (Double.parseDouble(query.get("vote")) != vote)
                    filtered.remove(p);
            }

            if (query.containsKey("duration")) {
                if (Integer.parseInt(query.get("duration")) != p.getMovie().getDuration())
                    filtered.remove(p);
            }
        }
        List<ProjectionDTO> responses = new ArrayList<>();
        System.out.println("Daa");
        for(Projection p: filtered){
            ProjectionDTO response = new ProjectionDTO();
            response.setId(p.getId());
            response.setPrice(p.getPrice());
            response.setTime(p.getDateTime());
            response.setNotReservedSeats(p.getNotReservedSeats());
            response.setHallId(p.getHall().getId());
            response.setHallName(p.getHall().getLabel());
            response.setCinemaName(p.getHall().getCinema().getName());
            response.setMovieTitle(p.getMovie().getTitle());
            response.setDuration(p.getMovie().getDuration());
            response.setGenre(p.getMovie().getGenre());
            response.setDesc(p.getMovie().getDescription());
            response.setNotReservedSeats(p.getNotReservedSeats());
            double sum = 0;
            List<Vote> votes = voteRepository.findByMovie(p.getMovie());
            for(Vote v: votes){
                sum += v.getVote();
            }
            if (votes.size() > 0)
                response.setVote(sum / votes.size());
            else
                response.setVote(sum);
            responses.add(response);
        }
        return responses;
    }

    public boolean reserve(int projectionId, String username) {
        User user = userRepository.findOneByUsername(username);
        if (user == null)
            return false;
        Projection p = projectionRepository.findOneById(projectionId);
        if (p == null)
            return false;
        if (p.getNotReservedSeats() == 0)
            return false;

        Ticket ticket = new Ticket();
        ticket.setDeleted(false);
        ticket.setStatus("RESERVED");
        ticket.setProjection(p);
        ticket.setSpectator(user);

        List<Ticket> tickets = ticketRepository.findAll();
        int id = tickets.size() + 1;
        ticket.setId(id);
        ticketRepository.save(ticket);

        p.setNotReservedSeats(p.getNotReservedSeats() - 1);
        projectionRepository.save(p);

        return true;
    }

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        System.out.println(url);
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    public ProjectionDTO create(NewProjectionDTO request) throws Exception {
        MovieHall hall = hallRepository.getOne(request.getHall());
        if (hall == null)
            throw new Exception("Movie hall is not valid!");
        Movie movie = movieRepository.findOneByTitle(request.getMovie());
        if (movie == null)
            throw new Exception("Movie id is not valid!");
        Projection newProj = new Projection();
        newProj.setDeleted(false);
        newProj.setPrice(request.getPrice());
        newProj.setDateTime(request.getDateTime());
        int id = projectionRepository.findAll().size() + 1;
        newProj.setId(id);
        newProj.setMovie(movie);
        newProj.setHall(hall);
        newProj.setNotReservedSeats(hall.getCapacity());

        projectionRepository.save(newProj);

        ProjectionDTO response = new ProjectionDTO();
        response.setId(newProj.getId());
        response.setPrice(newProj.getPrice());
        response.setTime(newProj.getDateTime());
        response.setNotReservedSeats(newProj.getNotReservedSeats());
        response.setHallId(newProj.getHall().getId());
        response.setHallName(newProj.getHall().getLabel());
        response.setCinemaName(newProj.getHall().getCinema().getName());
        response.setMovieTitle(newProj.getMovie().getTitle());
        response.setDuration(newProj.getMovie().getDuration());
        response.setGenre(newProj.getMovie().getGenre());
        response.setDesc(newProj.getMovie().getDescription());
        response.setNotReservedSeats(newProj.getNotReservedSeats());
        double sum = 0;
        List<Vote> votes = voteRepository.findByMovie(newProj.getMovie());
        for(Vote v: votes){
            sum += v.getVote();
        }
        if (votes.size() > 0)
            response.setVote(sum / votes.size());
        else
            response.setVote(sum);

        return response;
    }


    public List<ProjectionDTO> getAllProjectionsByCinema(long id){
        List<Projection> allProjections = projectionRepository.findAll();
        List<ProjectionDTO> responses = new ArrayList<>();
        for(Projection p: allProjections){
            if (p.isDeleted())
                continue;
            if (p.getHall().getCinema().getId() != id)
                continue;
            ProjectionDTO response = new ProjectionDTO();
            response.setId(p.getId());
            response.setPrice(p.getPrice());
            response.setTime(p.getDateTime());
            response.setNotReservedSeats(p.getNotReservedSeats());
            response.setHallId(p.getHall().getId());
            response.setHallName(p.getHall().getLabel());
            response.setCinemaName(p.getHall().getCinema().getName());
            response.setMovieTitle(p.getMovie().getTitle());
            response.setDuration(p.getMovie().getDuration());
            response.setGenre(p.getMovie().getGenre());
            response.setDesc(p.getMovie().getDescription());
            response.setNotReservedSeats(p.getNotReservedSeats());
            double sum = 0;
            List<Vote> votes = voteRepository.findByMovie(p.getMovie());
            for(Vote v: votes){
                sum += v.getVote();
            }
            if (votes.size() > 0)
                response.setVote(sum / votes.size());
            else
                response.setVote(sum);
            responses.add(response);
        }
        return responses;
    }
}
