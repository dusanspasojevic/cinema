package com.example.cinema.models;

import javax.persistence.*;

@Entity(name = "tickets")
public class Ticket {

    @Id
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projection_id")
    private Projection projection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spectator_id")
    private User spectator;

    @Column
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Column
    private String status;

    public Ticket() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public User getSpectator() {
        return spectator;
    }

    public void setSpectator(User spectator) {
        this.spectator = spectator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
