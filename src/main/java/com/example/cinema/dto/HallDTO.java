package com.example.cinema.dto;


public class HallDTO {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String label;

    private int capacity;

    private long cinema;

    private String cinemaName;

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

    public long getCinema() {
        return cinema;
    }

    public void setCinema(long cinema) {
        this.cinema = cinema;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
