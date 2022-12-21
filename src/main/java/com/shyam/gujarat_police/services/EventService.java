package com.shyam.gujarat_police.services;

import com.shyam.gujarat_police.entities.Event;
import com.shyam.gujarat_police.exceptions.DataNotFoundException;
import com.shyam.gujarat_police.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public Event updateEvent(Event event, Long eventId) {
        if (Objects.isNull(eventId)){
            throw new DataNotFoundException("Event id not found with id " + eventId);
        }
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            throw new DataNotFoundException("Event not found with id " + eventId);
        } else {
            Event obtainedEvent = optionalEvent.get();
            obtainedEvent.setEventName(obtainedEvent.getEventName());
            obtainedEvent.setEventDetails(obtainedEvent.getEventDetails());
            obtainedEvent.setEventStartDate(obtainedEvent.getEventStartDate());
            obtainedEvent.setEventEndDate(obtainedEvent.getEventEndDate());
            obtainedEvent.setAssignPolice(obtainedEvent.getAssignPolice());
            return eventRepository.save(obtainedEvent);
        }
    }

    public Event readSpecific(Long eventId){
        return eventRepository.findById(eventId)
                .orElseThrow(()->new DataNotFoundException("Event not found with id: " + eventId));
    }

    public void deleteEvent(Long eventId){
        eventRepository.deleteById(eventId);
    }

    public List<Event> getEventsBetweenGivenEvent(long eventId) {
        Event event = eventRepository.findById(eventId).
                orElseThrow(()->new DataNotFoundException("Event not found with id: " + eventId));
        return eventRepository.getEventsBetweenGivenEvent(event.getEventStartDate(), event.getEventEndDate());
    }
}
