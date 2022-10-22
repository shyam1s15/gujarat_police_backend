package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.entities.Event;
import com.shyam.gujarat_police.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }
}
