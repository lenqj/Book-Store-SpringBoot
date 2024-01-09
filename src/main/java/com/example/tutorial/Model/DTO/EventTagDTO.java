package com.example.tutorial.Model.DTO;

import com.example.tutorial.Model.Event;
import com.example.tutorial.Model.Tag;
import jakarta.validation.constraints.NotNull;

public class EventTagDTO {
    @NotNull
    private Event event;
    private Tag tag;
    public EventTagDTO(){}

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
