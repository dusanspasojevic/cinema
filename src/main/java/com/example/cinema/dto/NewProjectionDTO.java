package com.example.cinema.dto;

import java.util.Date;

public class NewProjectionDTO {

    private Date dateTime;

    private int price;

    private String movie;

    private long hall;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public long getHall() {
        return hall;
    }

    public void setHall(long hall) {
        this.hall = hall;
    }
}
