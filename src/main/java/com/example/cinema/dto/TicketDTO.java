package com.example.cinema.dto;



import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

public class TicketDTO {

    private long id;

    private long projectionId;

    private long viewerId;

    private String filmName;

    private String filmDuration;

    private long filmId;

    private String filmGenre;

    private String cinemaName;

    private String theaterName;

    private LocalDateTime time;

    private int price;
    private String status;
}
