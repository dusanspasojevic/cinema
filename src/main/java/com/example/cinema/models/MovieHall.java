package com.example.cinema.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "moviehalls")
public class MovieHall {

    @Id
    private long id;

    @Column
    private String label;

    @Column
    private int capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    public MovieHall() {
    }

    public MovieHall(String label, int capacity, Cinema cinema) {
        this.label = label;
        this.capacity = capacity;
        this.cinema = cinema;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
}
