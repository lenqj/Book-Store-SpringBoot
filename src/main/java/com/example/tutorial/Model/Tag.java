package com.example.tutorial.Model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Tag extends AbstractEntity{
    @Size(min = 1, max = 25)
    @NotBlank
    private String name;
    @ManyToMany(mappedBy = "tags")
    private List<Event> events = new ArrayList<>();
    public Tag(String name){
        this.name = name;
    }
    public Tag(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }
    public void setEvents(List<Event> events) {
        this.events = events;
    }
    public String getDisplayName() {
        return "#" + name + " ";
    }
}
