package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.entities.Event;
import com.shyam.gujarat_police.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/")
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @PostMapping("/")
    public Event saveEvent(@RequestBody @Valid Event event){
        return eventService.saveEvent(event);
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@RequestBody @Valid Event event,
        @PathVariable("eventId") Long eventId){
        return eventService.updateEvent(event, eventId);
    }

    @DeleteMapping("/{eventId}")
    public String deleteEvent(@PathVariable("eventId") Long eventId){
        eventService.deleteEvent(eventId);
        return "Event deleted successfully with id " + eventId;
    }

    @GetMapping("/{eventId}/")
    public Event readSpecific(@PathVariable("eventId") Long eventId){
        return eventService.readSpecific(eventId);
    }
}
