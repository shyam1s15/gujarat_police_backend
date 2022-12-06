package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.entities.Event;
import com.shyam.gujarat_police.response.APIResponse;
import com.shyam.gujarat_police.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/")
    public APIResponse getAllEvents(){
        return APIResponse.ok(eventService.getAllEvents());
    }

    @PostMapping("/")
    public APIResponse saveEvent(@RequestBody @Valid Event event){
        Event dto = eventService.saveEvent(event);
        return APIResponse.ok(dto);
    }

    @PutMapping("/{eventId}")
    public APIResponse updateEvent(@RequestBody @Valid Event event,
        @PathVariable("eventId") Long eventId){
        Event dto = eventService.updateEvent(event, eventId);
        return APIResponse.ok(dto);
    }

    @DeleteMapping("/{eventId}")
    public APIResponse deleteEvent(@PathVariable("eventId") Long eventId){
        eventService.deleteEvent(eventId);
        return APIResponse.ok("Event deleted successfully with id " + eventId);
    }

    @GetMapping("/{eventId}/")
    public APIResponse readSpecific(@PathVariable("eventId") Long eventId){
        Event dto = eventService.readSpecific(eventId);
        return APIResponse.ok(dto);
    }
}
