package com.example.cinema.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DiscriminatorColumn(name = "myDType")
@Entity(name = "users")
public class User {
    @Id
    private String username;

    @Column(insertable = false, updatable = false)
    private String myDType;

    @Column
    private String password;

    @Column
    private String firstName;

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getActive() {
        return active;
    }

    @Column
    private String lastName;

    @Column
    private String role;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private Date birthDate;

    @Column
    private Boolean active;

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    @ManyToMany(mappedBy = "managers", fetch = FetchType.LAZY)
    private List<Cinema> cinemas = new ArrayList<>();

    @Column
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @OneToMany(mappedBy = "spectator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "spectator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public User() {
    }

    public User(User user) {
        username = user.getUsername();
        password = user.getPassword();
        firstName = user.getFirstName();
        lastName = user.lastName;
        role = user.role;
        phoneNumber = user.phoneNumber;
        email = user.email;
        birthDate = user.birthDate;
        active = user.active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setAddress(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birth date='" + birthDate + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
