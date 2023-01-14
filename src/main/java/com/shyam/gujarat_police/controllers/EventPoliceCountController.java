package com.shyam.gujarat_police.controllers;

import com.shyam.gujarat_police.dto.request.EventPoliceCountDto;
import com.shyam.gujarat_police.entities.EventPoliceCount;
import com.shyam.gujarat_police.response.APIResponse;
import com.shyam.gujarat_police.services.EventPoliceCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/event_police_count")
public class EventPoliceCountController {

    @Autowired
    EventPoliceCountService eventPoliceCountService;

    @GetMapping("/")
    public APIResponse getAllEventsPoliceCount(){
        List<EventPoliceCount> resp = eventPoliceCountService.getAllEventPoliceCount();
        return APIResponse.ok(resp);
    }

    @PostMapping("/")
    public APIResponse saveEventPoliceCount(@RequestBody @Valid EventPoliceCountDto dto){
        EventPoliceCount resp = eventPoliceCountService.saveEventPoliceCount(dto);
        return APIResponse.ok(resp);
    }

    @PutMapping("/{eventPoliceCountId}")
    public APIResponse updateEventPoliceCount(@RequestBody @Valid EventPoliceCount eventPoliceCount,
                                   @PathVariable("eventPoliceCountId") Long eventPoliceCountId){
        EventPoliceCount dto = eventPoliceCountService.updateEventPoliceCount(eventPoliceCount, eventPoliceCountId);
        return APIResponse.ok(dto);
    }

    @DeleteMapping("/{eventPoliceCountId}")
    public APIResponse deleteEventPoliceCount(@PathVariable("eventPoliceCountId") Long eventPoliceCountId){
        eventPoliceCountService.deleteEventPoliceCount(eventPoliceCountId);
        return APIResponse.ok("Event deleted successfully with id " + eventPoliceCountId);
    }

    @GetMapping("/{eventPoliceCountId}/")
    public APIResponse readSpecific(@PathVariable("eventPoliceCountId") Long eventPoliceCountId){
        EventPoliceCount dto = eventPoliceCountService.readSpecific(eventPoliceCountId);
        return APIResponse.ok(dto);
    }
}
