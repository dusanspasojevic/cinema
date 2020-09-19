package com.example.cinema.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "cinemas")
public class Cinema {

    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String email;

    @Column
    private String phone;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MovieHall> theaters = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "cinema_manager",
            joinColumns = @JoinColumn(name = "cinema_id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    List<User> managers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Projection> projections = new ArrayList<>();

    public Cinema(long id, String name, String address, String email, String phone, List<MovieHall> theaters, List<User> managers, List<Projection> projections) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.theaters = theaters;
        this.managers = managers;
        this.projections = projections;
    }

    public Cinema() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<MovieHall> getTheaters() {
        return theaters;
    }

    public void setTheaters(List<MovieHall> theaters) {
        this.theaters = theaters;
    }

    public List<User> getManagers() {
        return managers;
    }

    public void setManagers(List<User> managers) {
        this.managers = managers;
    }

    public List<Projection> getProjections() {
        return projections;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }
}
