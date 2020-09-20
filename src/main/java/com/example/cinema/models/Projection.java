package com.example.cinema.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "projections")
public class Projection {

    @Id
    private int id;

    @Column
    private Date dateTime;

    @Column
    private int notReservedSeats;

    @Column
    private int price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id")
    private MovieHall hall;

    public Projection() { }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
