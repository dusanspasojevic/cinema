package com.example.cinema.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Projection {

    @Id
    private String id;

    @Column
    private Date dateTime;

    @Column
    private int notReservedSeats;

    @Column
    private int price;

    @Column
    private int reservedTickets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id")
    private MovieHall hall;

    public Projection() { this.reservedTickets = 0; }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getNotReservedSeats() {
        return notReservedSeats;
    }

    public void setNotReservedSeats(int notReservedSeats) {
        this.notReservedSeats = notReservedSeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getReservedTickets() {
        return reservedTickets;
    }

    public void setReservedTickets(int reservedTickets) {
        this.reservedTickets = reservedTickets;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieHall getHall() {
        return hall;
    }

    public void setHall(MovieHall hall) {
        this.hall = hall;
    }
}
