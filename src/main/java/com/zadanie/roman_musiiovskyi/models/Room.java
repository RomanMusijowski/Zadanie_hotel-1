package com.zadanie.roman_musiiovskyi.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room extends AbstractDomainClass{

    private String type;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateFrom;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateTo;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Additive> additives = new HashSet<Additive>();

    @ManyToOne
    private User user;




    public void addAdditive(Additive additive){
        if(!this.additives.contains(additive)){
            this.additives.add(additive);
        }

        if(!additive.getRoom().equals(this)){
            additive.setRoom(this);
        }
    }

    public void removeAdditive(Additive additive){
        this.additives.remove(additive);
        additive.setRoom(null);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Additive> getAdditives() {
        return additives;
    }

    public void setAdditives(Set<Additive> additives) {
        this.additives = additives;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "Room{" +
                "type='" + type + '\'' +
                ", additives=" + additives +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", id=" + id +
                '}';
    }
}
